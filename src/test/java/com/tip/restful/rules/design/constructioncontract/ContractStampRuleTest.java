package com.tip.restful.rules.design.constructioncontract;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.constructioncontract.ContractStampRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ContractStampRuleTest {

    private ContractStampRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new ContractStampRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * 正常情况：甲乙方信息正确，合同期限覆盖展会 + 筹展期
     */
    @Test
    public void testApply_Valid_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn("乙方公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方公司");

        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2024-10-18 00:00:00");
        when(mockContext.get(DataKeyConstant.EXHIBITION_END)).thenReturn("2024-10-20 00:00:00");

        Map<String, String> contractPeriod = new HashMap<>();
        contractPeriod.put("start", "2024-10-15 00:00:00");
        contractPeriod.put("end", "2024-10-20 00:00:00");
        when(mockContext.get(DataKeyConstant.CONTRACT_PERIOD)).thenReturn(contractPeriod);

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * 甲方信息不匹配
     */
    @Test
    public void testApply_InvalidPartyA_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn("错误甲方");
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn("乙方公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方公司");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("甲方信息("+mockContext.get(DataKeyConstant.PARTY_A)+")与企业名称("+mockContext.get(DataKeyConstant.COMPANY_NAME)+")不匹配", result.getReason());
    }

    /**
     * 乙方信息不匹配
     */
    @Test
    public void testApply_InvalidPartyB_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn("错误乙方");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方公司");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("乙方信息("+mockContext.get(DataKeyConstant.PARTY_B)+")与特装施工服务商名称("+mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)+")不匹配", result.getReason());
    }

    /**
     * 合同起始时间晚于要求时间
     */
    @Test
    public void testApply_ContractStartTooLate_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn("乙方公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方公司");

        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2024-10-18 00:00:00");
        when(mockContext.get(DataKeyConstant.EXHIBITION_END)).thenReturn("2024-10-20 00:00:00");

        Map<String, String> contractPeriod = new HashMap<>();
        contractPeriod.put("start", "2024-10-17 00:00:00"); // should be ≤ 10-15
        contractPeriod.put("end", "2024-10-20 00:00:00");
        when(mockContext.get(DataKeyConstant.CONTRACT_PERIOD)).thenReturn(contractPeriod);

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("合同期限(2024-10-17 00:00:00至2024-10-20 00:00:00)不足展会时间加上前三日筹展期", result.getReason());
    }

    /**
     * 合同结束时间早于展会结束时间
     */
    @Test
    public void testApply_ContractEndTooEarly_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn("乙方公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方公司");

        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2024-10-18 00:00:00");
        when(mockContext.get(DataKeyConstant.EXHIBITION_END)).thenReturn("2024-10-20 00:00:00");

        Map<String, String> contractPeriod = new HashMap<>();
        contractPeriod.put("start", "2024-10-15 00:00:00");
        contractPeriod.put("end", "2024-10-19 00:00:00"); // should be ≥ 10-20
        when(mockContext.get(DataKeyConstant.CONTRACT_PERIOD)).thenReturn(contractPeriod);

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("合同期限(2024-10-15 00:00:00至2024-10-19 00:00:00)不足展会时间加上前三日筹展期", result.getReason());
    }

    /**
     * 日期格式非法或字段缺失
     */
    @Test
    public void testApply_InvalidDateFormat_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn("乙方公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方公司");

        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("invalid_date");
        when(mockContext.get(DataKeyConstant.EXHIBITION_END)).thenReturn("2024-10-20 00:00:00");

        Map<String, String> contractPeriod = new HashMap<>();
        contractPeriod.put("start", "also_invalid");
        contractPeriod.put("end", "2024-10-20 00:00:00");
        when(mockContext.get(DataKeyConstant.CONTRACT_PERIOD)).thenReturn(contractPeriod);

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("合同时间格式错误或缺失", result.getReason());
    }
}
