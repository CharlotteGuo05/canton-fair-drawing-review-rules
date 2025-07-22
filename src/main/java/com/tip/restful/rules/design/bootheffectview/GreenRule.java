package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.design.BoothEffectView;

import java.util.List;

public class GreenRule implements Rule {

    @Override
    public RuleResult apply(String imageId, RuleContext context) {

        String type = (String)context.get(DataKeyConstant.GREEN_TYPE);

        //提取的材料
        List<BoothEffectView.Material> materials = (List<BoothEffectView.Material>) context.get(DataKeyConstant.MATERIAL_LIST);

        //禁止材料
        String forbidden = (String) context.get(DataKeyConstant.FORBIDDEN_MATERIAL);
        String isForbidden = (String) context.get(DataKeyConstant.IS_FORBIDDEN); //检索知识库后第二次过大模型的结果
        //木质材料
        String wood = (String)context.get(DataKeyConstant.WOOD_MATERIAL);
        String isWood = (String) context.get(DataKeyConstant.IS_WOOD); //检索知识库后第二次过大模型的结果

        //确认知识库中匹配的具体字段是什么
        if ("A类".equals(type)) {
                if (isForbidden.equals("True") || isWood.equals("True")) {
                    return RuleResult.fail("出现禁止材料");
                }

        }else if("B类".equals(type)){
            for (BoothEffectView.Material material : materials){
                if("True".equals(material.isMovable())){
                    if(material.getLength()*material.getWidth()>7500000) return RuleResult.fail("可移动材料面积不可以大于7500mm");
                }
                if(isForbidden.equals("True")) return RuleResult.fail("出现禁止材料");
            }
        }


        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "效果图绿色核查";
    }

}
