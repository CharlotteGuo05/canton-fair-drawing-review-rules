package com.tip.restful.rules.design.tile;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import javax.xml.crypto.Data;

public class AreaRule implements Rule {

    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        Tile tileArea =(Tile) context.get(DataKeyConstant.TILE_AREA);
        if(tileArea.getArea() > 2976800) return RuleResult.fail("图中单片瓷砖面积为"+tileArea.getArea()+"mm²,超过了122cm*244 cm");

        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "单片面积要求";
    }

    public class Tile{
        public int width;
        public int length;

        public Tile(int width, int length) {
            this.width = width;
            this.length = length;
        }

        public int getArea() {
            return width * length;
        }

    }
}


