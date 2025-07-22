package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.electric.BoothElectricLayoutResolver;

import java.util.List;

public class layoutSystemRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        BoothElectricLayoutResolver.MainInfo main = (BoothElectricLayoutResolver.MainInfo) context.get(DataKeyConstant.MAIN_INFO);
        List<BoothElectricLayoutResolver.BranchInfo> branches = (List<BoothElectricLayoutResolver.BranchInfo>) context.get(DataKeyConstant.BRANCH_INFO);
        BoothElectricLayoutResolver.electriclayout layout = (BoothElectricLayoutResolver.electriclayout) context.get(DataKeyConstant.ELECTRIC_LAYOUT);

        //每一个回路的灯具数量不得超过25盏
        for(BoothElectricLayoutResolver.BranchInfo branch : branches){
            int branchNumber = branch.getNumber();
            if(branchNumber > 25) return RuleResult.fail("回路灯具数量("+branchNumber+")不得超过25盏");
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
        for(BoothElectricLayoutResolver.BranchInfo branch : branches){
            int branchCapacity = branch.getCapacity();
            int mainVoltage= main.getMainV();
            int maxInCurrent = branchCapacity/mainVoltage;

            if(maxInCurrent > target_maxInCurrent) return RuleResult.fail("回路电流("+maxInCurrent+"A)不得超过知识库中最大允许电流("+target_maxInCurrent+"A)");
        }

        return null;
    }

    @Override
    public String getName() {
        return "配电系统要求";
    }



}
