package com.tip.restful;

import java.util.ArrayList;
import java.util.List;


/**
 * 组合规则，实现大审核项-子审核项结构。
 * 只要有一个子规则不通过，整体就不通过。
 */
public class CompositeRule implements Rule {
    private final String name;
    private final List<Rule> subRules;

    public CompositeRule(String name, List<Rule> subRules) {
        this.name = name;
        this.subRules = subRules;
    }

    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        List<RuleResult> subResults = new ArrayList<>();
        boolean allPass = true;
        StringBuilder reasons = new StringBuilder();

        for (Rule rule : subRules) {
            RuleResult result = rule.apply(imageId, context);
            subResults.add(result);
            if (!result.isPass()) {
                allPass = false;
                reasons.append("[").append(rule.getName()).append("] ")
                        .append(result.getReason()).append("; ");
            }
        }

        if (allPass) {
            return RuleResult.pass("所有子项均通过");
        } else {
            return RuleResult.fail("以下子项未通过: " + reasons.toString());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取所有子规则。
     */
    public List<Rule> getSubRules() {
        return subRules;
    }
}
