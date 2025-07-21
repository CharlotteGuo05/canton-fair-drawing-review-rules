package com.tip.restful.rules.design.booththirdview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

/* 展位三视图-标摊简装尺寸 */
public class StandardBoothSimpleSizeRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        // 字段名需要改成英文
        String type = (String) context.get(DataKeyConstant.DRAWING_KIND);
        if (!"简装".equals(type)) return RuleResult.pass("不是简装");

        int frontHeight = Integer.parseInt((String) context.get(DataKeyConstant.FRONT_HEIGHT));
        int sideHeight = Integer.parseInt((String) context.get(DataKeyConstant.SIDE_HEIGHT));
        int length = Integer.parseInt((String) context.get(DataKeyConstant.LENGTH));
        int width = Integer.parseInt((String) context.get(DataKeyConstant.WIDTH));


        if (length > 2970 || width > 2970) {
            return RuleResult.fail("长度("+length+")、宽度("+width+")均不得大于2970mm");
        }

        if (frontHeight != 2400 || sideHeight != 2400) {
            return RuleResult.fail("单层展位高度("+frontHeight+")不为2.4m");
        }

        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "标摊简装尺寸";
    }
}
