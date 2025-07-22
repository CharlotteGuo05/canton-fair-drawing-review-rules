package com.tip.restful.rules.electric.ConstructionSafety;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.electric.ConstructionSafetyResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class StampRuleTest {

    private StampRule rule;
    private RuleContext mockContext;
    private ConstructionSafetyResolver.StampInfo mockStamp;

    @Before
    public void setUp() {
        rule = new StampRule();
        mockContext = Mockito.mock(RuleContext.class);
        mockStamp = Mockito.mock(ConstructionSafetyResolver.StampInfo.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
        mockStamp = null;
    }

    /**
     * TC01: 印章中甲方名称与参展商名称不符，返回失败
     */
    @Test
    public void testApply_PartyAMismatch_ReturnsFail() {
        when(mockStamp.getPartyA()).thenReturn("甲方A");
        when(mockStamp.getPartyB()).thenReturn("施工商B");
        when(mockStamp.getSignDate()).thenReturn(Arrays.asList("2024-10-01 10:00:00"));
        when(mockContext.get(DataKeyConstant.SAFETY_STAMP)).thenReturn(mockStamp);
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("公司X");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("施工商B");
        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2024-11-01 09:00:00");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("印章中甲方名称(甲方A)与参展商资料中的企业名称(公司X)不匹配", result.getReason());
    }

    /**
     * TC02: 印章中乙方名称与施工服务商名称不符，返回失败
     */
    @Test
    public void testApply_PartyBMismatch_ReturnsFail() {
        when(mockStamp.getPartyA()).thenReturn("公司X");
        when(mockStamp.getPartyB()).thenReturn("施工商错误");
        when(mockStamp.getSignDate()).thenReturn(Arrays.asList("2024-10-01 10:00:00"));
        when(mockContext.get(DataKeyConstant.SAFETY_STAMP)).thenReturn(mockStamp);
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("公司X");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("施工商B");
        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2024-11-01 09:00:00");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("印章中乙方名称(施工商错误)与特装施工服务商资料中的特装施工服务商名称(施工商B)不匹配", result.getReason());
    }

    /**
     * TC03: 合同签署日期晚于展会开始日期，返回失败
     */
    @Test
    public void testApply_SignDateTooLate_ReturnsFail() {
        when(mockStamp.getPartyA()).thenReturn("公司X");
        when(mockStamp.getPartyB()).thenReturn("施工商B");
        when(mockStamp.getSignDate()).thenReturn(Arrays.asList("2024-11-02 10:00:00"));
        when(mockContext.get(DataKeyConstant.SAFETY_STAMP)).thenReturn(mockStamp);
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("公司X");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("施工商B");
        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2024-11-01 09:00:00");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("合同签署日期(2024-11-02 10:00:00)不得晚于该年该期展会的最早开始日期(2024-11-01 09:00:00)", result.getReason());
    }

    /**
     * TC04: 所有字段正确，返回通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        when(mockStamp.getPartyA()).thenReturn("公司X");
        when(mockStamp.getPartyB()).thenReturn("施工商B");
        when(mockStamp.getSignDate()).thenReturn(Arrays.asList("2024-10-15 10:00:00"));
        when(mockContext.get(DataKeyConstant.SAFETY_STAMP)).thenReturn(mockStamp);
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("公司X");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("施工商B");
        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2024-11-01 09:00:00");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }
}
