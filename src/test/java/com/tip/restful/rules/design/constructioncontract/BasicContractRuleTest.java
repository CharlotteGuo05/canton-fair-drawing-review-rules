package com.tip.restful.rules.design.constructioncontract;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.constructioncontract.BasicContractRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BasicContractRuleTest {

    private BasicContractRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new BasicContractRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 所有信息齐全，且为合同，返回通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.IS_CONTRACT)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn("乙方公司");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 不是合同，返回失败
     */
    @Test
    public void testApply_IsNotContract_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.IS_CONTRACT)).thenReturn("False");
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn("乙方公司");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("图片内容不为合同", result.getReason());
    }

    /**
     * TC03: 合同但甲方缺失，返回失败
     */
    @Test
    public void testApply_MissingPartyA_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.IS_CONTRACT)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn(null);
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn("乙方公司");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("图片未含有甲方或乙方消息", result.getReason());
    }

    /**
     * TC04: 合同但乙方缺失，返回失败
     */
    @Test
    public void testApply_MissingPartyB_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.IS_CONTRACT)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.PARTY_A)).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.PARTY_B)).thenReturn(null);

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("图片未含有甲方或乙方消息", result.getReason());
    }
}
