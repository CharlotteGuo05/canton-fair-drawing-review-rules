package com.tip.restful.rules.fire;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BasicPicRuleTest {

    private BasicPicRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new BasicPicRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
    }

    /**
     * TC01: 所有材料都在阻燃清单中，且字段为True，应通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.FIRE_MATERIAL_REQUIREMENT)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.FIRE_MATERIAL_LIST)).thenReturn(Arrays.asList("防火板", "石膏板"));
        when(mockContext.get(DataKeyConstant.FLAME_PROOF_MATERIAL)).thenReturn("防火板 石膏板 岩棉板");

        RuleResult result = rule.apply(null,mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 字段为False，应不通过
     */
    @Test
    public void testApply_RequirementFalse_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.FIRE_MATERIAL_REQUIREMENT)).thenReturn("False");
        when(mockContext.get(DataKeyConstant.FIRE_MATERIAL_LIST)).thenReturn(Arrays.asList("防火板", "石膏板"));
        when(mockContext.get(DataKeyConstant.FLAME_PROOF_MATERIAL)).thenReturn("防火板 石膏板 岩棉板");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("图片未包含搭建材料全部使用B1级难燃材料字段", result.getReason());
    }

    /**
     * TC03: 有材料不在阻燃清单中，应不通过
     */
    @Test
    public void testApply_MaterialNotInList_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.FIRE_MATERIAL_REQUIREMENT)).thenReturn("True");
        when(mockContext.get(DataKeyConstant.FIRE_MATERIAL_LIST)).thenReturn(Arrays.asList("防火板", "易燃布料"));
        when(mockContext.get(DataKeyConstant.FLAME_PROOF_MATERIAL)).thenReturn("防火板 石膏板 岩棉板");

        RuleResult result = rule.apply(null,mockContext);
        assertFalse(result.isPass());
        assertEquals("材料(易燃布料)不在阻燃/难燃材料清单中", result.getReason());
    }
}
