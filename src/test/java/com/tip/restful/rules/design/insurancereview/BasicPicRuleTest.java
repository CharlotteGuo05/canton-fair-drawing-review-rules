package com.tip.restful.rules.design.insurancereview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.insurancereview.BasicPicRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BasicPicRuleTest {

    private BasicPicRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new BasicPicRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 有一个保险为True，返回通过
     */
    @Test
    public void testApply_InsuranceTrue_ReturnsPass() {
        List<String> insuranceList = Arrays.asList("True", "False");
        when(mockContext.get(DataKeyConstant.HAS_INSURANCE)).thenReturn(insuranceList);
        when(mockContext.get(DataKeyConstant.HAS_STAMP)).thenReturn("False");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 所有保险都为False，返回失败
     */
    @Test
    public void testApply_AllInsuranceFalse_ReturnsFail() {
        List<String> insuranceList = Arrays.asList("False", "False");
        when(mockContext.get(DataKeyConstant.HAS_INSURANCE)).thenReturn(insuranceList);
        when(mockContext.get(DataKeyConstant.HAS_STAMP)).thenReturn("False");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("未含有保险单号和被保险人", result.getReason());
    }

    /**
     * TC03: 没有保险，但有盖章，返回通过
     */
    @Test
    public void testApply_NoInsuranceButStamped_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.HAS_INSURANCE)).thenReturn(Collections.emptyList());
        when(mockContext.get(DataKeyConstant.HAS_STAMP)).thenReturn("True");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC04: 没有保险，也未盖章，返回失败
     */
    @Test
    public void testApply_NoInsuranceAndNoStamp_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.HAS_INSURANCE)).thenReturn(Collections.emptyList());
        when(mockContext.get(DataKeyConstant.HAS_STAMP)).thenReturn("False");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("未盖章", result.getReason());
    }
}
