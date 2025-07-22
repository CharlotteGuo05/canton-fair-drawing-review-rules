package com.tip.restful.resolvers.design;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

import java.util.List;

public class BoothEffectView implements DataResolver {

    /// 数据库获取
//    private final IDaReportDrawingInfoService drawingInfoService;

    //知识库
    private final KnowledgeRepositoryService knowledgeRepositoryService;

    // 大模型
//    private final ModelExtractionService modelExtractionService;


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
                // 获取大模型信息
//               return modelExtractionService.getExtraction(key);
                return "todo";
            case DataKeyConstant.KNOWLEDGE_BASE_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.COMPANY_NAME:
//                Map<String, Object> map = JSONHelper.jsonToMap(context.get(DataKeyConstant.REPORT_DRAWING_INFO));
//                return map.get("companyName");
                return "todo";
            case DataKeyConstant.FASCIA_COMPANY_NAME:
                return ((CompanyRequirement)getModel(context)).getFasciaCompanyName();
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
                return ((SizeRequirement)getModel(context)).getDisplayHeight();
            case DataKeyConstant.DISPLAY_WIDTH:
                return ((SizeRequirement)getModel(context)).getDisplayWidth();
            case DataKeyConstant.MATERIAL_LIST:
                return ((GreenContent)getModel(context)).getMaterials();
            case DataKeyConstant.GRAPH_CONTENT:
                return ((ViolationContent)getModel(context)).getViolationContent();
            case DataKeyConstant.IS_FORBIDDEN:
                return "todo";
            case DataKeyConstant.IS_WOOD:
                return "todo";
            case DataKeyConstant.IS_HORNOR:
                return "todo";
            case DataKeyConstant.IS_VIOLATION:
                return "todo";

            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }

    }
    private Object getModel(RuleContext context) {
        return context.get(DataKeyConstant.MODEL_EXTRACT_INFO);
    }

    public class CompanyRequirement{
        public String fasciaCompanyName;

        public String getFasciaCompanyName(){
            return fasciaCompanyName;
        }
    }

    public class SizeRequirement{
        public String displayWidth;
        public String displayHeight;

        public String getDisplayWidth(){
            return displayWidth;
        }
        public String getDisplayHeight(){
            return displayHeight;
        }
    }

    public class GreenContent{
        public List<Material> materials;

        public List<Material> getMaterials(){
            return materials;
        }

    }

    public class ViolationContent{
        public String violationContent;
        public String getViolationContent(){
            return violationContent;
        }
    }

    public class Material{
        private String name;
        private String movable;
        private int width;
        private int length;

        public Material(String name, String movable, int width, int length) {
            this.name = name;
            this.movable = movable;
            this.width = width;
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public String isMovable() {
            return movable;
        }

        public int getWidth() {
            return width;
        }

        public int getLength() {
            return length;
        }
    }
}
