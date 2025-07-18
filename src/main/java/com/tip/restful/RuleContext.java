package com.tip.restful;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 规则上下文，负责缓存和按需获取审核过程中用到的数据。
 */
public class RuleContext {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    private final DataResolver resolver;

    /**
     * 构造函数，注入数据解析器。
     * @param resolver 数据解析器
     */
    public RuleContext(DataResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * 获取指定数据，如果未缓存则通过resolver动态获取并缓存。
     */
    public Object get(String key) {
        // todo，这里可能涉及到数据库操作，如果触发了多次数据库操作，则需要再优化性能问题。这里使用了 ConcurrentHashMap 不会出现并发问题
        return cache.computeIfAbsent(key, k -> resolver.resolve(k, this));
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }
}
