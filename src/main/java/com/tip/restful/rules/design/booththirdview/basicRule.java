package com.tip.restful.rules.design.booththirdview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;


/* 展位三视图-基本图片要求 */
public class basicRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {

        //提取大模型结果（True,True,True)
        //如果大模型结果的数组有一项是false，则fail
        boolean hasFrontViews = (boolean) context.get(DataKeyConstant.HAS_FRONT_VIEWS);
        boolean hasSideViews = (boolean) context.get(DataKeyConstant.HAS_SIDE_VIEWS);
        boolean hasTopViews = (boolean) context.get(DataKeyConstant.HAS_TOP_VIEWS);

        if(!hasFrontViews) return RuleResult.fail("没有正面视图");
        if(!hasSideViews) return RuleResult.fail("没有侧面视图");
        if(!hasTopViews) return RuleResult.fail("没有顶视图");


        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "基本图片要求";
    }
}
