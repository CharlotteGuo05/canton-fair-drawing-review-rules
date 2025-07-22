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
                // 数据库获取整个报图信息
//                return drawingInfoService.getOneReportDrawingInfo("id,测试，后面需要改");
                return "todo";
            case DataKeyConstant.MODEL_EXTRACT_INFO:
                // 获取大模型信息
//               return modelExtractionService.getExtraction(key);
                return "todo";
            case DataKeyConstant.KNOWLEDGE_BASE_INFO:
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
                return ((BasicRequirement)getModel(context)).getHasFront();
            case DataKeyConstant.HAS_SIDE_VIEWS:
                return ((BasicRequirement)getModel(context)).getHasSide();
            case DataKeyConstant.HAS_TOP_VIEWS:
                return ((BasicRequirement)getModel(context)).getHasTop();
            case DataKeyConstant.HEIGHT:
                return ((HeightRequirement)getModel(context)).getHeight();
            case DataKeyConstant.WIDTH:
                return ((LengthWidthRequirement)getModel(context)).getWidth();
            case DataKeyConstant.LENGTH:
                return ((LengthWidthRequirement)getModel(context)).getLength();


            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }
    }
    private Object getModel(RuleContext context) {
        return context.get(DataKeyConstant.MODEL_EXTRACT_INFO);
    }

    public class BasicRequirement{
        public String hasFront;
        public String hasSide;
        public String hasTop;

        public String getHasFront() {
            return hasFront;
        }
        public String getHasSide() {
            return hasSide;
        }
        public String getHasTop() {
            return hasTop;
        }
    }
    public class LengthWidthRequirement{
        public String length;
        public String width;

        public String getLength() {
            return length;
        }
        public String getWidth() {
            return width;
        }

    }
    public class HeightRequirement{
        public String height;

        public String getHeight() {
            return height;
        }
    }

}
