package com.tip.restful.rules.design.tile;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class TileStandardBoothRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        String type = (String) context.get(DataKeyConstant.DRAWING_KIND);
        String hasBooth = (String)context.get(DataKeyConstant.HAS_BOOTH);

        String tileCompanyName = (String) context.get(DataKeyConstant.TILE_COMPANY_NAME);
        if("简装".equals(type)){
            if("False".equals(hasBooth)){
                //哪里含有？
                if(tileCompanyName.contains("大会统一装搭")){
                    return RuleResult.pass();
                }else{
                    return RuleResult.fail("未含有大会统一装搭");
                }
            }

        }
        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "标摊简装展板";
    }
}
