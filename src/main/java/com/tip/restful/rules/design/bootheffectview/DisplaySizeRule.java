package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class DisplaySizeRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {

        int width = -1;
        int height = -1;

        //大模型提取：宽，高
        width = Integer.parseInt((String)context.get(DataKeyConstant.DISPLAY_WIDTH));
        height = Integer.parseInt((String)context.get(DataKeyConstant.DISPLAY_HEIGHT));

        //如果提取出来的宽和高为空，则通过
        if(width == -1 && height == -1){
            return RuleResult.pass();
        }else if(2500 <= height && height <= 3000){
            if(width > 2500) return RuleResult.fail("高("+height+"mm)为2500-3000mm, 宽("+width+"mm)不得大于2500m");
        }else if(2500 <= width && width <= 3000){
            if(height > 2500) return RuleResult.fail("宽("+width+"mm)为2500m-3000m, 高("+height+"mm)不得大于2500m");
        }
        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "展柜或展架尺寸要求";
    }
}
