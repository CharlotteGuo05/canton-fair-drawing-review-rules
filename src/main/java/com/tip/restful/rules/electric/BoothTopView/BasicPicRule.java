package com.tip.restful.rules.electric.BoothTopView;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class BasicPicRule implements Rule {

    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        String basicRequirement = (String)context.get(DataKeyConstant.ELECTRIC_TOP_REQUIREMENT);
        if(!basicRequirement.equals("True")) return RuleResult.fail("图片未包含电气平面图或电路图的含义字段");
        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "基本图片要求";
    }
}
