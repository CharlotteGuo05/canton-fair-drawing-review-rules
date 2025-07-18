package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import java.util.List;

public class layoutSystemRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        MainInfo main = (MainInfo) context.get(DataKeyConstant.MAIN_INFO);
        List<BranchInfo> branches = (List<BranchInfo>) context.get(DataKeyConstant.BRANCH_INFO);
        electriclayout layout = (electriclayout) context.get(DataKeyConstant.ELECTRIC_LAYOUT);

        //每一个回路的灯具数量不得超过25盏
        for(BranchInfo branch : branches){
            int branchNumber = branch.getNumber();
            if(branchNumber > 25) return RuleResult.fail("回路灯具数量不得超过25盏");
        }

        //接地判断
        String isGround = layout.getGround();
        if(!isGround.equals("True")) return RuleResult.fail("没有接地文字说明");

        //主电缆p数判断
        String isMainCableP = layout.getMain_cable_p();
        if(!isMainCableP.equals("True")) return RuleResult.fail("电箱220V没对应2P或电箱380V没对应3P");


        //分电缆p数判断
        String isBranchCableP = layout.getBranch_cable_p();
        if(!isBranchCableP.equals("True")) return RuleResult.fail("分电缆规格不是1P或2P");

        //节能判断
        String isEnergySaving = layout.getEnergy_saving();
        if(!isEnergySaving.equals("True")) return RuleResult.fail("没有节能文字说明");

        //30mA≤0.1s判断
        String isThirtymA = layout.getThirtymA();
        if(!isThirtymA.equals("True")) return RuleResult.fail("没有30mA≤0.1s文字说明");



        //知识库中的最大允许电流
        int target_maxInCurrent = Integer.parseInt((String)context.get(DataKeyConstant.TARGET_MAXINCURRENT));

        //计算各回路最大电流：w/v
        for(BranchInfo branch : branches){
            int branchCapacity = branch.getCapacity();
            int mainVoltage= main.getMainV();
            int maxInCurrent = branchCapacity/mainVoltage;

            if(maxInCurrent > target_maxInCurrent) return RuleResult.fail("回路电流不得超过知识库中最大允许电流");
        }

        return null;
    }

    @Override
    public String getName() {
        return "配电系统要求";
    }

    public class MainInfo{
        private int box_power;   // 电箱功率
        private int main_v;      // 主开关电压
        private int main_i;     // 主开关电流
        private int cable_size;  // 电缆规格


        public int getMainV(){
            return main_v;
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

        public int getNumber(){
            return number;
        }

        public int getCapacity(){
            return capacity;
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
