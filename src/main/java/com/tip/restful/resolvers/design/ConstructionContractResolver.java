package com.tip.restful.resolvers.design;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

import java.util.List;

public class ConstructionContractResolver implements DataResolver {

    // 数据库获取
//    private final IDaReportDrawingInfoService drawingInfoService;

    //知识库
    private final KnowledgeRepositoryService knowledgeRepositoryService;

    // 大模型
//    private final ModelExtractionService modelExtractionService;

    public ConstructionContractResolver(KnowledgeRepositoryService knowledgeRepositoryService) {
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
//               return modelExtractionService.getExtraction(key);
                return "todo";
            case DataKeyConstant.KNOWLEDGE_BASE_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.EXHIBITION_START:
                return "todo";
            case DataKeyConstant.EXHIBITION_END:
                return "todo";
            case DataKeyConstant.IS_CONTRACT:
                return ((BasicRequirement)getModel( context)).getIsContract();
            case DataKeyConstant.PARTY_A:
                return ((BasicRequirement)getModel(context)).getPartyA();
            case DataKeyConstant.PARTY_B:
                return ((BasicRequirement)getModel(context)).getPartyB();
            case DataKeyConstant.CONTRACT_PERIOD:
                return ((BasicInfo)getModel(context)).getContractPeriod();

            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }
    }
    private Object getModel(RuleContext context) {
        return context.get(DataKeyConstant.MODEL_EXTRACT_INFO);
    }


    public class BasicRequirement{
        public String isContract;
        public String partyA;
        public String partyB;

        public String getIsContract() {
			return isContract;
		}
		public String getPartyA() {
			return partyA;
		}
		public String getPartyB() {
			return partyB;
		}
    }

    public class BasicInfo{
        public List<String> contractPeriod;

        public List<String> getContractPeriod(){
            return contractPeriod;
        }
    }


}
