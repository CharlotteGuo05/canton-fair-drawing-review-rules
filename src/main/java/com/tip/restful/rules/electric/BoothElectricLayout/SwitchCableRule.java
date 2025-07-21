package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import java.util.List;

public class SwitchCableRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        MainInfo main = (MainInfo) context.get(DataKeyConstant.MAIN_INFO);
        List<BranchInfo> branches = (List<BranchInfo>) context.get(DataKeyConstant.BRANCH_INFO);
        //I1：主开关电流
        int mainI1=main.getMain_i();
        int mainCableSize=main.getCable_size();
        //I2：主开关电缆规格 --> 知识库“允许通过最大电流”。根据知识库结构来讨论如何实现。
        int mainI2=Integer.parseInt((String)context.get(DataKeyConstant.TARGET_MAXINCURRENT));

        if(mainI1>mainI2) return RuleResult.fail("主开关电流("+mainI1+"A)不得超过知识库中允许通过最大电流("+mainI2+"A)");

        for(BranchInfo branch:branches){
            int branchI1=branch.getBranch_i();
            int branchCableSize=branch.getCable_size();
            int branchI2=Integer.parseInt((String)context.get(DataKeyConstant.TARGET_MAXINCURRENT));

            if(branchI1>branchI2) return RuleResult.fail("回路电流("+branchI1+"A)不得超过知识库中最大允许电流("+branchI2+"A)");
        }





        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "开关规格与电缆材料要求";
    }


    public class MainInfo{
        private int box_power;   // 电箱功率
        private int main_v;      // 主开关电压
        private int main_i;     // 主开关电流
        private int cable_size;  // 电缆规格

        public int getMain_i(){
            return main_i;
        }

        public int getCable_size(){
            return cable_size;
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

        public int getBranch_i(){
            return branch_i;
        }

        public int getCable_size(){
            return cable_size;
        }


    }


}
