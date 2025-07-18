package com.tip.restful.rules.electric.BoothTopView;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
     * TC01: 图片含义字段为 "True"，返回通过
     */
    @Test
    public void testApply_BasicRequirementTrue_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.ELECTRIC_TOP_REQUIREMENT)).thenReturn("True");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 图片含义字段为 "False"，返回失败
     */
    @Test
    public void testApply_BasicRequirementFalse_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.ELECTRIC_TOP_REQUIREMENT)).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("图片未包含电气平面图或电路图的含义字段", result.getReason());
    }

    /**
     * TC03: 图片含义字段为 null，返回失败（防御性测试）
     */
    @Test(expected = NullPointerException.class)
    public void testApply_BasicRequirementNull_ThrowsException() {
        when(mockContext.get(DataKeyConstant.ELECTRIC_TOP_REQUIREMENT)).thenReturn(null);
        rule.apply(null, mockContext); // 预期抛出 NullPointerException
    }
}
