package com.tip.restful.resolvers.design;


import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

public class BoothThirdViewResolver implements DataResolver {

    // 数据库获取
//    private final IDaReportDrawingInfoService drawingInfoService;

    //知识库
    private final KnowledgeRepositoryService knowledgeRepositoryService;

    // 大模型
//    private final ModelExtractionService modelExtractionService;


    public BoothThirdViewResolver( KnowledgeRepositoryService knowledgeRepositoryService) {
//        this.drawingInfoService = drawingInfoService;
        this.knowledgeRepositoryService = knowledgeRepositoryService;
    }

    @Override
    public Object resolve(String key, RuleContext context) {
        switch (key) {
            case DataKeyConstant.REPORT_DRAWING_INFO:
                // 数据库获取整个报图信息 todo 要改
//                return drawingInfoService.getOneReportDrawingInfo("id,测试，后面需要改");
                return "todo";
            case DataKeyConstant.MODEL_EXTRACT_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.DRAWING_KIND:
                // 获取报图信息中的报图类型
//                Map<String, Object> map = JSONHelper.jsonToMap(context.get(DataKeyConstant.REPORT_DRAWING_INFO));
                // todo 具体还需要根据实际的数据结构来获取
//                return map.get("drawingKind");
                return "todo";
            case DataKeyConstant.SECOND_EXHIBITION:
                //获取报图信息中的"是否搭建二层结构"
//                Map<String, Object> map = JSONHelper.jsonToMap(context.get(DataKeyConstant.REPORT_DRAWING_INFO));
//                return map.get("secondExhibition");
                return "todo";
            case DataKeyConstant.BOOTH_ID:
                return "todo";
            case DataKeyConstant.PERIOD:
                return "todo";
            case DataKeyConstant.ALLOW_SECOND:
                //获取知识库中关于是否允许二层的信息
                // Map<String, Object> map = JSONHelper.jsonToMap(context.get(DataKeyConstant.MODEL_EXTRACT_INFO));
                // return map.get("allowSecond");
                return "todo";
            case DataKeyConstant.TARGET_LENGTH:
                return "todo";
            case DataKeyConstant.TARGET_WIDTH:
                return "todo";
            case DataKeyConstant.HAS_FRONT_VIEWS:
                return "todo";
            case DataKeyConstant.HAS_SIDE_VIEWS:
                return "todo";
            case DataKeyConstant.HAS_TOP_VIEWS:
                return "todo";
            case DataKeyConstant.FRONT_HEIGHT:
                return "todo";
            case DataKeyConstant.SIDE_HEIGHT:
                return "todo";
            case DataKeyConstant.WIDTH:
                return "todo";
            case DataKeyConstant.LENGTH:
                return "todo";


            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }
    }


}
