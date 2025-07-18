package com.tip.restful.rules.design.booththirdview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.booththirdview.basicRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class basicRuleTest {

    private basicRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new basicRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        // 清理资源（如果需要）
    }

    /**
     * TC01: 所有视图都有，应通过
     */
    @Test
    public void testApply_AllViewsPresent_ReturnsPass() {
        setupMockContext(true, true, true);

        RuleResult result = rule.apply(null,mockContext);

        assertTrue(result.isPass());
    }

    /**
     * TC02: 缺少正面视图，应失败
     */
    @Test
    public void testApply_MissingFrontView_ReturnsFail() {
        setupMockContext(false, true, true);

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("没有正面视图", result.getReason());
    }

    /**
     * TC03: 缺少侧面视图，应失败
     */
    @Test
    public void testApply_MissingSideView_ReturnsFail() {
        setupMockContext(true, false, true);

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("没有侧面视图", result.getReason());
    }

    /**
     * TC04: 缺少顶视图，应失败
     */
    @Test
    public void testApply_MissingTopView_ReturnsFail() {
        setupMockContext(true, true, false);

        RuleResult result = rule.apply(null,mockContext);

        assertFalse(result.isPass());
        assertEquals("没有顶视图", result.getReason());
    }

    /**
     * 辅助方法：模拟三种视图的布尔值
     */
    private void setupMockContext(boolean hasFront, boolean hasSide, boolean hasTop) {
        when(mockContext.get(DataKeyConstant.HAS_FRONT_VIEWS)).thenReturn(hasFront);
        when(mockContext.get(DataKeyConstant.HAS_SIDE_VIEWS)).thenReturn(hasSide);
        when(mockContext.get(DataKeyConstant.HAS_TOP_VIEWS)).thenReturn(hasTop);
    }
}
