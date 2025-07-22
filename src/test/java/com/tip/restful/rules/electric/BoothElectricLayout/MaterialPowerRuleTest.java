package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.electric.BoothElectricLayoutResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MaterialPowerRuleTest {

    private MaterialPowerRule rule;
    private RuleContext mockContext;
    private BoothElectricLayoutResolver.MainInfo mockMain;
    private BoothElectricLayoutResolver.BranchInfo mockBranch;

    @Before
    public void setUp() {
        rule = new MaterialPowerRule();
        mockContext = Mockito.mock(RuleContext.class);
        mockMain = Mockito.mock(BoothElectricLayoutResolver.MainInfo.class);
        mockBranch = Mockito.mock(BoothElectricLayoutResolver.BranchInfo.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 电缆型号不在清单中，返回失败
     */
    @Test
    public void testApply_InvalidCableType_ReturnsFail() {
        setupCommonMocks();
        when(mockBranch.getCable_type()).thenReturn("ABC");
        when(mockContext.get(DataKeyConstant.TARGET_CABLE_TYPE)).thenReturn("XYZ");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("电缆型号(ABC)不在清单中", result.getReason());
    }

    /**
     * TC02: 电缆规格过小，返回失败
     */
    @Test
    public void testApply_CableSizeTooSmall_ReturnsFail() {
        setupCommonMocks();
        when(mockBranch.getCable_type()).thenReturn("XYZ");
        when(mockContext.get(DataKeyConstant.TARGET_CABLE_TYPE)).thenReturn("XYZ");
        when(mockBranch.getCable_size()).thenReturn(6); // < 7.5

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("电缆规格(6mm²)不得小于3*2.5mm²", result.getReason());
    }

    /**
     * TC03: 敷设方式为空，返回失败
     */
    @Test
    public void testApply_InstallationEmpty_ReturnsFail() {
        setupCommonMocks();
        when(mockBranch.getCable_type()).thenReturn("XYZ");
        when(mockContext.get(DataKeyConstant.TARGET_CABLE_TYPE)).thenReturn("XYZ");
        when(mockBranch.getCable_size()).thenReturn(10);
        when(mockBranch.getInstallation()).thenReturn("");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("敷设方式不能为空", result.getReason());
    }

    /**
     * TC04: 主电缆规格小于分电缆规格，返回失败
     */
    @Test
    public void testApply_MainCableTooSmall_ReturnsFail() {
        setupCommonMocks();
        when(mockBranch.getCable_type()).thenReturn("XYZ");
        when(mockContext.get(DataKeyConstant.TARGET_CABLE_TYPE)).thenReturn("XYZ");
        when(mockBranch.getCable_size()).thenReturn(10);
        when(mockBranch.getInstallation()).thenReturn("吊顶敷设");
        when(mockMain.getCable_size()).thenReturn(8); // 小于分电缆

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("主电缆规格(8mm²)不得小于分电缆规格(10mm²)", result.getReason());
    }

    /**
     * TC05: 容量 ≠ 数量 * 功率，返回失败
     */
    @Test
    public void testApply_CapacityMismatch_ReturnsFail() {
        setupCommonMocks();
        when(mockBranch.getCable_type()).thenReturn("XYZ");
        when(mockContext.get(DataKeyConstant.TARGET_CABLE_TYPE)).thenReturn("XYZ");
        when(mockBranch.getCable_size()).thenReturn(10);
        when(mockBranch.getInstallation()).thenReturn("墙面敷设");
        when(mockMain.getCable_size()).thenReturn(20);

        when(mockBranch.getNumber()).thenReturn(2);
        when(mockBranch.getPower()).thenReturn(100);
        when(mockBranch.getCapacity()).thenReturn(150); // 错误值

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("容量(150W)不等于数量(2)*功率(100W)", result.getReason());
    }

    /**
     * TC06: 所有条件满足，返回通过
     */
    @Test
    public void testApply_AllValid_ReturnsPass() {
        setupCommonMocks();
        when(mockBranch.getCable_type()).thenReturn("XYZ");
        when(mockContext.get(DataKeyConstant.TARGET_CABLE_TYPE)).thenReturn("XYZ");
        when(mockBranch.getCable_size()).thenReturn(10);
        when(mockBranch.getInstallation()).thenReturn("地面敷设");
        when(mockMain.getCable_size()).thenReturn(20);

        when(mockBranch.getNumber()).thenReturn(2);
        when(mockBranch.getPower()).thenReturn(100);
        when(mockBranch.getCapacity()).thenReturn(200); // 正确值

        RuleResult result = rule.apply(null, mockContext);
        assertTrue(result.isPass());
    }

    private void setupCommonMocks() {
        when(mockContext.get(DataKeyConstant.MAIN_INFO)).thenReturn(mockMain);
        when(mockContext.get(DataKeyConstant.BRANCH_INFO)).thenReturn(Collections.singletonList(mockBranch));
    }
}
