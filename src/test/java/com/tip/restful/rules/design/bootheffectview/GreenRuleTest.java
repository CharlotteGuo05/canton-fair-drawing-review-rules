package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.design.BoothEffectView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class GreenRuleTest {

    private GreenRule rule;
    private RuleContext mockContext;
    private BoothEffectView.Material mockMaterial;

    @Before
    public void setUp() {
        rule = new GreenRule();
        mockContext = Mockito.mock(RuleContext.class);
        mockMaterial = Mockito.mock(BoothEffectView.Material.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /** TC01: A类材料中出现禁止材料，返回失败 */
    @Test
    public void testApply_AType_ForbiddenMaterial_Fail() {
        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("A类");
        when(mockContext.get(DataKeyConstant.IS_FORBIDDEN)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.IS_WOOD)).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("出现禁止材料", result.getReason());
    }

    /** TC02: A类材料中出现木质材料，返回失败 */
    @Test
    public void testApply_AType_WoodMaterial_Fail() {
        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("A类");
        when(mockContext.get(DataKeyConstant.IS_FORBIDDEN)).thenReturn("False");
        when(mockContext.get(DataKeyConstant.IS_WOOD)).thenReturn("True");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("出现禁止材料", result.getReason());
    }

    /** TC03: B类材料中有面积超限的可移动材料，返回失败 */
    @Test
    public void testApply_BType_MovableTooLarge_Fail() {
        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("B类");
        when(mockContext.get(DataKeyConstant.IS_FORBIDDEN)).thenReturn("False");
        when(mockMaterial.isMovable()).thenReturn("True");
        when(mockMaterial.getLength()).thenReturn(3000);
        when(mockMaterial.getWidth()).thenReturn(3000);
        when(mockContext.get(DataKeyConstant.MATERIAL_LIST)).thenReturn(List.of(mockMaterial));

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("可移动材料面积不可以大于7500mm", result.getReason());
    }

    /** TC04: B类材料中出现禁止材料，返回失败 */
    @Test
    public void testApply_BType_Forbidden_Fail() {
        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("B类");
        when(mockContext.get(DataKeyConstant.IS_FORBIDDEN)).thenReturn("True");
        when(mockMaterial.isMovable()).thenReturn("False");
        when(mockMaterial.getLength()).thenReturn(1000);
        when(mockMaterial.getWidth()).thenReturn(1000);
        when(mockContext.get(DataKeyConstant.MATERIAL_LIST)).thenReturn(List.of(mockMaterial));

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("出现禁止材料", result.getReason());
    }

    /** TC05: A类材料全部合法，返回通过 */
    @Test
    public void testApply_AType_AllValid_Pass() {
        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("A类");
        when(mockContext.get(DataKeyConstant.IS_FORBIDDEN)).thenReturn("False");
        when(mockContext.get(DataKeyConstant.IS_WOOD)).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    /** TC06: B类材料全部合法，返回通过 */
    @Test
    public void testApply_BType_AllValid_Pass() {
        when(mockContext.get(DataKeyConstant.GREEN_TYPE)).thenReturn("B类");
        when(mockContext.get(DataKeyConstant.IS_FORBIDDEN)).thenReturn("False");
        when(mockMaterial.isMovable()).thenReturn("True");
        when(mockMaterial.getLength()).thenReturn(1000);
        when(mockMaterial.getWidth()).thenReturn(1000);
        when(mockContext.get(DataKeyConstant.MATERIAL_LIST)).thenReturn(List.of(mockMaterial));

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }
}
