package com.tip.restful;

/**
 * 数据解析器接口，负责根据许哟啊获取的信息名称和上下文动态获取数据。
 */
public interface DataResolver {
    Object resolve(String key, RuleContext context);
}

