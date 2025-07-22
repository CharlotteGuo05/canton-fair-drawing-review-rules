package com.tip.restful.rules.design.booththirdview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class StandardBoothSimpleSizeRuleTest {

    private StandardBoothSimpleSizeRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new StandardBoothSimpleSizeRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 类型不是简装，返回通过（带提示）
     */
    @Test
    public void testApply_NotSimple_ReturnsPassWithReason() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("特装");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
        assertEquals("不是简装", result.getReason());
    }

    /**
     * TC02: 长度或宽度超过2970，返回失败
     */
    @Test
    public void testApply_LengthOrWidthTooLarge_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.HEIGHT)).thenReturn("2400");
        when(mockContext.get(DataKeyConstant.LENGTH)).thenReturn("2980");
        when(mockContext.get(DataKeyConstant.WIDTH)).thenReturn("2900");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("长度(2980)、宽度(2900)均不得大于2970mm", result.getReason());
    }

    /**
     * TC03: 高度不等于2400，返回失败
     */
    @Test
    public void testApply_HeightNot2400_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.HEIGHT)).thenReturn("2500");
        when(mockContext.get(DataKeyConstant.LENGTH)).thenReturn("2970");
        when(mockContext.get(DataKeyConstant.WIDTH)).thenReturn("2970");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("单层展位高度(2500)不为2.4m", result.getReason());
    }

    /**
     * TC04: 所有数据符合要求，返回通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.HEIGHT)).thenReturn("2400");
        when(mockContext.get(DataKeyConstant.LENGTH)).thenReturn("2970");
        when(mockContext.get(DataKeyConstant.WIDTH)).thenReturn("2970");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }
}
