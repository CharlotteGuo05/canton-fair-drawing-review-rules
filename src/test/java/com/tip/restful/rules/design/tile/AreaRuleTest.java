package com.tip.restful.rules.design.tile;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.design.TileResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

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
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 存在单片瓷砖面积超过 2976800，返回失败
     */
    @Test
    public void testApply_ExceedsMaxArea_ReturnsFail() {
        TileResolver.Tile mockTile = Mockito.mock(TileResolver.Tile.class);
        when(mockTile.getArea()).thenReturn(3000000);
        List<TileResolver.Tile> tiles = Arrays.asList(mockTile);
        when(mockContext.get(DataKeyConstant.TILE)).thenReturn(tiles);

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("图中单片瓷砖面积为3000000mm²,超过了122cm*244 cm", result.getReason());
    }

    /**
     * TC02: 所有瓷砖面积都合规，返回通过
     */
    @Test
    public void testApply_AllWithinLimit_ReturnsPass() {
        TileResolver.Tile tile1 = Mockito.mock(TileResolver.Tile.class);
        TileResolver.Tile tile2 = Mockito.mock(TileResolver.Tile.class);
        when(tile1.getArea()).thenReturn(2500000);
        when(tile2.getArea()).thenReturn(2600000);
        List<TileResolver.Tile> tiles = Arrays.asList(tile1, tile2);
        when(mockContext.get(DataKeyConstant.TILE)).thenReturn(tiles);

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }
}
