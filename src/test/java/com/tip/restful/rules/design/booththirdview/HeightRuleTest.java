package com.tip.restful.rules.design.booththirdview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.booththirdview.HeightRule;
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
        // optional: clean up if needed
    }

    /**
     * TC01: 展位为二层，但数据库不允许，应不通过
     */
    @Test
    public void testApply_SecondNotAllowedButExhibitionIsSecond_ReturnsFail() {
        setupMockContext("特装", "1", "1", "6000", "6000", "1", "1", "0");

        RuleResult result = rule.apply(null, mockContext);

        assertFalse(result.isPass());
        assertEquals("不允许搭建二层结构", result.getReason());
    }

    /**
     * TC02: 简装，前高 >= 2400，应不通过
     */
    @Test
    public void testApply_SimpleFrontHeightTooHigh_ReturnsFail() {
        setupMockContext("简装", "0", "1", "2500", "2300", "1", "1", "1");

        RuleResult result = rule.apply(null, mockContext);

        assertFalse(result.isPass());
        assertEquals("简装单层展位高度大于等于2.4m", result.getReason());
    }

    /**
     * TC03: 简装，侧高 >= 2400，应不通过
     */
    @Test
    public void testApply_SimpleSideHeightTooHigh_ReturnsFail() {
        setupMockContext("简装", "0", "1", "2300", "2400", "1", "1", "1");

        RuleResult result = rule.apply(null, mockContext);

        assertFalse(result.isPass());
        assertEquals("简装单层展位高度大于等于2.4m", result.getReason());
    }

    /**
     * TC04: 特装双层，高度不等于6000，应不通过
     */
    @Test
    public void testApply_SpecialDoubleWrongHeight_ReturnsFail() {
        setupMockContext("特装", "1", "1", "5900", "6000", "1", "1", "1");

        RuleResult result = rule.apply(null, mockContext);

        assertFalse(result.isPass());
        assertEquals("特装双层展位高度不等于6m", result.getReason());
    }

    /**
     * TC05: 特装单层，高度不等于2400，应不通过
     */
    @Test
    public void testApply_SpecialSingleWrongHeight_ReturnsFail() {
        setupMockContext("特装", "0", "1", "2300", "2400", "1", "1", "1");

        RuleResult result = rule.apply(null, mockContext);

        assertFalse(result.isPass());
        assertEquals("特装单层展位高度不等于2.4m", result.getReason());
    }

    /**
     * TC06: 所有参数符合，应通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        setupMockContext("特装", "1", "1", "6000", "6000", "1", "1", "1");

        RuleResult result = rule.apply(null, mockContext);

        assertTrue(result.isPass());
        assertTrue(result.getReason() == null || result.getReason().isEmpty());

    }

    /**
     * 辅助方法：设置 RuleContext 中的字段 mock 返回值
     */
    private void setupMockContext(String drawingKind, String secondExhibition, String modelExtract,
                                  String frontHeight, String sideHeight, String boothID,
                                  String period, String allowSecond) {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn(drawingKind);
        when(mockContext.get(DataKeyConstant.SECOND_EXHIBITION)).thenReturn(secondExhibition);
        when(mockContext.get(DataKeyConstant.MODEL_EXTRACT_INFO)).thenReturn(modelExtract);
        when(mockContext.get(DataKeyConstant.FRONT_HEIGHT)).thenReturn(frontHeight);
        when(mockContext.get(DataKeyConstant.SIDE_HEIGHT)).thenReturn(sideHeight);
        when(mockContext.get(DataKeyConstant.BOOTH_ID)).thenReturn(boothID);
        when(mockContext.get(DataKeyConstant.PERIOD)).thenReturn(period);
        when(mockContext.get(DataKeyConstant.ALLOW_SECOND)).thenReturn(allowSecond);
    }
}
