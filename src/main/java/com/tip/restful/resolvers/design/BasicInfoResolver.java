package com.tip.restful.resolvers.design;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

public class BasicInfoResolver implements DataResolver {

    private final KnowledgeRepositoryService knowledgeRepositoryService;

    public BasicInfoResolver(KnowledgeRepositoryService knowledgeRepositoryService) {
        this.knowledgeRepositoryService = knowledgeRepositoryService;
    }


    @Override
    public Object resolve(String key, RuleContext context) {
        switch(key) {
            case DataKeyConstant.REPORT_DRAWING_INFO:
                // 数据库获取整个报图信息
//                return drawingInfoService.getOneReportDrawingInfo("id,测试，后面需要改");
                return "todo";
            case DataKeyConstant.MODEL_EXTRACT_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.COMPANY_ID:
                return "todo";
            case DataKeyConstant.DRAWING_COMPANY_ID:
                return "todo";
            case DataKeyConstant.BOOTH_ID:
                return "todo";
            case DataKeyConstant.PERIOD:
                return "todo";

            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }
    }
}
