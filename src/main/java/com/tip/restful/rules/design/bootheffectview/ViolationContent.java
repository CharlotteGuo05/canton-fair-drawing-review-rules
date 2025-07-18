package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class ViolationContent implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {

        //效果图图片内容
        String graphContent = (String) context.get(DataKeyConstant.GRAPH_CONTENT);
        //知识库-违规关键词、违规图形名称
        String violationContent = (String) context.get(DataKeyConstant.VIOLATION);

        //====大模型匹配====


        //知识库-荣誉称号
        String honorContent = (String) context.get(DataKeyConstant.HONOR);
        //荣誉称号证明图片
        String honorGraph = (String) context.get(DataKeyConstant.HONOR_GRAPH);

        //====大模型匹配====

        if(graphContent.contains("若与事实不符或引发相关纠纷，本参展企业对此负全部责任") && honorGraph != null){
            return RuleResult.pass();
        }else{
            return RuleResult.fail("不符合荣誉要求");
        }

    }

    @Override
    public String getName() {
        return "违规内容核查";
    }
}
