package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import java.util.List;

public class MaterialPowerRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        MainInfo main = (MainInfo) context.get(DataKeyConstant.MAIN_INFO);
        List<BranchInfo> branches = (List<BranchInfo>) context.get(DataKeyConstant.BRANCH_INFO);

        int mainCableSize=main.getCable_size();

        //电缆型号是否在知识库“电缆型号”清单中
        for(BranchInfo branch : branches){
            String cableType=branch.getCable_type();
            String targetCableType=(String)context.get(DataKeyConstant.TARGET_CABLE_TYPE);
            if(!cableType.equals(targetCableType)) return RuleResult.fail("电缆型号("+cableType+")不在清单中");

            //电缆规格不得小于3*2.5mm²
            int cableSize=branch.getCable_size();
            if(cableSize<3*2.5) return RuleResult.fail("电缆规格("+cableSize+"mm²)不得小于3*2.5mm²");

            //敷设方式不为空
            String installation=branch.getInstallation();
            if(installation==null||installation.isEmpty()) return RuleResult.fail("敷设方式不能为空");

            //主电缆规格不得小于分电缆规格
            int branchCableSize=branch.getCable_size();
            if(mainCableSize<branchCableSize) return RuleResult.fail("主电缆规格("+mainCableSize+"mm²)不得小于分电缆规格("+branchCableSize+"mm²)");

            //容量=数量*功率
            int capacity=branch.getCapacity();
            int number=branch.getNumber();
            int power=branch.getPower();

            if(capacity != number*power) return RuleResult.fail("容量("+capacity+"W)不等于数量("+number+")*功率("+power+"W)");



        }





        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "各回路材料及功率要求";
    }


    public class MainInfo{
        private int box_power;   // 电箱功率
        private int main_v;      // 主开关电压
        private int main_i;     // 主开关电流
        private int cable_size;  // 电缆规格

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


    }
}
