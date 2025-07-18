package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.bootheffectview.GreenRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GreenRuleTest {

    private GreenRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new GreenRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
    }

    /**
     * TC01: A类，包含禁止材料，返回失败
     */
    @Test
    public void testApply_TypeA_ContainsForbiddenMaterial_ReturnsFail() {
        GreenRule.Material m1 = rule.new Material("泡沫板", "False", 1200, 900);
        List<GreenRule.Material> materials = Arrays.asList(m1);

        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("A类");
        when(mockContext.get(DataKeyConstant.MATERIAL_LIST)).thenReturn(materials);
        when(mockContext.get(DataKeyConstant.FORBIDDEN_MATERIAL)).thenReturn("泡沫板");
        when(mockContext.get(DataKeyConstant.WOOD_MATERIAL)).thenReturn("木板");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("不得出现该类材料", result.getReason());
    }

    /**
     * TC02: B类，移动材料面积过大，返回失败
     */
    @Test
    public void testApply_TypeB_MovableTooLarge_ReturnsFail() {
        GreenRule.Material m1 = rule.new Material("钢架", "True", 3000, 3000); // 9000000 > 7500000
        List<GreenRule.Material> materials = Arrays.asList(m1);

        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("B类");
        when(mockContext.get(DataKeyConstant.MATERIAL_LIST)).thenReturn(materials);
        when(mockContext.get(DataKeyConstant.FORBIDDEN_MATERIAL)).thenReturn("");
        when(mockContext.get(DataKeyConstant.WOOD_MATERIAL)).thenReturn("");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("可移动材料面积不可以大于7500mm", result.getReason());
    }

    /**
     * TC03: B类，包含禁止材料，返回失败
     */
    @Test
    public void testApply_TypeB_ForbiddenMaterial_ReturnsFail() {
        GreenRule.Material m1 = rule.new Material("泡沫板", "False", 1500, 3000);
        List<GreenRule.Material> materials = Arrays.asList(m1);

        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("B类");
        when(mockContext.get(DataKeyConstant.MATERIAL_LIST)).thenReturn(materials);
        when(mockContext.get(DataKeyConstant.FORBIDDEN_MATERIAL)).thenReturn("泡沫板");
        when(mockContext.get(DataKeyConstant.WOOD_MATERIAL)).thenReturn("");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("不得出现该类材料", result.getReason());
    }

    /**
     * TC04: A类，材料合法，返回通过
     */
    @Test
    public void testApply_TypeA_Valid_ReturnsPass() {
        GreenRule.Material m1 = rule.new Material("布料", "False", 1200, 900);
        List<GreenRule.Material> materials = Arrays.asList(m1);

        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("A类");
        when(mockContext.get(DataKeyConstant.MATERIAL_LIST)).thenReturn(materials);
        when(mockContext.get(DataKeyConstant.FORBIDDEN_MATERIAL)).thenReturn("泡沫板");
        when(mockContext.get(DataKeyConstant.WOOD_MATERIAL)).thenReturn("木板");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC05: B类，材料合法，返回通过
     */
    @Test
    public void testApply_TypeB_Valid_ReturnsPass() {
        GreenRule.Material m1 = rule.new Material("玻璃", "True", 1500, 3000); // 4500000 < 7500000
        List<GreenRule.Material> materials = Arrays.asList(m1);

        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("B类");
        when(mockContext.get(DataKeyConstant.MATERIAL_LIST)).thenReturn(materials);
        when(mockContext.get(DataKeyConstant.FORBIDDEN_MATERIAL)).thenReturn("泡沫板");
        when(mockContext.get(DataKeyConstant.WOOD_MATERIAL)).thenReturn("木板");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }
}
