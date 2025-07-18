package com.tip.restful;

import java.util.List;

/**
 * 规则执行结果。
 */
public class RuleResult {
    // 是否通过
    private final boolean pass;
    // 审核理由
    private final String reason;
    // 组合规则用
    private final List<RuleResult> subResults;


    public RuleResult(boolean pass, String reason) {
        this(pass, reason, null);
    }

    public RuleResult(boolean pass, String reason, List<RuleResult> subResults) {
        this.pass = pass;
        this.reason = reason;
        this.subResults = subResults;
    }

    public static RuleResult pass() {
        return new RuleResult(true, "");
    }
    public static RuleResult pass(String reason) {
        return new RuleResult(true, reason);
    }

    public static RuleResult fail(String reason) {
        return new RuleResult(false, reason);
    }

    // getter
    public boolean isPass() { return pass; }
    public String getReason() { return reason; }
    public List<RuleResult> getSubResults() { return subResults; }
}
