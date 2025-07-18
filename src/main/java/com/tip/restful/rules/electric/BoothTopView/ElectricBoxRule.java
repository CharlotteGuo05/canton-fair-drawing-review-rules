package com.tip.restful.rules.electric.BoothTopView;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class ElectricBoxRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        boxInfo box=(boxInfo)context.get(DataKeyConstant.ELECTRIC_BOX);

        //报图信息中的“报图资料-展示用电电箱规格”
        String targetBoxType = (String) context.get(DataKeyConstant.TARGET_BOX_TYPE);
        if(!box.getBoxType().equals(targetBoxType)) return RuleResult.fail("电箱规格不匹配");


        //数据库中的“展位配电系统图”中电箱数量
        int targetBoxNumber = Integer.parseInt((String)context.get(DataKeyConstant.TARGET_BOX_NUMBER));
        if(box.getBoxNumber()!=targetBoxNumber) return RuleResult.fail("电箱数量不匹配");


        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "电箱要求";
    }

    public class boxInfo{
        public int boxNumber;
        public String boxType;

        public int getBoxNumber(){
            return boxNumber;
        }

        public String getBoxType(){
            return boxType;
        }
    }
}
