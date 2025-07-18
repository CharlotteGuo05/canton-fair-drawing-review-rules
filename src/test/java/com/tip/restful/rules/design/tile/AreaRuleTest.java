package com.tip.restful.rules.design.tile;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.tile.AreaRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class AreaRuleTest {

    private AreaRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new AreaRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
    }

    /**
     * TC01: 面积小于限制，应通过
     */
    @Test
    public void testApply_TileAreaBelowLimit_ReturnsPass() {
        AreaRule.Tile tile = rule.new Tile(1000, 2000); // 2,000,000 mm²
        when(mockContext.get(DataKeyConstant.TILE_AREA)).thenReturn(tile);

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 面积等于限制，应通过
     */
    @Test
    public void testApply_TileAreaEqualLimit_ReturnsPass() {
        AreaRule.Tile tile = rule.new Tile(1220, 2440); // 2,976,800 mm²
        when(mockContext.get(DataKeyConstant.TILE_AREA)).thenReturn(tile);

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC03: 面积超过限制，应不通过
     */
    @Test
    public void testApply_TileAreaAboveLimit_ReturnsFail() {
        AreaRule.Tile tile = rule.new Tile(1500, 2000); // 3,000,000 mm²
        when(mockContext.get(DataKeyConstant.TILE_AREA)).thenReturn(tile);

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("单片瓷砖面积不得超过122 cm *244 cm", result.getReason());
    }
}
