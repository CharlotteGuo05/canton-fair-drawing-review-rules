package com.tip.restful.rules.design.tile;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.design.TileResolver;

import javax.xml.crypto.Data;
import java.util.List;

public class AreaRule implements Rule {

    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        List<TileResolver.Tile> tiles =(List<TileResolver.Tile>) context.get(DataKeyConstant.TILE);
        for(TileResolver.Tile tile : tiles){
            int tileArea = tile.getArea();
            if(tileArea > 2976800) return RuleResult.fail("图中单片瓷砖面积为"+tileArea+"mm²,超过了122cm*244 cm");
        }


        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "单片面积要求";
    }


}


