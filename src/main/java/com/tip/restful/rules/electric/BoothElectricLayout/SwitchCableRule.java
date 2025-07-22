package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.electric.BoothElectricLayoutResolver;

import java.util.List;

public class SwitchCableRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        BoothElectricLayoutResolver.MainInfo main = (BoothElectricLayoutResolver.MainInfo) context.get(DataKeyConstant.MAIN_INFO);
        List<BoothElectricLayoutResolver.BranchInfo> branches = (List<BoothElectricLayoutResolver.BranchInfo>) context.get(DataKeyConstant.BRANCH_INFO);
        //I1：主开关电流
        int mainI1=main.getMain_i();
        int mainCableSize=main.getCable_size();
        //I2：主开关电缆规格 --> 知识库“允许通过最大电流”。根据知识库结构来讨论如何实现。
        int mainI2=Integer.parseInt((String)context.get(DataKeyConstant.TARGET_MAXINCURRENT));

        if(mainI1>mainI2) return RuleResult.fail("主开关电流("+mainI1+"A)不得超过知识库中允许通过最大电流("+mainI2+"A)");

        for(BoothElectricLayoutResolver.BranchInfo branch:branches){
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




}
