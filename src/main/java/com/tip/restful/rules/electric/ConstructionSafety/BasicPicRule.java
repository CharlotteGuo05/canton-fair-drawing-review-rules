package com.tip.restful.rules.electric.ConstructionSafety;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class BasicPicRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        String basicRequirement = (String)context.get(DataKeyConstant.CONSTRUCTION_SAFETY_REQUIREMENT);
        if (basicRequirement == null || basicRequirement.isEmpty() || !"True".equals(basicRequirement)) {
            return RuleResult.fail("图片未包含“广交会特装施工安全责任承诺书”字段");
        }


        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "基本图片要求";
    }
}
