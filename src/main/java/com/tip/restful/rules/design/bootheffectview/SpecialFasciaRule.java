package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import javax.xml.crypto.Data;

public class SpecialFasciaRule implements Rule {


    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        //获取报图信息中的type
        String type = (String) context.get(DataKeyConstant.DRAWING_KIND);
        if ("特装".equals(type)) {
            //大模型公司名
            String fasciaCompanyName = (String) context.get(DataKeyConstant.FASCIA_COMPANY_NAME);
            //知识库公司名
            String companyName = (String)context.get(DataKeyConstant.COMPANY_NAME);
            if(companyName.contains(fasciaCompanyName) || companyName == fasciaCompanyName){
                return RuleResult.pass();
            }else{
                return RuleResult.fail("特装门楣上的企业名称("+fasciaCompanyName+")与参展商资料中的企业名称("+companyName+")不符");
            }
        }
        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "特装展位企业门楣要求";
    }
}
