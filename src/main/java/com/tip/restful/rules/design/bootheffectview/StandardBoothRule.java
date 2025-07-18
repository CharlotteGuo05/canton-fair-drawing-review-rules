package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class StandardBoothRule implements Rule {

    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        String type = (String) context.get(DataKeyConstant.DRAWING_KIND);
        if ("简装".equals(type)) {
            //大模型公司名
            String fasciaCompanyName = (String) context.get(DataKeyConstant.FASCIA_COMPANY_NAME);
            //知识库公司名
            String companyName = (String)context.get(DataKeyConstant.COMPANY_NAME);
            if(companyName.contains(fasciaCompanyName) || companyName == fasciaCompanyName){
                return RuleResult.pass();
            }else{
                return RuleResult.fail("特装门楣企业名称不符");
            }
        }
        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "标摊展位企业名要求";
    }
}
