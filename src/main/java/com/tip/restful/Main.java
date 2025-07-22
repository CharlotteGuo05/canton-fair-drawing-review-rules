package com.tip.restful;


import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.design.BoothThirdViewResolver;
import com.tip.restful.rules.design.booththirdview.HeightRule;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. 构造规则树
        Rule boothThirdViewRule = new CompositeRule("boothThirdViewRule",
//                Arrays.asList(new StandardBoothSimpleSizeRule())
                Arrays.asList(new HeightRule()));

        // 2. 构造每图片的context
        RuleContext context = new RuleContext(new BoothThirdViewResolver( null));
//        context.put(DataKeyConstant.DRAWING_KIND, "简装");

        //初始化imageId为null
        String imageId=null;

        // 如果需要统一读取信息，可以再这里直接 context.put

        //StandardBoothSimpleSizeRule
//        context.put(DataKeyConstant.DRAWING_KIND, "a");
//        context.put(DataKeyConstant.DRAWING_KIND, "简装");
//        context.put(DataKeyConstant.WIDTH, "2400");
//        context.put(DataKeyConstant.LENGTH, "2400");
//        context.put(DataKeyConstant.SIDE_HEIGHT, "2400");
//        context.put(DataKeyConstant.FRONT_HEIGHT, "2400");

        //HeightRule
        context.put(DataKeyConstant.SECOND_EXHIBITION, "1");
        context.put(DataKeyConstant.ALLOW_SECOND, "1");
        context.put(DataKeyConstant.DRAWING_KIND, "简装");
        context.put(DataKeyConstant.HEIGHT, "6000");


        // 执行
        RuleResult apply = boothThirdViewRule.apply(imageId, context);
        System.out.println(String.format("%s, %s", apply.isPass(), apply.getReason()));


    }

    public static void printResult(RuleResult result, int level) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("  ");
        }        System.out.println(indent + (result.isPass() ? "[通过] " : "[未通过] ") + result.getReason());
        if (result.getSubResults() != null) {
            for (RuleResult sub : result.getSubResults()) {
                printResult(sub, level + 1);
            }
        }
    }



    public static RuleResult applyRuleTree(Rule rule, Map<String, RuleContext> contextMap) {
        String imageId=null;
        if (rule instanceof CompositeRule) {
            CompositeRule composite = (CompositeRule) rule;
            List<RuleResult> subResults = new ArrayList<>();
            boolean allPass = true;
            for (Rule subRule : composite.getSubRules()) {
                // 递归传递context
                RuleContext context = contextMap.get(subRule.getName());
                RuleResult result = (context != null)
                        ? subRule.apply(imageId, context)
                        : applyRuleTree(subRule, contextMap);
                subResults.add(result);
                if (!result.isPass()) allPass = false;
            }
            return new RuleResult(allPass, composite.getName() + (allPass ? "全部通过" : "未全部通过"), subResults);
        } else {
            // 叶子规则，找到context
            RuleContext context = contextMap.get(rule.getName());
            if (context == null) throw new RuntimeException("缺少context: " + rule.getName());
            return rule.apply(imageId, context);
        }
    }


}
