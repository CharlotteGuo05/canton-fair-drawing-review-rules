package com.tip.restful.rules.design.insurancereview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class InsuranceCompanyRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        String insurance = (String)context.get(DataKeyConstant.INSURANCE_COMPANY);
        String targetInsurance = (String)context.get(DataKeyConstant.COMPANY_NAME);

        if(insurance.equals(targetInsurance))
            return RuleResult.pass();

        return RuleResult.fail("保险清单中企业名称("+insurance+")与参展商资料-企业名称("+targetInsurance+")不匹配");
    }

    @Override
    public String getName() {
        return "保险清单参展企业是否合规";
    }
}
