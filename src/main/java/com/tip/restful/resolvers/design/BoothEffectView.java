package com.tip.restful.resolvers.design;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

public class BoothEffectView implements DataResolver {

    //知识库
    private final KnowledgeRepositoryService knowledgeRepositoryService;

    public BoothEffectView(KnowledgeRepositoryService knowledgeRepositoryService) {
        this.knowledgeRepositoryService = knowledgeRepositoryService;
    }

    @Override
    public Object resolve(String key, RuleContext context) {
        //从数据库&知识库中获取的变量都要记录在case里
        switch(key){
            case DataKeyConstant.REPORT_DRAWING_INFO:
                // 数据库获取整个报图信息
//                return drawingInfoService.getOneReportDrawingInfo("id,测试，后面需要改");
                return "todo";
            case DataKeyConstant.MODEL_EXTRACT_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.COMPANY_NAME:
//                Map<String, Object> map = JSONHelper.jsonToMap(context.get(DataKeyConstant.REPORT_DRAWING_INFO));
//                return map.get("companyName");
                return "todo";
            case DataKeyConstant.FASCIA_COMPANY_NAME:
                return "todo";
            case DataKeyConstant.GREEN_TYPE:
                return "todo";
            case DataKeyConstant.FORBIDDEN_MATERIAL:
                return "todo";
            case DataKeyConstant.WOOD_MATERIAL:
                return "todo";
            case DataKeyConstant.VIOLATION:
                return "todo";
            case DataKeyConstant.HONOR:
                return "todo";
            case DataKeyConstant.HONOR_GRAPH:
                return "todo";
            case DataKeyConstant.DISPLAY_HEIGHT:
                return "todo";
            case DataKeyConstant.DISPLAY_WIDTH:
                return "todo";
            case DataKeyConstant.MATERIAL_LIST:
                return "todo";
            case DataKeyConstant.GRAPH_CONTENT:
                return "todo";

            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }

    }
}
