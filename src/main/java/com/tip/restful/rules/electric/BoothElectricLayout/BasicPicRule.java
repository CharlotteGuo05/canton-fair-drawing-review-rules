package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import javax.xml.crypto.Data;

public class BasicPicRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        String electricBasic = (String) context.get(DataKeyConstant.ELECTRIC_BASIC);
        if(!electricBasic.equals("True") || electricBasic==null || electricBasic.isEmpty()) return RuleResult.fail("图片中未包含“配电系统”或“总开关电箱”或“客量”字段");
        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "基本图片要求";
    }
}
