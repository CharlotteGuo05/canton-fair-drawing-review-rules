package com.tip.restful.rules.design.basicinfo;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.basicinfo.BoothIDRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BoothIDRuleTest {

    private BoothIDRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new BoothIDRule();  // 使用的是 BoothIDRule，而不是 StandardBoothRule
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: COMPANY_ID 与 DRAWING_COMPANY_ID 相同，返回通过
     */
    @Test
    public void testApply_CompanyIDMatchesDrawingCompanyID_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("A123");
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn("2025");
        when(mockContext.get(DataKeyConstant.COMPANY_ID)).thenReturn("C001");
        when(mockContext.get(DataKeyConstant.DRAWING_COMPANY_ID)).thenReturn("C001");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: COMPANY_ID 与 DRAWING_COMPANY_ID 不同，返回失败
     */
    @Test
    public void testApply_CompanyIDNotMatchDrawingCompanyID_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("B456");
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn("2025");
        when(mockContext.get(DataKeyConstant.COMPANY_ID)).thenReturn("C001");
        when(mockContext.get(DataKeyConstant.DRAWING_COMPANY_ID)).thenReturn("C999");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("参展企业不与展位号对应", result.getReason());
    }
}
