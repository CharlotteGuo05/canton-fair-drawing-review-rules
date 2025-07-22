package com.tip.restful.rules.design.insurancereview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

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

    @Test
    public void testApply_MissingPolicy_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.INSURANCE_POLICY)).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("未含有人身伤害约定无免赔或免赔说明含义内容", result.getReason());
    }

    @Test
    public void testApply_MissingAmount_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.INSURANCE_POLICY)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.INSURANCE_AMOUNT)).thenReturn(null);

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("未含每次赔付额", result.getReason());
    }

    @Test
    public void testApply_AccumulatedAmountMissing_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.INSURANCE_POLICY)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.INSURANCE_AMOUNT)).thenReturn(Collections.singletonList("100"));
        when(mockContext.get(DataKeyConstant.ACCUMULATED_AMOUNT)).thenReturn(null);

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("未含有累计赔付额度", result.getReason());
    }

    @Test
    public void testApply_PremisesLiabilityExceed_ReturnsFail() {
        setupCommonSuccessMock();
        Map<String, String> accumulated = new HashMap<>();
        accumulated.put("premise", "10000");
        accumulated.put("employer", "1000");
        accumulated.put("injury", "1000");
        when(mockContext.get(DataKeyConstant.ACCUMULATED_AMOUNT)).thenReturn(accumulated);
        when(mockContext.get(DataKeyConstant.PREMISES_LIABILITY)).thenReturn("5000");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("场地责任累计金额(10000万元)大于场地责任额度(5000万元)", result.getReason());
    }

    @Test
    public void testApply_EmployerLiabilityExceed_ReturnsFail() {
        setupCommonSuccessMock();
        Map<String, String> accumulated = new HashMap<>();
        accumulated.put("premise", "1000");
        accumulated.put("employer", "9999");
        accumulated.put("injury", "1000");
        when(mockContext.get(DataKeyConstant.ACCUMULATED_AMOUNT)).thenReturn(accumulated);
        when(mockContext.get(DataKeyConstant.EMPLOYER_LIABILITY)).thenReturn("3000");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("雇员责任累计金额(9999万元)大于雇员责任额度(3000万元)", result.getReason());
    }

    @Test
    public void testApply_InjuryLiabilityExceed_ReturnsFail() {
        setupCommonSuccessMock();
        Map<String, String> accumulated = new HashMap<>();
        accumulated.put("premise", "1000");
        accumulated.put("employer", "1000");
        accumulated.put("injury", "4000");
        when(mockContext.get(DataKeyConstant.ACCUMULATED_AMOUNT)).thenReturn(accumulated);
        when(mockContext.get(DataKeyConstant.PERSONAL_INJURY)).thenReturn("3000");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("第三者的人身损害累计金额(4000万元)大于第三者人员责任责任额度(3000万元)", result.getReason());
    }

    @Test
    public void testApply_AllValid_ReturnsPass() {
        setupCommonSuccessMock();
        Map<String, String> accumulated = new HashMap<>();
        accumulated.put("premise", "1000");
        accumulated.put("employer", "1000");
        accumulated.put("injury", "1000");
        when(mockContext.get(DataKeyConstant.ACCUMULATED_AMOUNT)).thenReturn(accumulated);

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    private void setupCommonSuccessMock() {
        when(mockContext.get(DataKeyConstant.INSURANCE_POLICY)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.INSURANCE_AMOUNT)).thenReturn(Collections.singletonList("12345"));
        when(mockContext.get(DataKeyConstant.PREMISES_LIABILITY)).thenReturn("10000");
        when(mockContext.get(DataKeyConstant.EMPLOYER_LIABILITY)).thenReturn("10000");
        when(mockContext.get(DataKeyConstant.PERSONAL_INJURY)).thenReturn("10000");
    }
}
