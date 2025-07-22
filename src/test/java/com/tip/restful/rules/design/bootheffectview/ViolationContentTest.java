package com.tip.restful.rules.design.bootheffectview;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ViolationContentTest {

    private ViolationContent rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new ViolationContent();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 存在违规关键词，返回失败
     */
    @Test
    public void testApply_ViolationDetected_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.IS_VIOLATION)).thenReturn("true");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("存在违规内容", result.getReason());
    }

    /**
     * TC02: 存在荣誉内容，返回失败
     */
    @Test
    public void testApply_HonorDetected_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.IS_VIOLATION)).thenReturn("false");
        when(mockContext.get(DataKeyConstant.IS_HORNOR)).thenReturn("true");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("存在荣誉内容", result.getReason());
    }

    /**
     * TC03: 内容声明存在，图像存在，返回通过
     */
    @Test
    public void testApply_ValidHonorGraph_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.IS_VIOLATION)).thenReturn("false");
        when(mockContext.get(DataKeyConstant.IS_HORNOR)).thenReturn("false");
        when(mockContext.get(DataKeyConstant.GRAPH_CONTENT)).thenReturn("若与事实不符或引发相关纠纷，本参展企业对此负全部责任");
        when(mockContext.get(DataKeyConstant.HONOR_GRAPH)).thenReturn("some_graph.jpg");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC04: 内容声明存在，但图像缺失，返回失败
     */
    @Test
    public void testApply_MissingHonorGraph_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.IS_VIOLATION)).thenReturn("false");
        when(mockContext.get(DataKeyConstant.IS_HORNOR)).thenReturn("false");
        when(mockContext.get(DataKeyConstant.GRAPH_CONTENT)).thenReturn("若与事实不符或引发相关纠纷，本参展企业对此负全部责任");
        when(mockContext.get(DataKeyConstant.HONOR_GRAPH)).thenReturn(null);

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("不符合荣誉要求", result.getReason());
    }

    /**
     * TC05: 内容声明缺失但图像存在，返回失败
     */
    @Test
    public void testApply_MissingStatement_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.IS_VIOLATION)).thenReturn("false");
        when(mockContext.get(DataKeyConstant.IS_HORNOR)).thenReturn("false");
        when(mockContext.get(DataKeyConstant.GRAPH_CONTENT)).thenReturn("未声明");
        when(mockContext.get(DataKeyConstant.HONOR_GRAPH)).thenReturn("some_graph.jpg");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("不符合荣誉要求", result.getReason());
    }
}
