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
            if(width > 2500) return RuleResult.fail("宽高不符合要求");
        }else if(2500 <= width && width <= 3000){
            if(height > 2500) return RuleResult.fail("宽高不符合要求");
        }
        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "展柜或展架尺寸要求";
    }
}
