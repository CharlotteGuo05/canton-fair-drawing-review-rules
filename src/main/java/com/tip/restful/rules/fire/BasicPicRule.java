package com.tip.restful.rules.fire;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import java.util.List;

public class BasicPicRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        //大模型
        String materialRequirement = (String) context.get(DataKeyConstant.FIRE_MATERIAL_REQUIREMENT);
        List<String> materialList = (List<String>) context.get(DataKeyConstant.FIRE_MATERIAL_LIST);

        //知识库-阻燃/难燃材料
        String flameProofMaterial = (String) context.get(DataKeyConstant.FLAME_PROOF_MATERIAL);


        if(!materialRequirement.equals("True")) return RuleResult.fail("图片未包含搭建材料全部使用B1级难燃材料字段");
        for(String material:materialList){
            if(!flameProofMaterial.contains(material)){
                return RuleResult.fail("材料("+material+")不在阻燃/难燃材料清单中");
            }
        }




        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "基本图片要求";
    }
}
