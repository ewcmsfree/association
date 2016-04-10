package org.apache.shiro.cache.spring;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Collection;
import java.util.Set;

/**
 * 包装Spring cache抽象
 * 
 * @author wu_zhijun
 */
public class SpringCacheManagerWrapper implements CacheManager {

    private org.springframework.cache.CacheManager cacheManager;

    /**
     * 设置spring cache manager
     *
     * @param cacheManager
     */
    public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        org.springframework.cache.Cache springCache = cacheManager.getCache(name);
        return new SpringCacheWrapper<K, V>(springCache);
    }

    static class  SpringCacheWrapper<K, V> implements Cache<K, V> {
        private org.springframework.cache.Cache springCache;

        SpringCacheWrapper(org.springframework.cache.Cache springCache) {
            this.springCache = springCache;
        }

        @SuppressWarnings("unchecked")
		@Override
        public Object get(Object key) throws CacheException {
            Object value = springCache.get(key);
            if (value instanceof SimpleValueWrapper) {
                return ((SimpleValueWrapper) value).get();
            }
            return value;
        }

        @SuppressWarnings("unchecked")
		@Override
        public Object put(Object key, Object value) throws CacheException {
            springCache.put(key, value);
            return value;
        }

        @SuppressWarnings("unchecked")
		@Override
        public Object remove(Object key) throws CacheException {
            springCache.evict(key);
            return null;
        }

        @Override
        public void clear() throws CacheException {
            springCache.clear();
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
        }

        @Override
        public Set<K> keys() {
            throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
        }

        @Override
        public Collection<V> values() {
            throw new UnsupportedOperationException("invoke spring cache abstract values method not supported");
        }
    }
}
