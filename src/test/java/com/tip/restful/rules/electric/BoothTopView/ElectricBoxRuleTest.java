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
        mockBox = null;
    }

    /**
     * TC01: 电箱类型不匹配，返回失败
     */
    @Test
    public void testApply_BoxTypeMismatch_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.ELECTRIC_BOX)).thenReturn(mockBox);
        when(mockBox.getBoxType()).thenReturn("A型");
        when(mockContext.get(DataKeyConstant.TARGET_BOX_TYPE)).thenReturn("B型");

        when(mockBox.getBoxNumber()).thenReturn(2);
        when(mockContext.get(DataKeyConstant.TARGET_BOX_NUMBER)).thenReturn("2");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("电箱规格不匹配", result.getReason());
    }

    /**
     * TC02: 电箱数量不匹配，返回失败
     */
    @Test
    public void testApply_BoxNumberMismatch_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.ELECTRIC_BOX)).thenReturn(mockBox);
        when(mockBox.getBoxType()).thenReturn("B型");
        when(mockContext.get(DataKeyConstant.TARGET_BOX_TYPE)).thenReturn("B型");

        when(mockBox.getBoxNumber()).thenReturn(1);
        when(mockContext.get(DataKeyConstant.TARGET_BOX_NUMBER)).thenReturn("2");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("电箱数量不匹配", result.getReason());
    }

    /**
     * TC03: 类型和数量都匹配，返回通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.ELECTRIC_BOX)).thenReturn(mockBox);
        when(mockBox.getBoxType()).thenReturn("C型");
        when(mockContext.get(DataKeyConstant.TARGET_BOX_TYPE)).thenReturn("C型");

        when(mockBox.getBoxNumber()).thenReturn(3);
        when(mockContext.get(DataKeyConstant.TARGET_BOX_NUMBER)).thenReturn("3");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }
}
