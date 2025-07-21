package com.tip.restful.rules.design.booththirdview;


import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;


/* 展位三视图-高度要求 */
public class HeightRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {

        //数据库中"是否搭建二层结构"
        int secondExhibition=Integer.parseInt((String)context.get(DataKeyConstant.SECOND_EXHIBITION));

        //展位号
        int boothId = Integer.parseInt((String)context.get(DataKeyConstant.BOOTH_ID));
        //期数
        int period = Integer.parseInt((String)context.get(DataKeyConstant.PERIOD));

        //从知识库中通过展位号+期数获取是否允许二层结构
        //return是否允许二层 --> return 1 是，0 否
        int allowSecond = Integer.parseInt((String)context.get(DataKeyConstant.ALLOW_SECOND));

        if(allowSecond == 0 && secondExhibition == 1) return RuleResult.fail("不允许搭建二层结构");


        String type = (String) context.get(DataKeyConstant.DRAWING_KIND);
        int frontHeight = Integer.parseInt((String) context.get(DataKeyConstant.FRONT_HEIGHT));
        int sideHeight = Integer.parseInt((String) context.get(DataKeyConstant.SIDE_HEIGHT));

        if("简装".equals(type)){
            if(frontHeight >= 2400 || sideHeight >= 2400) return RuleResult.fail("简装单层展位高度(正视图："+frontHeight+"mm 或侧视图："+sideHeight+"mm)大于等于2.4m");
        }

        if("特装".equals(type)){
            if(secondExhibition == 1){
                if(frontHeight != 6000 || sideHeight != 6000) return RuleResult.fail("特装双层展位高度(正视图："+frontHeight+"mm 或侧视图："+sideHeight+"mm)不等于6m");
            }else if(secondExhibition== 0){
                if(frontHeight != 2400 || sideHeight != 2400) return RuleResult.fail("特装单层展位高度(正视图："+frontHeight+"mm 或侧视图："+sideHeight+"mm)不等于2.4m");
            }
        }


        return RuleResult.pass();
    }
    @Override
    public String getName() {
        return "高度要求";
    }

}
