package com.tip.restful.service;

/* 演示用 */
public interface KnowledgeRepositoryService {
    // 从知识库搜索某项，接口可能还需要包含 prompt
    Object getKnowledge(Object key);
}
