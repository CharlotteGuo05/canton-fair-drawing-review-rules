package com.tip.restful.resolvers.fire;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

public class BasicPicResolver implements DataResolver {

    //知识库
    private final KnowledgeRepositoryService knowledgeRepositoryService;


    public BasicPicResolver(KnowledgeRepositoryService knowledgeRepositoryService) {
        this.knowledgeRepositoryService = knowledgeRepositoryService;
    }


    // 大模型
//    private final ModelExtractionService modelExtractionService;

    // 数据库获取
//    private final IDaReportDrawingInfoService drawingInfoService;

    @Override
    public Object resolve(String key, RuleContext context) {
        switch (key) {
            case DataKeyConstant.REPORT_DRAWING_INFO:
                // 数据库获取整个报图信息
//                return drawingInfoService.getOneReportDrawingInfo("id,测试，后面需要改");
                return "todo";
            case DataKeyConstant.MODEL_EXTRACT_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.FLAME_PROOF_MATERIAL:
                return "todo";
            case DataKeyConstant.FIRE_MATERIAL_REQUIREMENT:
                return "todo";
            case DataKeyConstant.FIRE_MATERIAL_LIST:
                return "todo";

            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }
    }
}
