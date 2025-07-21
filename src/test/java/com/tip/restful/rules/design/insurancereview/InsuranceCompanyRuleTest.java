package com.tip.restful.rules.design.insurancereview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.insurancereview.InsuranceCompanyRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class InsuranceCompanyRuleTest {

    private InsuranceCompanyRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new InsuranceCompanyRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 保险公司名称与目标名称一致，应返回通过
     */
    @Test
    public void testApply_InsuranceMatchesTarget_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.INSURANCE_COMPANY)).thenReturn("中国保险有限公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("中国保险有限公司");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 保险公司名称与目标名称不一致，应返回失败
     */
    @Test
    public void testApply_InsuranceNotMatchTarget_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.INSURANCE_COMPANY)).thenReturn("中国保险有限公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("中国人寿");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("保险清单中企业名称("+mockContext.get(DataKeyConstant.INSURANCE_COMPANY)+")与参展商资料-企业名称("+mockContext.get(DataKeyConstant.COMPANY_NAME)+")不匹配", result.getReason());
    }
}
