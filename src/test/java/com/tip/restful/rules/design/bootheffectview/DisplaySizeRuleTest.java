package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.bootheffectview.DisplaySizeRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DisplaySizeRuleTest {

    private DisplaySizeRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new DisplaySizeRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
    }

    /**
     * TC01: 无展架尺寸，直接通过
     */
    @Test
    public void testApply_NoDisplaySize_ReturnsPass() {
        //when(对象.方法()).thenReturn(返回值);
        //mock在这里mock了context，在context.get()的时候返回-1，表示无展架尺寸
        when(mockContext.get(DataKeyConstant.DISPLAY_WIDTH)).thenReturn("-1");
        when(mockContext.get(DataKeyConstant.DISPLAY_HEIGHT)).thenReturn("-1");
        //.apply()表示调用被测试的规则逻辑，传入的参数是
        RuleResult result = rule.apply(null,mockContext);
        //判断result.isPass()为true，则通过测试
        assertTrue(result.isPass());
    }


    /**
     * TC02: 高度在2.5-3m之间，宽度<2.5m, 通过
     */
    @Test
    public void testApply_DisplaySizeInRange1_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DISPLAY_HEIGHT)).thenReturn("2500");
        when(mockContext.get(DataKeyConstant.DISPLAY_WIDTH)).thenReturn("2500");
        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }


    /**
     * TC03: 宽度在2.5-3m之间，高度<2.5m, 通过
     */
    @Test
    public void testApply_DisplaySizeInRange2_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DISPLAY_WIDTH)).thenReturn("2500");
        when(mockContext.get(DataKeyConstant.DISPLAY_HEIGHT)).thenReturn("2500");
        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }



    /**
     * TC04: 高度在2.5-3m之间，宽度>2.5m, 不通过
     */
    @Test
    public void testApply_DisplaySizeInRange1_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.DISPLAY_HEIGHT)).thenReturn("2600");
        when(mockContext.get(DataKeyConstant.DISPLAY_WIDTH)).thenReturn("3000");
        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("高("+mockContext.get(DataKeyConstant.DISPLAY_HEIGHT)+"mm)为2500-3000mm, 宽("+mockContext.get(DataKeyConstant.DISPLAY_WIDTH)+"mm)不得大于2500m", result.getReason());
    }


    /**
     * TC05: 宽度在2.5-3m之间，高度>2.5m, 不通过
     */
    @Test
    public void testApply_DisplaySizeInRange2_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.DISPLAY_WIDTH)).thenReturn("2600");
        when(mockContext.get(DataKeyConstant.DISPLAY_HEIGHT)).thenReturn("3000");
        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("高("+mockContext.get(DataKeyConstant.DISPLAY_HEIGHT)+"mm)为2500-3000mm, 宽("+mockContext.get(DataKeyConstant.DISPLAY_WIDTH)+"mm)不得大于2500m", result.getReason());
    }



}