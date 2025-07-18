package com.tip.restful.resolvers.electric;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

public class BoothElectricLayoutResolver implements DataResolver {
    // 数据库获取
//    private final IDaReportDrawingInfoService drawingInfoService;

    //知识库
    private final KnowledgeRepositoryService knowledgeRepositoryService;


    // 大模型
//    private final ModelExtractionService modelExtractionService;


    public BoothElectricLayoutResolver(KnowledgeRepositoryService knowledgeRepositoryService) {
        this.knowledgeRepositoryService = knowledgeRepositoryService;
    }

    @Override
    public Object resolve(String key, RuleContext context) {
        switch(key){
            case DataKeyConstant.REPORT_DRAWING_INFO:
                // 数据库获取整个报图信息
//                return drawingInfoService.getOneReportDrawingInfo("id,测试，后面需要改");
                return "todo";
            case DataKeyConstant.MODEL_EXTRACT_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.ELECTRIC_BASIC:
                return "todo";
            case DataKeyConstant.MAIN_INFO:
                return "todo";
            case DataKeyConstant.BRANCH_INFO:
                return "todo";
            case DataKeyConstant.ELECTRIC_LAYOUT:
                return "todo";
            case DataKeyConstant.TARGET_MAXINCURRENT:
                return "todo";
            case DataKeyConstant.TARGET_CABLE_TYPE:
                return "todo";

            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }

    }
}
