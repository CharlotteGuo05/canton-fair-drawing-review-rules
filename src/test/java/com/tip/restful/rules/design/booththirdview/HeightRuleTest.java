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

public class HeightRuleTest {

    private HeightRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new HeightRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 不允许搭建二层结构但尝试搭建，返回失败
     */
    @Test
    public void testApply_DisallowedSecondLevel_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.SECOND_EXHIBITION)).thenReturn("1");
        when(mockContext.get(DataKeyConstant.ALLOW_SECOND)).thenReturn("0");
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("100");
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn("2025");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("不允许搭建二层结构", result.getReason());
    }

    /**
     * TC02: 简装高度 >= 2400，返回失败
     */
    @Test
    public void testApply_JianZhuangHeightTooHigh_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.SECOND_EXHIBITION)).thenReturn("0");
        when(mockContext.get(DataKeyConstant.ALLOW_SECOND)).thenReturn("1");
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("101");
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn("2025");
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.HEIGHT)).thenReturn("2400");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("简装单层展位高度(2400mm)大于等于2.4m", result.getReason());
    }

    /**
     * TC03: 特装单层高度 != 2400，返回失败
     */
    @Test
    public void testApply_TeZhuangSingleWrongHeight_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.SECOND_EXHIBITION)).thenReturn("0");
        when(mockContext.get(DataKeyConstant.ALLOW_SECOND)).thenReturn("1");
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("102");
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn("2025");
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("特装");
        when(mockContext.get(DataKeyConstant.HEIGHT)).thenReturn("3000");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("特装单层展位高度(3000mm)不等于2.4m", result.getReason());
    }

    /**
     * TC04: 特装双层高度 != 6000，返回失败
     */
    @Test
    public void testApply_TeZhuangDoubleWrongHeight_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.SECOND_EXHIBITION)).thenReturn("1");
        when(mockContext.get(DataKeyConstant.ALLOW_SECOND)).thenReturn("1");
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("103");
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn("2025");
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("特装");
        when(mockContext.get(DataKeyConstant.HEIGHT)).thenReturn("5000");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("特装双层展位高度(5000mm)不等于6m", result.getReason());
    }

    /**
     * TC05: 所有信息合法，返回通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.SECOND_EXHIBITION)).thenReturn("0");
        when(mockContext.get(DataKeyConstant.ALLOW_SECOND)).thenReturn("1");
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("104");
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn("2025");
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("特装");
        when(mockContext.get(DataKeyConstant.HEIGHT)).thenReturn("2400");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }
}
