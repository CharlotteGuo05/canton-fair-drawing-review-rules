package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.electric.BoothElectricLayout.SwitchCableRule.BranchInfo;
import com.tip.restful.rules.electric.BoothElectricLayout.SwitchCableRule.MainInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SwitchCableRuleTest {

    private SwitchCableRule rule;
    private RuleContext mockContext;
    private MainInfo mockMain;
    private BranchInfo mockBranch;

    @Before
    public void setUp() {
        rule = new SwitchCableRule();
        mockContext = Mockito.mock(RuleContext.class);
        mockMain = Mockito.mock(MainInfo.class);
        mockBranch = Mockito.mock(BranchInfo.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 主开关电流超过允许最大值，返回失败
     */
    @Test
    public void testApply_MainCurrentOverLimit_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.MAIN_INFO)).thenReturn(mockMain);
        when(mockMain.getMain_i()).thenReturn(80);  // 实际值
        when(mockContext.get(DataKeyConstant.TARGET_MAXINCURRENT)).thenReturn("60"); // 知识库允许最大值

        when(mockContext.get(DataKeyConstant.BRANCH_INFO)).thenReturn(Collections.emptyList());

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("主开关电流("+mockMain.getMain_i()+"A)不得超过知识库中允许通过最大电流("+mockContext.get(DataKeyConstant.TARGET_MAXINCURRENT)+"A)", result.getReason());
    }

    /**
     * TC02: 分支电流超过最大允许值，返回失败
     */
    @Test
    public void testApply_BranchCurrentOverLimit_ReturnsFail() {
        when(mockContext.get(DataKeyConstant.MAIN_INFO)).thenReturn(mockMain);
        when(mockMain.getMain_i()).thenReturn(50); // 合法值
        when(mockContext.get(DataKeyConstant.TARGET_MAXINCURRENT)).thenReturn("60");

        when(mockBranch.getBranch_i()).thenReturn(70); // 超过
        when(mockContext.get(DataKeyConstant.BRANCH_INFO)).thenReturn(Collections.singletonList(mockBranch));

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("回路电流("+mockBranch.getBranch_i()+"A)不得超过知识库中最大允许电流("+mockContext.get(DataKeyConstant.TARGET_MAXINCURRENT)+"A)", result.getReason());
    }

    /**
     * TC03: 所有电流值都在允许范围内，返回通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        when(mockContext.get(DataKeyConstant.MAIN_INFO)).thenReturn(mockMain);
        when(mockMain.getMain_i()).thenReturn(50);
        when(mockContext.get(DataKeyConstant.TARGET_MAXINCURRENT)).thenReturn("100");

        when(mockBranch.getBranch_i()).thenReturn(80);
        when(mockContext.get(DataKeyConstant.BRANCH_INFO)).thenReturn(Collections.singletonList(mockBranch));

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }
}
