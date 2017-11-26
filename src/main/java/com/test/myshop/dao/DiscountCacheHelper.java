package com.test.myshop.dao;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 *  This is the Ehcache helper class Which maintains an inmemory state of the Discount metadata cache .
 *
 *
 */

public class DiscountCacheHelper {

    private static DiscountCacheHelper cacheHelper;
    private CacheManager cacheManager;
    private Cache<String, Float> brandsDiscountCache;
    private Cache<String, Float> categoryDiscountCache;
    private Cache<String, String> relationCache;

    public DiscountCacheHelper() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();

        brandsDiscountCache = cacheManager.createCache("brandsDiscountCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Float.class, ResourcePoolsBuilder.heap(20)));
        categoryDiscountCache = cacheManager.createCache("categoryDiscountCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Float.class, ResourcePoolsBuilder.heap(20)));
        relationCache = cacheManager.createCache("relationCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(20)));
    }

    /**
     * Returs a Singleton instance of the cache helper
     *
     * @return
     */
    public static DiscountCacheHelper getInstance() {
        if (cacheHelper == null)
            cacheHelper = new DiscountCacheHelper();

        return cacheHelper;
    }

    public Cache<String, Float> getBrandsDiscountCache() {
        return brandsDiscountCache;
    }

    public Cache<String, Float> getCategoryDiscountCache() {
        return categoryDiscountCache;
    }

    public Cache<String, String> getRelationCache() {
        return relationCache;
    }




}
