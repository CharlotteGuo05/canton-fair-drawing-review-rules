package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import java.util.List;

public class GreenRule implements Rule {

    @Override
    public RuleResult apply(String imageId, RuleContext context) {

        String type = (String)context.get(DataKeyConstant.GREEN_TYPE);

        //提取的材料
        List<Material> materials = (List<Material>) context.get(DataKeyConstant.MATERIAL_LIST);

        //禁止材料
        String forbidden = (String) context.get(DataKeyConstant.FORBIDDEN_MATERIAL);
        //木质材料
        String wood = (String)context.get(DataKeyConstant.WOOD_MATERIAL);

        //确认知识库中匹配的具体字段是什么
        if ("A类".equals(type)) {
            for (Material material : materials) {
                if (forbidden.contains(material.getName()) || wood.contains(material.getName())) {
                    return RuleResult.fail("不得出现该类材料:"+material.getName());
                }
            }
        }else if("B类".equals(type)){
            for (Material material : materials){
                if("True".equals(material.isMovable())){
                    if(material.getLength()*material.getWidth()>7500000) return RuleResult.fail("可移动材料面积不可以大于7500mm");
                }
                if(forbidden.contains(material.getName())) return RuleResult.fail("不得出现该类材料:"+material.getName());
            }
        }


        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "效果图绿色核查";
    }
    public class Material{
        private String name;
        private String movable;
        private int width;
        private int length;

        public Material(String name, String movable, int width, int length) {
            this.name = name;
            this.movable = movable;
            this.width = width;
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public String isMovable() {
            return movable;
        }

        public int getWidth() {
            return width;
        }

        public int getLength() {
            return length;
        }
    }
}
