package com.swiggy.cache;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;
import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Cache using memcached
 * 
 * @author gkushwaha
 *
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    private static final String AT = "@";
    private static final Logger LOGGER = getLogger(CacheServiceImpl.class);

    private final String prefix;
    private final int cacheExpiration;
    private final MemcachedClient memcachedClient;

    @Autowired
    public CacheServiceImpl(@Value("${cache.prefix}") final String prefix,
                            @Value("${cache.expiration}") final int cacheExpiration,
                            final MemcachedClient memcachedClient) {
        this.prefix = prefix;
        this.cacheExpiration = cacheExpiration;
        this.memcachedClient = checkNotNull(memcachedClient, "The memcached client cannot be null");
    }

    private String createCacheKey(final String key) {
        return prefix + AT + key.trim();
    }

    @Override
    public Object get(final String k) {
        final String key = createCacheKey(k);
        final Object object = memcachedClient.get(key);
        LOGGER.debug("key = , object = ", k, object);
        return object;
    }

    @Override
    public void add(final String k, final Object v) {
        LOGGER.debug("key = , object = ", k, v);
        memcachedClient.add(createCacheKey(k), cacheExpiration, v);
    }

}
