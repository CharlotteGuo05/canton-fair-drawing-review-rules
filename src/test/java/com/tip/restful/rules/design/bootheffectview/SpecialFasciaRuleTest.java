package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.bootheffectview.SpecialFasciaRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SpecialFasciaRuleTest {

    private SpecialFasciaRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new SpecialFasciaRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        // 清理 mock
    }

    /**
     * TC01: 类型不是特装，应直接通过
     */
    @Test
    public void testApply_NotSpecialBooth_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");

        RuleResult result = rule.apply(null,mockContext);

        assertTrue(result.isPass());
    }

    /**
     * TC02: 特装类型，公司名一致，应通过
     */
    @Test
    public void testApply_SpecialBooth_NameMatches_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("特装");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("上海某某公司有限公司");
        when(mockContext.get(DataKeyConstant.FASCIA_COMPANY_NAME)).thenReturn("某某公司");

        RuleResult result = rule.apply(null,mockContext);

        assertTrue(result.isPass());
    }

    /**
     * TC03: 特装类型，公司名不一致，应失败
     */
    @Test
    public void testApply_SpecialBooth_NameMismatch_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("特装");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("北京其他公司");
        when(mockContext.get(DataKeyConstant.FASCIA_COMPANY_NAME)).thenReturn("不相关公司");

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("特装门楣上的企业名称("+mockContext.get(DataKeyConstant.FASCIA_COMPANY_NAME)+")与参展商资料中的企业名称("+mockContext.get(DataKeyConstant.COMPANY_NAME)+")不符", result.getReason());
    }
}
