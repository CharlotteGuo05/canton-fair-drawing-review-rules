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
                // 获取大模型信息
//               return modelExtractionService.getExtraction(key);
                return "todo";
            case DataKeyConstant.KNOWLEDGE_BASE_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.ELECTRIC_BASIC:
                return ((BasicRequirement)getModel(context)).getElectricBasic();
            case DataKeyConstant.MAIN_INFO:
                return ((Main)getModel(context)).getMain();
            case DataKeyConstant.BRANCH_INFO:
                return ((Branch)getModel(context)).getBranch();
            case DataKeyConstant.ELECTRIC_LAYOUT:
                return ((peidian)getModel(context)).getPeidian();
            case DataKeyConstant.TARGET_MAXINCURRENT:
                return "todo";
            case DataKeyConstant.TARGET_CABLE_TYPE:
                return "todo";

            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }

    }
    private Object getModel(RuleContext context) {
        return context.get(DataKeyConstant.MODEL_EXTRACT_INFO);
    }

    public class BasicRequirement{
        public String electricBasic;

        public String getElectricBasic() {
            return electricBasic;
        }
    }

    public class Main{
        public MainInfo main;

        public MainInfo getMain(){
            return main;
        }
    }

    public class Branch{
        public BranchInfo branch;

        public BranchInfo getBranch(){
            return branch;
        }
    }

    public class peidian{
        public electriclayout peidian;
        public electriclayout getPeidian(){
            return peidian;
        }
    }

    public class MainInfo{
        private int box_power;   // 电箱功率
        private int main_v;      // 主开关电压
        private int main_i;     // 主开关电流
        private int cable_size;  // 电缆规格


        public int getMainV(){ return main_v; }
        public int getCable_size(){
            return cable_size;
        }
        public int getMain_i(){
            return main_i;
        }

    }

    public class BranchInfo{
        private String no;          // 回路编号
        private int number;      // 数量
        private int power;       // 功率
        private int capacity;    // 容量
        private int branch_i;   // 回路电流
        private int cable_size;  // 电缆规格
        private String cable_type;  // 电缆型号
        private String installation; //敷设方式

        public String getCable_type(){
            return cable_type;
        }
        public int getCable_size(){
            return cable_size;
        }
        public String getInstallation(){
            return installation;
        }
        public int getCapacity(){
            return capacity;
        }
        public int getNumber(){
            return number;
        }
        public int getPower(){
            return power;
        }
        public int getBranch_i(){
            return branch_i;
        }


    }

    public class electriclayout{
        private String ground;
        private String main_cable_p;
        private String branch_cable_p;
        private String energy_saving;
        private String thirtymA;

        public String getGround(){
            return ground;
        }

        public String getMain_cable_p(){
            return main_cable_p;
        }
        public String getBranch_cable_p(){
            return branch_cable_p;
        }
        public String getEnergy_saving(){
            return energy_saving;
        }
        public String getThirtymA(){
            return thirtymA;
        }


    }
}
