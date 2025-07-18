package com.tip.restful.rules.electric.ConstructionSafety;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import org.junit.After;
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

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 图片字段为 "True"，返回通过
     */
    @Test
    public void testApply_RequirementTrue_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.CONSTRUCTION_SAFETY_REQUIREMENT)).thenReturn("True");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 图片字段为 "False"，返回失败
     */
    @Test
    public void testApply_RequirementFalse_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.CONSTRUCTION_SAFETY_REQUIREMENT)).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("图片未包含“广交会特装施工安全责任承诺书”字段", result.getReason());
    }

    /**
     * TC03: 图片字段为 null，返回空指针异常（建议在生产代码中进行防御）
     */
    @Test
    public void testApply_RequirementNull_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.CONSTRUCTION_SAFETY_REQUIREMENT)).thenReturn(null);

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("图片未包含“广交会特装施工安全责任承诺书”字段", result.getReason());
    }

}
