package com.tip.restful.resolvers.design;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

import java.util.List;
import java.util.Map;

public class InsuranceReviewResolver implements DataResolver {

    // 数据库获取
//    private final IDaReportDrawingInfoService drawingInfoService;

    //知识库
    private final KnowledgeRepositoryService knowledgeRepositoryService;

    // 大模型
//    private final ModelExtractionService modelExtractionService;


    public InsuranceReviewResolver(KnowledgeRepositoryService knowledgeRepositoryService) {
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
                // 获取大模型信息
//                return modelExtractionService.getExtraction(key);
                return "todo";
            case DataKeyConstant.KNOWLEDGE_BASE_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.COMPANY_NAME:
//                Abc o = (Abc)context.get(DataKeyConstant.REPORT_DRAWING_INFO);
//                return o.getBasicInfo().getCompanyName();
            case DataKeyConstant.BOOTH_ID:
                return "todo";
            case DataKeyConstant.SPECIAL_BOOTH_NAME:
                return "todo";
            case DataKeyConstant.EXHIBITION_START:
                return "todo";
            case DataKeyConstant.EXHIBITION_END:
                return "todo";
            case DataKeyConstant.PREMISES_LIABILITY:
                return "todo";
            case DataKeyConstant.EMPLOYER_LIABILITY:
                return "todo";
            case DataKeyConstant.PERSONAL_INJURY:
                return "todo";
            case DataKeyConstant.HAS_INSURANCE:
                return ((BasicRequirement)getModel(context)).getHasInsurance();
            case DataKeyConstant.HAS_STAMP:
                return ((BasicRequirement)getModel(context)).getHasStamp();
            case DataKeyConstant.INSURANCE_COMPANY:
                return ((Exhibitor)getModel(context)).getInsuranceCompany();
            case DataKeyConstant.INSURANCE_POLICY:
                return ((InsuranceInfo)getModel(context)).getInsurancePolicy();
            case DataKeyConstant.INSURANCE_PERIOD:
                return ((BasicInfo)getModel(context)).getInsurancePeriod();
            case DataKeyConstant.INSURANCE_AMOUNT:
                return ((InsuranceInfo)getModel(context)).getInsuranceAmount();
            case DataKeyConstant.BOOTH_AREA:
                return ((InsuranceInfo)getModel(context)).getBoothArea();
            case DataKeyConstant.ACCUMULATED_AMOUNT:
                return ((InsuranceInfo)getModel(context)).getAccumulatedAmount();
            case DataKeyConstant.INSURANCE_BOOTH_ID:
                return ((BasicInfo)getModel(context)).getInsuranceBoothID();

            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }
    }

    private Object getModel(RuleContext context) {
        return context.get(DataKeyConstant.MODEL_EXTRACT_INFO);
    }

//    private Object getKnowledge(RuleContext context){
//        return context.get(DataKeyConstant.KNOWLEDGE_BASE_INFO);
//    }
//
//    private Object getDrawing(RuleContext context){
//        return context.get(DataKeyConstant.REPORT_DRAWING_INFO);
//    }


    public class BasicRequirement {
        public List<String> hasInsurance;
        public String hasStamp;

        public List<String> getHasInsurance() {
            return hasInsurance;
        }
        public String getHasStamp() {
            return hasStamp;
        }
    }

    public class Exhibitor{
        public String insuranceCompany;

        public String getInsuranceCompany() {
            return insuranceCompany;
        }

    }

    public class BasicInfo{
        public String insuranceBoothID;
        List<String> insurancePeriod;

        public String getInsuranceBoothID() {
            return insuranceBoothID;
        }

        public List<String> getInsurancePeriod() {
            return insurancePeriod;
        }

    }

    public class InsuranceInfo{
        public String insurancePolicy;
        public String insuranceAmount;
        public String boothArea;
        public Map<String, String> accumulatedAmount;

        public String getInsurancePolicy() {
            return insurancePolicy;
        }

        public String getInsuranceAmount() {
            return insuranceAmount;
        }

        public String getBoothArea() {
            return boothArea;
        }

        public Map<String, String> getAccumulatedAmount() {
            return accumulatedAmount;
        }


    }


}
