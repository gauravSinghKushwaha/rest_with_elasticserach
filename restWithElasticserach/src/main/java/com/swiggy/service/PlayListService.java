package com.swiggy.service;

import java.util.List;

import com.google.common.base.Optional;
import com.swiggy.model.PlayList;
import com.swiggy.model.Tag;

/**
 * Service interface
 * 
 * @author gkushwaha
 *
 */
public interface PlayListService {

    Optional<List<PlayList>> getPlayListByTags(final List<Tag> tag);

    Optional<List<PlayList>> getAllPlayLists(final int startRange, final int endRange);

    Optional<List<String>> addPlayLists(final List<PlayList> playLists);

    Optional<List<String>> updatePlayLists(final List<PlayList> playLists);

    void removePlayListByTags(final List<Tag> tag);

}
