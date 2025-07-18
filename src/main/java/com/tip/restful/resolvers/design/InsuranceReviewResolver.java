package com.tip.restful.resolvers.design;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

public class InsuranceReviewResolver implements DataResolver {

    private final KnowledgeRepositoryService knowledgeRepositoryService;

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
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.COMPANY_NAME:
                return "todo";
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
                return "todo";
            case DataKeyConstant.HAS_STAMP:
                return "todo";
            case DataKeyConstant.INSURANCE_COMPANY:
                return "todo";
            case DataKeyConstant.INSURANCE_POLICY:
                return "todo";
            case DataKeyConstant.INSURANCE_PERIOD:
                return "todo";
            case DataKeyConstant.INSURANCE_AMOUNT:
                return "todo";
            case DataKeyConstant.BOOTH_AREA:
                return "todo";
            case DataKeyConstant.ACCUMULATED_AMOUNT:
                return "todo";
            case DataKeyConstant.INSURANCE_BOOTH_ID:
                return "todo";

            default:
                throw new IllegalArgumentException("Invalid key: " + key);



        }
    }
}
