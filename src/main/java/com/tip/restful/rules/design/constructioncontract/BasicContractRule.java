package com.tip.restful.rules.design.constructioncontract;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class BasicContractRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        String isContract = (String)context.get(DataKeyConstant.IS_CONTRACT);
        String partyA = (String)context.get(DataKeyConstant.PARTY_A);
        String partyB = (String)context.get(DataKeyConstant.PARTY_B);

        if(!isContract.equals("True")) return RuleResult.fail("图片内容不为合同");
        if(partyA == null || partyB == null || partyA.isEmpty() || partyB.isEmpty()) return RuleResult.fail("图片未含有甲方或乙方消息");



        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "基本图片要求";
    }
}
