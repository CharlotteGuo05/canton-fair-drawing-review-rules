package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.electric.BoothElectricLayoutResolver;

import java.util.List;

public class MaterialPowerRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        BoothElectricLayoutResolver.MainInfo main = (BoothElectricLayoutResolver.MainInfo) context.get(DataKeyConstant.MAIN_INFO);
        List<BoothElectricLayoutResolver.BranchInfo> branches = (List<BoothElectricLayoutResolver.BranchInfo>) context.get(DataKeyConstant.BRANCH_INFO);

        int mainCableSize=main.getCable_size();

        //电缆型号是否在知识库“电缆型号”清单中
        for(BoothElectricLayoutResolver.BranchInfo branch : branches){
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


}
