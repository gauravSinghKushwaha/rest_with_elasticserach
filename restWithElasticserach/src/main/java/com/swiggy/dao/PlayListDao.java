package com.swiggy.dao;

import java.util.List;

import com.swiggy.model.PlayList;
import com.swiggy.model.Tag;

/**
 * The data access object for {@link PlayList}.
 */
public interface PlayListDao {

    List<PlayList> getPlayListByTags(final List<Tag> tag);

    List<PlayList> getAllPlayLists(int startRange, int endRange);

    List<String> addPlayLists(final List<PlayList> playLists);

    List<String> updatePlayLists(final List<PlayList> playLists);

    void removePlayListByTags(final List<Tag> tag);

}
