package com.tip.restful.rules.design.booththirdview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.booththirdview.LengthWidthRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class LengthWidthRuleTest {

    private LengthWidthRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new LengthWidthRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        // 清理 mock
    }

    /**
     * TC01: 是独立展位，应直接通过
     */
    @Test
    public void testApply_IsIndependent_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.MODEL_EXTRACT_INFO)).thenReturn("1");
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("123");
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn("1");


        RuleResult result = rule.apply(null,mockContext);

        assertTrue(result.isPass());
    }

    /**
     * TC02: 长度 <= 10m，但未预留正确值，应返回长度失败（小于10m规则）
     */
    @Test
    public void testApply_LengthTooShortWithoutReserve_ReturnsFail() {
        setupMockContext("0", "9000", "9000", "9000", "9000"); // 正确值应为 8910

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("10米以内的展位长度没有每3m预留30mm", result.getReason());
    }

    /**
     * TC03: 长度 > 10m，但未预留100mm，应返回长度失败（大于10m规则）
     */
    @Test
    public void testApply_LengthTooLongWithoutReserve_ReturnsFail() {
        setupMockContext("0", "15000", "9000", "15000", "9000"); // 应为 14900

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("10米以上的展位长度没有预留100mm", result.getReason());
    }

    /**
     * TC04: 宽度 <= 10m，但未预留正确值，应返回宽度失败
     */
    @Test
    public void testApply_WidthTooShortWithoutReserve_ReturnsFail() {
        setupMockContext("0", "5940", "9000", "6000", "9000"); // 应为 8910，宽度错

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("10米以内的展位宽度没有每3m预留30mm", result.getReason());
    }

    /**
     * TC05: 宽度 > 10m，但未预留100mm，应返回宽度失败
     */
    @Test
    public void testApply_WidthTooLongWithoutReserve_ReturnsFail() {
        setupMockContext("0", "14900", "9000", "15000", "12000"); // 应为 11000，给了 9000

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("10米以上的展位宽度没有预留100mm", result.getReason());
    }

    /**
     * TC06: 长宽都符合预留要求，应通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        setupMockContext("0", "8910", "8910", "9000", "9000"); // 9000 - 90 = 8910

        RuleResult result = rule.apply(null,mockContext);
        System.out.println("result: " + result);
        assertTrue(result.isPass());
    }

    /**
     * 辅助方法：设置 RuleContext 中所有字段
     */
    private void setupMockContext(String isIndependent, String length, String width, String targetLength, String targetWidth) {
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn("123");
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn("1");
        when(mockContext.get(DataKeyConstant.MODEL_EXTRACT_INFO)).thenReturn(isIndependent);
        when(mockContext.get(DataKeyConstant.LENGTH)).thenReturn(length);
        when(mockContext.get(DataKeyConstant.WIDTH)).thenReturn(width);
        when(mockContext.get(DataKeyConstant.TARGET_LENGTH)).thenReturn(targetLength);
        when(mockContext.get(DataKeyConstant.TARGET_WIDTH)).thenReturn(targetWidth);
    }
}
