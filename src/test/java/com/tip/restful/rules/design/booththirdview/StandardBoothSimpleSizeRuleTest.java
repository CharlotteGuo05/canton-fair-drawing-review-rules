package com.tip.restful.rules.design.booththirdview;


import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.booththirdview.StandardBoothSimpleSizeRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.xml.crypto.Data;

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
        // 清理所有 mock
    }

    /**
     * TC01: 类型不是“简装”，应直接通过
     */
    @Test
    public void testApply_NotSimpleType_ReturnsPassWithReason() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("精装");

        RuleResult result = rule.apply(null,mockContext);

        assertTrue(result.isPass());
        assertEquals("不是简装", result.getReason());
    }

    /**
     * TC02: 简装类型，但长度超过2970mm
     */
    @Test
    public void testApply_LengthExceedsLimit_ReturnsFail() {
        setupMockContext("简装", "3000", "2400", "2400", "1000");

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("长度("+mockContext.get(DataKeyConstant.LENGTH) +")、宽度("+mockContext.get(DataKeyConstant.WIDTH)+")均不得大于2970mm", result.getReason());
    }

    /**
     * TC03: 简装类型，但宽度超过2970mm
     */
    @Test
    public void testApply_WidthExceedsLimit_ReturnsFail() {
        setupMockContext("简装", "1000", "2400", "2400", "3000");

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("长度("+mockContext.get(DataKeyConstant.LENGTH) +")、宽度("+mockContext.get(DataKeyConstant.WIDTH)+")均不得大于2970mm", result.getReason());
    }

    /**
     * TC04: 简装类型，前高不等于2400
     */
    @Test
    public void testApply_FrontHeightNot2400_ReturnsFail() {
        setupMockContext("简装", "1000", "2300", "2400", "2000");

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("单层展位高度("+mockContext.get(DataKeyConstant.FRONT_HEIGHT)+")不为2.4m", result.getReason());
    }

    /**
     * TC05: 简装类型，侧高不等于2400
     */
    @Test
    public void testApply_SideHeightNot2400_ReturnsFail() {
        setupMockContext("简装", "1000", "2400", "2300", "2000");

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("单层展位高度("+mockContext.get(DataKeyConstant.FRONT_HEIGHT)+")不为2.4m", result.getReason());
    }

    /**
     * TC06: 所有参数符合要求，应通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        setupMockContext("简装", "1000", "2400", "2400", "2000");

        RuleResult result = rule.apply(null,mockContext);

        assertTrue(result.isPass());
        assertEquals("", result.getReason());
    }

    /**
     * 辅助方法：设置RuleContext中各个字段的模拟返回值
     */
    private void setupMockContext(String drawingKind, String length, String frontHeight, String sideHeight, String width) {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn(drawingKind);
        when(mockContext.get(DataKeyConstant.LENGTH)).thenReturn(length);
        when(mockContext.get(DataKeyConstant.FRONT_HEIGHT)).thenReturn(frontHeight);
        when(mockContext.get(DataKeyConstant.SIDE_HEIGHT)).thenReturn(sideHeight);
        when(mockContext.get(DataKeyConstant.WIDTH)).thenReturn(width);
    }
}