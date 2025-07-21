package com.tip.restful.rules.design.tile;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CompanyNameRuleTest {

    private CompanyNameRule rule;
    private RuleContext mockContext;

    @Before
    public void setUp() {
        rule = new CompanyNameRule();
        mockContext = Mockito.mock(RuleContext.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 瓷砖图中公司名称是数据库中公司名称的简称
     */
    @Test
    public void testApply_CompanyNameContainsTileName_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.TILE_COMPANY_NAME)).thenReturn("AB公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("AB技术有限公司");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC02: 瓷砖图中的公司名称和数据库中公司名称相同
     */
    @Test
    public void testApply_CompanyNamesAreEqual_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.TILE_COMPANY_NAME)).thenReturn("AB技术有限公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("AB技术有限公司");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC03: 瓷砖图中可以没有公司名称
     */
    @Test
    public void testApply_TileCompanyNameIsNull_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.TILE_COMPANY_NAME)).thenReturn(null);
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("AB技术有限公司");

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    /**
     * TC04: 瓷砖图中的公司名称与数据库中的公司名称不符
     */
    @Test
    public void testApply_CompanyNameMismatch_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.TILE_COMPANY_NAME)).thenReturn("BC公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("AB技术有限公司");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("公司名称(BC)与报备的参展商资料-企业名称(AB技术)不符", result.getReason());
    }
}
