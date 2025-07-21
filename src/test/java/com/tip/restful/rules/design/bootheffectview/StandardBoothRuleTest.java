package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.bootheffectview.StandardBoothRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class StandardBoothRuleTest {

    private StandardBoothRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new StandardBoothRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        // cleanup if needed
    }

    /**
     * TC01: 非“简装”类型，应直接通过
     */
    @Test
    public void testApply_NotStandardBooth_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("特装");

        RuleResult result = rule.apply(null,mockContext);

        assertTrue(result.isPass());
    }

    /**
     * TC02: 简装类型，公司名一致，应通过
     */
    @Test
    public void testApply_StandardBooth_MatchingCompanyName_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.FASCIA_COMPANY_NAME)).thenReturn("ABC Corp");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("ABC Corp");

        RuleResult result = rule.apply(null,mockContext);

        assertTrue(result.isPass());
    }

    /**
     * TC03: 简装类型，知识库公司名包含大模型名，应通过
     */
    @Test
    public void testApply_StandardBooth_CompanyNameContainsFasciaName_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.FASCIA_COMPANY_NAME)).thenReturn("ABC");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("ABC Corporation");

        RuleResult result = rule.apply(null,mockContext);

        assertTrue(result.isPass());
    }

    /**
     * TC04: 简装类型，公司名不匹配，应失败
     */
    @Test
    public void testApply_StandardBooth_NameMismatch_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.FASCIA_COMPANY_NAME)).thenReturn("XYZ Ltd");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("ABC Corp");

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("简装门楣上的企业名称("+mockContext.get(DataKeyConstant.FASCIA_COMPANY_NAME)+")与参展商资料中的企业名称("+mockContext.get(DataKeyConstant.COMPANY_NAME)+")不符", result.getReason());
    }
}
