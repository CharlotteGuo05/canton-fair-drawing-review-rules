package com.tip.restful.rules.design.tile;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.tile.TileStandardBoothRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TileStandardBoothRuleTest {

    private TileStandardBoothRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new TileStandardBoothRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
    }

    /**
     * TC01: 简装、无标摊、公司名含有“大会统一装搭” -> 通过
     */
    @Test
    public void testApply_Simple_NoBooth_ContainsUnifiedText_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.HAS_BOOTH)).thenReturn("False");
        when(mockContext.get(DataKeyConstant.TILE_COMPANY_NAME)).thenReturn("由大会统一装搭");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 简装、无标摊、公司名不含“大会统一装搭” -> 不通过
     */
    @Test
    public void testApply_Simple_NoBooth_MissingUnifiedText_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.HAS_BOOTH)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.TILE_COMPANY_NAME)).thenReturn("某企业名称");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("简装门楣出现企业名称，但图中未含有大会统一装搭", result.getReason());
    }

    /**
     * TC03: 简装、有标摊 -> 通过（无强制要求）
     */
    @Test
    public void testApply_Simple_HasBooth_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("简装");
        when(mockContext.get(DataKeyConstant.HAS_BOOTH)).thenReturn("False");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC04: 非简装类型 -> 通过（规则不适用）
     */
    @Test
    public void testApply_NotSimple_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.DRAWING_KIND)).thenReturn("特装");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }
}
