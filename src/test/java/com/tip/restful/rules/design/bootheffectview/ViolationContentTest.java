package com.tip.restful.rules.design;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.design.bootheffectview.ViolationContent;
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

    /**
     * TC01: 包含免责声明且荣誉图片不为null，应通过
     */
    @Test
    public void testApply_HasDisclaimerAndHonorGraphNotNull_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.GRAPH_CONTENT))
                .thenReturn("本展位展示内容仅供参考，若与事实不符或引发相关纠纷，本参展企业对此负全部责任");
        when(mockContext.get(DataKeyConstant.HONOR_GRAPH)).thenReturn("honor_image.png");

        RuleResult result = rule.apply(null, mockContext);

        assertTrue(result.isPass());
        assertTrue(result.getReason() == null || result.getReason().isEmpty());

    }

    /**
     * TC02: 缺少免责声明，应不通过
     */
    @Test
    public void testApply_MissingDisclaimer_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.GRAPH_CONTENT)).thenReturn("本展位展示内容仅供参考");
        when(mockContext.get(DataKeyConstant.HONOR_GRAPH)).thenReturn("honor_image.png");

        RuleResult result = rule.apply(null, mockContext);

        assertFalse(result.isPass());
        assertEquals("不符合荣誉要求", result.getReason());
    }

    /**
     * TC03: 荣誉图为空，应不通过
     */
    @Test
    public void testApply_HonorGraphIsNull_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.GRAPH_CONTENT))
                .thenReturn("若与事实不符或引发相关纠纷，本参展企业对此负全部责任");
        when(mockContext.get(DataKeyConstant.HONOR_GRAPH)).thenReturn(null);

        RuleResult result = rule.apply(null, mockContext);

        assertFalse(result.isPass());
        assertEquals("不符合荣誉要求", result.getReason());
    }

    /**
     * TC04: 两个条件都不满足，应不通过
     */
    @Test
    public void testApply_BothConditionsFail_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.GRAPH_CONTENT)).thenReturn("无免责声明");
        when(mockContext.get(DataKeyConstant.HONOR_GRAPH)).thenReturn(null);

        RuleResult result = rule.apply(null, mockContext);

        assertFalse(result.isPass());
        assertEquals("不符合荣誉要求", result.getReason());
    }
}
