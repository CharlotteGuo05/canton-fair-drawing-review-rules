package com.tip.restful.resolvers.electric;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

import java.util.List;

public class ConstructionSafetyResolver implements DataResolver {

    // 数据库获取
//    private final IDaReportDrawingInfoService drawingInfoService;

    //知识库
    private final KnowledgeRepositoryService knowledgeRepositoryService;


    // 大模型
//    private final ModelExtractionService modelExtractionService;

    public ConstructionSafetyResolver(KnowledgeRepositoryService knowledgeRepositoryService) {
        this.knowledgeRepositoryService = knowledgeRepositoryService;
    }


    // 大模型
//    private final ModelExtractionService modelExtractionService;

    @Override
    public Object resolve(String key, RuleContext context) {
        switch (key){
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
            case DataKeyConstant.CONSTRUCTION_SAFETY_REQUIREMENT:
                return ((BasicRequirement)getModel(context)).getConstructionSafetyRequirement();
            case DataKeyConstant.SAFETY_STAMP:
                return ((StampRequirement)getModel(context)).getSafetyStamp();
            case DataKeyConstant.SPECIAL_BOOTH_NAME:
                return "todo";

            default:
                throw new IllegalArgumentException("Invalid key: " + key);


        }
    }
    private Object getModel(RuleContext context) {
        return context.get(DataKeyConstant.MODEL_EXTRACT_INFO);
    }
    public class BasicRequirement {
        public String constructionSafetyRequirement;
        public String getConstructionSafetyRequirement(){
            return constructionSafetyRequirement;
        }
    }
    public class StampRequirement {
        public StampInfo safetyStamp;
        public StampInfo getSafetyStamp(){
            return safetyStamp;
        }
    }
    public class StampInfo {
        public String partyA;
        public String partyB;
        public List<String> signDate;

        public String getPartyA(){
            return partyA;
        }
        public String getPartyB(){
            return partyB;
        }
        public List<String> getSignDate(){
            return signDate;
        }
    }
}
