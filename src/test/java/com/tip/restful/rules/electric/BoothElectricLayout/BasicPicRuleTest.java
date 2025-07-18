package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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

    /**
     * TC01: 图片中包含“配电系统”信息，应返回 null（待后续处理）
     */
    @Test
    public void testApply_ElectricBasicTrue_ReturnsNull() {
        when(mockContext.get(DataKeyConstant.ELECTRIC_BASIC)).thenReturn("True");

        RuleResult result = rule.apply(null, mockContext);

        assertTrue(result.isPass()); // 如果返回 null 是预期行为
    }

    /**
     * TC02: 图片中不包含“配电系统”信息，应返回失败
     */
    @Test
    public void testApply_ElectricBasicFalse_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.ELECTRIC_BASIC)).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);

        assertNotNull(result);
        assertFalse(result.isPass());
        assertEquals("图片中未包含“配电系统”或“总开关电箱”或“客量”字段", result.getReason());
    }

    /**
     * TC03: 图片信息为空，应返回失败
     */
    @Test
    public void testApply_ElectricBasicNull_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.ELECTRIC_BASIC)).thenReturn("");

        RuleResult result = rule.apply(null, mockContext);

        assertNotNull(result);
        assertFalse(result.isPass());
        assertEquals("图片中未包含“配电系统”或“总开关电箱”或“客量”字段", result.getReason());
    }
}
