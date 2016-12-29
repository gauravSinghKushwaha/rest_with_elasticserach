package com.swiggy.dao;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.swiggy.model.PlayList;
import com.swiggy.model.Tag;

@Service("playListDao")
public class PlayListDaoImpl implements PlayListDao {

    final ArrayList<PlayList> result;
    final ArrayList<Tag> tags;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.readLock();

    public PlayListDaoImpl() {
        result = newArrayList();
        tags = newArrayList();
    }

    @Override
    public List<PlayList> getPlayListByTags(final List<Tag> tag) {
        readLock.lock();
        try {
            return result.stream().filter(t -> {
                for (final Tag listTag : t.getTags()) {
                    if (tag.contains(listTag)) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<PlayList> getAllPlayLists(final int startRange, final int endRange) {
        readLock.lock();
        try {
            return result.subList(startRange, endRange > result.size() ? result.size() : endRange).stream()
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<String> addPlayLists(final List<PlayList> playLists) {
        writeLock.lock();
        try {
            for (final PlayList playList : playLists) {
                result.add(playList);
            }
            final List<String> res = newArrayList();
            playLists.stream().forEach(t -> res.add(t.getId()));
            return res;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<String> updatePlayLists(final List<PlayList> playLists) {
        writeLock.lock();
        try {
            result.removeAll(playLists);
            result.addAll(playLists);
            final List<String> res = newArrayList();
            playLists.stream().forEach(t -> res.add(t.getId()));
            return res;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removePlayListByTags(final List<Tag> tag) {
        writeLock.lock();
        try {
            result.removeIf(t -> tag.containsAll(t.getTags()));
        } finally {
            writeLock.unlock();
        }
    }

}
