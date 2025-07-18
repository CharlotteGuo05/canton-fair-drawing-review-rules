package com.tip.restful.rules.design.insurancereview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import java.util.List;

public class InsuranceRule implements Rule {

    @Override
    public RuleResult apply(String imageId,RuleContext context) {

        // 1. 人身伤害字段判断
        String insurancePolicy = (String) context.get(DataKeyConstant.INSURANCE_POLICY);
        if (!"True".equals(insurancePolicy)) {
            return RuleResult.fail("未含有人身伤害约定无免赔或免赔说明含义内容");
        }

        // 2. 每次赔付额字段判断
        List<String> insuranceAmount = (List<String>) context.get(DataKeyConstant.INSURANCE_AMOUNT);
        if (insuranceAmount == null || insuranceAmount.isEmpty()) {
            return RuleResult.fail("未含每次赔付额");
        }

        // 3. 累计赔付额判断
        List<Integer> accumulatedAmount = (List<Integer>) context.get(DataKeyConstant.ACCUMULATED_AMOUNT);
        if (accumulatedAmount == null || accumulatedAmount.size() < 3) {
            return RuleResult.fail("未含有累计赔付额度");
        }

//
//        int boothArea = (int) context.get(DataKeyConstant.BOOTH_AREA);

        //通过boothArea从知识库中获取：
        int premisesLiability = Integer.parseInt((String) context.get(DataKeyConstant.PREMISES_LIABILITY));
        int employerLiability = Integer.parseInt((String) context.get(DataKeyConstant.EMPLOYER_LIABILITY));
        int personalInjury = Integer.parseInt((String) context.get(DataKeyConstant.PERSONAL_INJURY));


        if (accumulatedAmount.get(0) < premisesLiability) {
            return RuleResult.fail("场地责任累计金额小于场地责任额度");
        }

        if (accumulatedAmount.get(1) < employerLiability) {
            return RuleResult.fail("雇员责任累计金额小于雇员责任额度");
        }

        if (accumulatedAmount.get(2) < personalInjury) {
            return RuleResult.fail("第三者的人身损害累计金额小于第三者人员责任责任额度");
        }

        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "保单、保额是否合规";
    }
}
