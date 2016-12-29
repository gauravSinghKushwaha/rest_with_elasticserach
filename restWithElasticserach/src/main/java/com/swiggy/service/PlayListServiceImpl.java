package com.swiggy.service;

import static com.google.common.base.Optional.fromNullable;
import static java.lang.String.join;
import static java.lang.String.valueOf;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.swiggy.cache.CacheService;
import com.swiggy.dao.PlayListDao;
import com.swiggy.model.PlayList;
import com.swiggy.model.Tag;

@Service("playListService")
public class PlayListServiceImpl implements PlayListService {

    private final PlayListDao playListDao;
    private final CacheService cacheService;

    private static final Logger LOGGER = getLogger(PlayListServiceImpl.class);
    private static final String KEY_ALL = "ALL";

    @Autowired
    public PlayListServiceImpl(final PlayListDao playListDao, final CacheService cacheService) {
        this.playListDao = playListDao;
        this.cacheService = cacheService;
    }

    @Override
    public Optional<List<PlayList>> getPlayListByTags(final List<Tag> tag) {
        final String key = generateKey(tag);
        @SuppressWarnings("unchecked")
        final Optional<List<PlayList>> value = (Optional<List<PlayList>>) cacheService.get(key);
        LOGGER.debug("tag = {} key = {} value = {}", tag, key, value);
        if(value !=null ){
            return value;
        }
        final List<PlayList> playListByTags = playListDao.getPlayListByTags(tag);
        final Optional<List<PlayList>> result = fromNullable(playListByTags);
        cacheService.add(key, result);
        return result;
    }

    @Override
    public Optional<List<PlayList>> getAllPlayLists(final int startRange, final int endRange) {
        final String key = join("::", KEY_ALL, valueOf(startRange), valueOf(endRange));
        @SuppressWarnings("unchecked")
        final Optional<List<PlayList>> value = (Optional<List<PlayList>>) cacheService.get(key);
        LOGGER.debug("value = {}", value);
        if (value != null) {
            return value;
        }
        final List<PlayList> playListByTags = playListDao.getAllPlayLists(startRange, endRange);
        final Optional<List<PlayList>> result = fromNullable(playListByTags);
        cacheService.add(key, result);
        return result;
    }

    @Override
    public Optional<List<String>> addPlayLists(final List<PlayList> playLists) {
        LOGGER.debug("playLists =", playLists);
        return fromNullable(playListDao.addPlayLists(playLists));
    }

    @Override
    public Optional<List<String>> updatePlayLists(final List<PlayList> playLists) {
        LOGGER.debug("playLists =", playLists);
        return fromNullable(playListDao.updatePlayLists(playLists));
    }

    @Override
    public void removePlayListByTags(final List<Tag> tag) {
        LOGGER.debug("tag =", tag);
        playListDao.removePlayListByTags(tag);
    }

    private String generateKey(final List<Tag> tag) {
        LOGGER.debug("tag =", tag);
        final String key =
                tag.stream().sorted((o1, o2) -> o1.getTag().compareTo(o2.getTag()))
                .reduce((t, u) -> new Tag(t.getTag() + ":" + u.getTag())).get().getTag();
        return key;
    }

}
