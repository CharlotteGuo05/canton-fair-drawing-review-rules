package com.tip.restful.rules.design.insurancereview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.insurancereview.NamePeriodRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class NamePeriodRuleTest {

    private NamePeriodRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new NamePeriodRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 所有信息匹配，保险时间包含展会时间，返回 pass
     */
    @Test
    public void testApply_AllMatch_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("ABC保险公司");
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("B01");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("XYZ特装");

        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2024-11-01 09:00:00");
        when(mockContext.get(DataKeyConstant.EXHIBITION_END)).thenReturn("2024-11-04 18:00:00");

        when(mockContext.get(DataKeyConstant.INSURANCE_COMPANY)).thenReturn("ABC保险公司");
        when(mockContext.get(DataKeyConstant.INSURANCE_BOOTH_ID)).thenReturn("B01");
        when(mockContext.get(DataKeyConstant.INSURANCE_PERIOD)).thenReturn(
                Arrays.asList("2024-10-29 00:00:00", "2024-11-05 00:00:00"));

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 被保险人不匹配
     */
    @Test
    public void testApply_InsuranceNameMismatch_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("ABC保险公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("XYZ特装");
        when(mockContext.get(DataKeyConstant.INSURANCE_COMPANY)).thenReturn("其他保险");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("被保险人信息("+mockContext.get(DataKeyConstant.INSURANCE_COMPANY)+")与企业名称或特装施工服务商名称不匹配", result.getReason());
    }

    /**
     * TC03: 展位号不匹配
     */
    @Test
    public void testApply_BoothIDMismatch_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("ABC保险公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("XYZ特装");
        when(mockContext.get(DataKeyConstant.INSURANCE_COMPANY)).thenReturn("ABC保险公司");

        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("A01");
        when(mockContext.get(DataKeyConstant.INSURANCE_BOOTH_ID)).thenReturn("B02");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("页面或附表清单中的展位号与基本信息中的展位号不匹配", result.getReason());
    }

    /**
     * TC04: 保险时间不足
     */
    @Test
    public void testApply_InsurancePeriodTooShort_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("ABC保险公司");
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("B01");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("XYZ特装");

        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2024-11-01 09:00:00");
        when(mockContext.get(DataKeyConstant.EXHIBITION_END)).thenReturn("2024-11-04 18:00:00");

        when(mockContext.get(DataKeyConstant.INSURANCE_COMPANY)).thenReturn("ABC保险公司");
        when(mockContext.get(DataKeyConstant.INSURANCE_BOOTH_ID)).thenReturn("B01");
        when(mockContext.get(DataKeyConstant.INSURANCE_PERIOD)).thenReturn(
                Arrays.asList("2024-10-31 00:00:00", "2024-11-03 23:59:59")); // 不包含展前3天和展后全部时间

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("保险期限（2024-10-31 00:00:00至2024-11-03 23:59:59）不足展会时间加前三日布展期", result.getReason());
    }
}
