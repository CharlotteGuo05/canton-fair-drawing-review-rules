package com.tip.restful.rules.design.booththirdview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;


/* 展位三视图-长宽预留需求 */
public class LengthWidthRule implements Rule {
   @Override
    public RuleResult apply(String imageId,RuleContext context) {
        //拿展位号+期数 去知识库 拿 是否为独立展位

       int boothId = Integer.parseInt((String)context.get(DataKeyConstant.BOOTH_ID));
       int period = Integer.parseInt((String)context.get(DataKeyConstant.PERIOD));
       int isIndependent = Integer.parseInt((String)context.get(DataKeyConstant.MODEL_EXTRACT_INFO));

       if(isIndependent==1) return RuleResult.pass();

       if(isIndependent!=1){

           int length = Integer.parseInt((String) context.get(DataKeyConstant.LENGTH));
           int width = Integer.parseInt((String) context.get(DataKeyConstant.WIDTH));

           int targetLength = Integer.parseInt((String) context.get(DataKeyConstant.TARGET_LENGTH));
           int targetWidth = Integer.parseInt((String) context.get(DataKeyConstant.TARGET_WIDTH));

           if(targetLength <= 10000 ){
               if(length!=(targetLength-(targetLength/3000)*30)) return RuleResult.fail("10米以内的展位长度没有每3m预留30mm");
           }else if(targetLength>10000){
               if(length!=(targetLength-100)) return RuleResult.fail("10米以上的展位长度没有预留100mm");
           }

           if(targetWidth <= 10000 ){
                if(width!=(targetWidth-(targetWidth/3000)*30)) return RuleResult.fail("10米以内的展位宽度没有每3m预留30mm");
            }else if(targetWidth>10000){
               if(width!=(targetWidth-100)) return RuleResult.fail("10米以上的展位宽度没有预留100mm");
           }


       }

        return RuleResult.pass();
    }
    @Override
    public String getName() {
        return "长宽预留需求";
    }

}
