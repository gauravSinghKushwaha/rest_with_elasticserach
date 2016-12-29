package com.swiggy.service;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.collect.Maps.newHashMap;
import static com.swiggy.util.TagUtil.geTagList;
import static java.lang.Integer.valueOf;
import static java.lang.String.join;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.swiggy.cache.CacheService;
import com.swiggy.dao.PlayListDao;
import com.swiggy.model.ExploreResult;
import com.swiggy.model.PlayList;
import com.swiggy.model.Tag;

@Service("exploreService")
public class ExploreServiceImpl implements ExploreService {

    private static final Logger LOGGER = getLogger(ExploreServiceImpl.class);

    private final PlayListDao playListDao;
    private final CacheService cache;

    @Autowired
    public ExploreServiceImpl(final PlayListDao playListDao, final CacheService cache) {
        this.playListDao = playListDao;
        this.cache = cache;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<ExploreResult> explore(final String tags, final int startRange, final int endRange) {
        LOGGER.debug("tags={} , startRange = {} , endRange = {}", tags, startRange, endRange);
        final List<Tag> tagList = geTagList(tags);
        final String key = getKey(startRange, endRange, tagList);

        final Optional<ExploreResult> value = (Optional<ExploreResult>) cache.get(key);
        if (value != null) {
            return value;
        }

        final List<PlayList> playList = playListDao.getPlayListByTags(tagList);

        /**
         * Considering both play count and like count has same priority, comparing based on sum of likeCount and
         * playCount.
         */
        final Comparator<PlayList> cmp = Comparator.comparing(PlayList::getLikePlayCount).reversed();

        // IF PLAY COUNT GOT HIGHER PRIORITY OVER LIKE COUNT , we shall use below comparator
        // final Comparator<PlayList> cmp =
        // Comparator.comparing(PlayList::getPlayCount).thenComparing(PlayList::getLikeCount).reversed();

        /**
         * Filtering out playlists with less than 8 songs
         * 
         * Filtering out playlists with 0 likes
         * 
         * Filtering out playlists with 0 playcount
         * 
         */
        final List<PlayList> filterSortedList =
                playList.stream()
                .filter(t -> t.getNumberOfSongs() >= 8 || t.getPlayCount() == 0 || t.getLikeCount() == 0)
                .sorted(cmp).collect(toList());

        final List<PlayList> list =
                filterSortedList
                .subList(startRange, endRange < filterSortedList.size() ? endRange : filterSortedList.size())
                .stream().collect(Collectors.toList());

        /**
         * Looking in search results (playlists) for suggestions tags, making a map of all the tags in results , with
         * tag being key and count being value. choosing the top tags to present suggestions.
         * 
         */
        final Map<Tag, Integer> map = makeMapOfTagsFromSearchResult(list);
        final Map<Tag, Integer> suggestions = getSuggestionsUsingTagCountsInSearchResult(startRange, endRange, map);
        final List<Tag> suggestedTags = suggestions.keySet().stream().collect(Collectors.toList());

        LOGGER.debug("map =  {} , suggestions = {} suggestedTags = {] ", map, suggestions, suggestedTags);

        final Optional<ExploreResult> result = fromNullable(new ExploreResult(list, suggestedTags));
        cache.add(key, result);
        LOGGER.debug("ExploreResult ", result);
        return result;

    }

    private Map<Tag, Integer> getSuggestionsUsingTagCountsInSearchResult(final int startRange, final int endRange,
            final Map<Tag, Integer> map) {
        final Map<Tag, Integer> suggestions =
                map.entrySet()
                .stream()
                .sorted(Map.Entry.<Tag, Integer> comparingByValue().reversed())
                .limit(endRange - startRange)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
        return suggestions;
    }

    private Map<Tag, Integer> makeMapOfTagsFromSearchResult(final List<PlayList> list) {
        final Map<Tag, Integer> map = newHashMap();
        list.stream().forEach(t -> {
            t.getTags().forEach(t1 -> {
                Integer count = map.get(t1);
                if (null != count) {
                    map.put(t1, ++count);
                } else {
                    map.put(t1, valueOf(1));
                }
            });
        });
        return map;
    }

    private String getKey(final int startRange, final int endRange, final List<Tag> tagList) {
        final StringBuilder st = new StringBuilder();
        tagList.stream().forEach(t -> st.append(t.getTag()));
        final String key = join(":", st.toString(), String.valueOf(startRange), String.valueOf(endRange));
        return key;
    }

}
