package com.tip.restful;

import java.util.ArrayList;
import java.util.List;


/**
 * 规则引擎，负责驱动规则集合依次执行。
 */
public class RuleEngine {
    private final List<Rule> rules;

    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
    }

    /**
     * 执行所有规则，返回结果列表。
     */
    public List<RuleResult> run(String imageId,RuleContext context) {
        List<RuleResult> results = new ArrayList<>();
        for (Rule rule : rules) {
            RuleResult result = rule.apply(imageId, context);
            results.add(result);
        }
        return results;
    }
}
