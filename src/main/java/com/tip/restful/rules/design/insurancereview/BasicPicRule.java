package com.tip.restful.rules.design.insurancereview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import java.lang.reflect.Array;
import java.util.List;

public class BasicPicRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        List<String> hasInsurance = (List<String>)context.get(DataKeyConstant.HAS_INSURANCE);
        String hasStamp = (String)context.get(DataKeyConstant.HAS_STAMP);

       for(String insurance : hasInsurance) {
           if(insurance.equals("True")) {
               return RuleResult.pass();
           }else{
               return RuleResult.fail("未含有保险单号和被保险人");
           }
       }

       if(hasStamp.equals("True")){return RuleResult.pass();}else{return RuleResult.fail("未盖章");}

    }

    @Override
    public String getName() {
        return "基本图片要求";
    }
}
