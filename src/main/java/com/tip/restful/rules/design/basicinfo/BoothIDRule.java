package com.tip.restful.rules.design.basicinfo;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class BoothIDRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        //通过展位号、届数，通过xx接口查询该最新的展商的企业ID
        String boothID = (String) context.get(DataKeyConstant.BOOTH_ID);
        String period = (String) context.get(DataKeyConstant.PERIOD);
        String companyID = (String)context.get(DataKeyConstant.COMPANY_ID);

        //报图单中的企业ID

        String DrawingCompanyID = (String) context.get(DataKeyConstant.DRAWING_COMPANY_ID);

        if(DrawingCompanyID.equals(companyID)) return RuleResult.pass();


        return RuleResult.fail("参展企业不与展位号对应");
    }

    @Override
    public String getName() {
        return "参展企业是否与展位号对应";
    }
}
