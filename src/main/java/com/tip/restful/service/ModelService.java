package com.tip.restful.service;

/* 演示用 */
public interface ModelService {
    // 从模型抽取信息中搜索某项，接口可能还需要包含 prompt
    Object extractInfo(Object key);
}
