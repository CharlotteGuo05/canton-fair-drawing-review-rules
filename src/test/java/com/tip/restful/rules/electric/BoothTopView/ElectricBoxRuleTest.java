package com.tip.restful.rules.electric.BoothTopView;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.electric.BoothTopView.ElectricBoxRule.boxInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ElectricBoxRuleTest {

    private ElectricBoxRule rule;
    private RuleContext mockContext;
    private boxInfo mockBox;

    @Before
    public void setUp() {
        rule = new ElectricBoxRule();
        mockContext = Mockito.mock(RuleContext.class);
        mockBox = Mockito.mock(boxInfo.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 电箱类型与目标类型不匹配，返回失败
     */
    @Test
    public void testApply_BoxTypeMismatch_ReturnsFail() {
        when(mockBox.getBoxType()).thenReturn("类型A");
        when(mockBox.getBoxNumber()).thenReturn(2);
        when(mockContext.get(DataKeyConstant.ELECTRIC_BOX)).thenReturn(mockBox);
        when(mockContext.get(DataKeyConstant.TARGET_BOX_TYPE)).thenReturn("类型B");
        when(mockContext.get(DataKeyConstant.TARGET_BOX_TYPE_TWO)).thenReturn("类型C");
        when(mockContext.get(DataKeyConstant.TARGET_BOX_NUMBER)).thenReturn("2");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("电箱规格(类型A)与报图资料中展示用电电箱规格(类型B)或者展位配电系统图中的电箱规格(类型C)不匹配", result.getReason());
    }

    /**
     * TC02: 电箱数量不一致，返回失败
     */
    @Test
    public void testApply_BoxNumberMismatch_ReturnsFail() {
        when(mockBox.getBoxType()).thenReturn("类型X");
        when(mockBox.getBoxNumber()).thenReturn(1);
        when(mockContext.get(DataKeyConstant.ELECTRIC_BOX)).thenReturn(mockBox);
        when(mockContext.get(DataKeyConstant.TARGET_BOX_TYPE)).thenReturn("类型X");
        when(mockContext.get(DataKeyConstant.TARGET_BOX_TYPE_TWO)).thenReturn("类型X");
        when(mockContext.get(DataKeyConstant.TARGET_BOX_NUMBER)).thenReturn("3");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("电箱数量(1)与展位配电系统图中的数量(类型X)不匹配", result.getReason());
    }

    /**
     * TC03: 类型和数量全部匹配，返回通过
     */
    @Test
    public void testApply_AllMatch_ReturnsPass() {
        when(mockBox.getBoxType()).thenReturn("合格类型");
        when(mockBox.getBoxNumber()).thenReturn(3);
        when(mockContext.get(DataKeyConstant.ELECTRIC_BOX)).thenReturn(mockBox);
        when(mockContext.get(DataKeyConstant.TARGET_BOX_TYPE)).thenReturn("合格类型");
        when(mockContext.get(DataKeyConstant.TARGET_BOX_TYPE_TWO)).thenReturn("合格类型");
        when(mockContext.get(DataKeyConstant.TARGET_BOX_NUMBER)).thenReturn("3");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }
}
