package com.tip.restful;

/**
 * 审核规则接口，所有审核项实现该接口。
 */
public interface Rule {
    /**
     * 执行规则审核逻辑。
     * @param context 规则上下文
     * @return 规则执行结果
     */
    RuleResult apply(String imageId, RuleContext context);

    /**
     * 获取规则名称。
     */
    String getName();
}
