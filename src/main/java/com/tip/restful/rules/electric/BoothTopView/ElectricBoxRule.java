package com.tip.restful.rules.electric.BoothTopView;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.electric.BoothTopViewResolver;

public class ElectricBoxRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        BoothTopViewResolver.boxInfo box =(BoothTopViewResolver.boxInfo)context.get(DataKeyConstant.ELECTRIC_BOX);

        //报图信息中的“报图资料-展示用电电箱规格”
        String targetBoxType = (String) context.get(DataKeyConstant.TARGET_BOX_TYPE);
        //数据库中的“展位配电系统图”中电箱规格
        String targetBoxTypeTwo = (String) context.get(DataKeyConstant.TARGET_BOX_TYPE_TWO);
        if(!box.getBoxType().equals(targetBoxType) || !box.getBoxType().equals(targetBoxTypeTwo)) return RuleResult.fail("电箱规格("+box.getBoxType()+")与报图资料中展示用电电箱规格("+targetBoxType+")或者展位配电系统图中的电箱规格("+targetBoxTypeTwo+")不匹配");


        //数据库中的“展位配电系统图”中电箱数量
        int targetBoxNumber = Integer.parseInt((String)context.get(DataKeyConstant.TARGET_BOX_NUMBER));
        if(box.getBoxNumber()!=targetBoxNumber) return RuleResult.fail("电箱数量("+box.getBoxNumber()+")与展位配电系统图中的数量("+targetBoxType+")不匹配");


        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "电箱要求";
    }

}
