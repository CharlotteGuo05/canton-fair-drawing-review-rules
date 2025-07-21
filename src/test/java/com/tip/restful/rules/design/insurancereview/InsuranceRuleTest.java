package com.tip.restful.rules.design.insurancereview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.insurancereview.InsuranceRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class InsuranceRuleTest {

    private InsuranceRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new InsuranceRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 所有条件满足，应通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.INSURANCE_POLICY)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.INSURANCE_AMOUNT)).thenReturn(Arrays.asList("10000"));
        when(mockContext.get(DataKeyConstant.ACCUMULATED_AMOUNT)).thenReturn(Arrays.asList(9000, 7000, 8000));
        when(mockContext.get(DataKeyConstant.PREMISES_LIABILITY)).thenReturn("10000");
        when(mockContext.get(DataKeyConstant.EMPLOYER_LIABILITY)).thenReturn("8000");
        when(mockContext.get(DataKeyConstant.PERSONAL_INJURY)).thenReturn("9000");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 人身伤害字段为 False，应失败
     */
    @Test
    public void testApply_InsurancePolicyFalse_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.INSURANCE_POLICY)).thenReturn("False");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("未含有人身伤害约定无免赔或免赔说明含义内容", result.getReason());
    }

    /**
     * TC03: 每次赔付额为空，应失败
     */
    @Test
    public void testApply_InsuranceAmountNull_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.INSURANCE_POLICY)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.INSURANCE_AMOUNT)).thenReturn(null);

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("未含每次赔付额", result.getReason());
    }

    /**
     * TC04: 累计赔付额少于三项，应失败
     */
    @Test
    public void testApply_AccumulatedAmountIncomplete_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.INSURANCE_POLICY)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.INSURANCE_AMOUNT)).thenReturn(Arrays.asList("10000"));
        when(mockContext.get(DataKeyConstant.ACCUMULATED_AMOUNT)).thenReturn(Arrays.asList(10000));  // only one

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("未含有累计赔付额度", result.getReason());
    }

    /**
     * TC05: 场地责任累计金额不足，应失败
     */
    @Test
    public void testApply_PremisesLiabilityTooLow_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.INSURANCE_POLICY)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.INSURANCE_AMOUNT)).thenReturn(Arrays.asList("10000"));
        when(mockContext.get(DataKeyConstant.ACCUMULATED_AMOUNT)).thenReturn(Arrays.asList(9000, 7000, 8000));
        when(mockContext.get(DataKeyConstant.PREMISES_LIABILITY)).thenReturn("5000");
        when(mockContext.get(DataKeyConstant.EMPLOYER_LIABILITY)).thenReturn("8000");
        when(mockContext.get(DataKeyConstant.PERSONAL_INJURY)).thenReturn("9000");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("场地责任累计金额大于场地责任额度", result.getReason());
    }
}
