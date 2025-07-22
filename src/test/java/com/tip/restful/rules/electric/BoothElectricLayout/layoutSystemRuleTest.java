package com.tip.restful.rules.electric.BoothElectricLayout;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.electric.BoothElectricLayoutResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class layoutSystemRuleTest {

    private layoutSystemRule rule;
    private RuleContext mockContext;
    private BoothElectricLayoutResolver.MainInfo mockMainInfo;
    private BoothElectricLayoutResolver.BranchInfo mockBranch;
    private BoothElectricLayoutResolver.electriclayout mockLayout;

    @Before
    public void setUp() {
        rule = new layoutSystemRule();
        mockContext = Mockito.mock(RuleContext.class);
        mockMainInfo = Mockito.mock(BoothElectricLayoutResolver.MainInfo.class);
        mockBranch = Mockito.mock(BoothElectricLayoutResolver.BranchInfo.class);
        mockLayout = Mockito.mock(BoothElectricLayoutResolver.electriclayout.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
    }

    /**
     * TC01: 回路灯具超过25盏，返回失败
     */
    @Test
    public void testApply_BranchOverLimit_ReturnsFail() {
        when(mockBranch.getNumber()).thenReturn(26);
        when(mockContext.get(DataKeyConstant.BRANCH_INFO)).thenReturn(Arrays.asList(mockBranch));
        when(mockContext.get(DataKeyConstant.ELECTRIC_LAYOUT)).thenReturn(mockLayout);

        when(mockLayout.getGround()).thenReturn("True");
        when(mockLayout.getMain_cable_p()).thenReturn("True");
        when(mockLayout.getBranch_cable_p()).thenReturn("True");
        when(mockLayout.getEnergy_saving()).thenReturn("True");
        when(mockLayout.getThirtymA()).thenReturn("True");

        when(mockContext.get(DataKeyConstant.MAIN_INFO)).thenReturn(mockMainInfo);
        when(mockContext.get(DataKeyConstant.TARGET_MAXINCURRENT)).thenReturn("100");

        when(mockBranch.getCapacity()).thenReturn(2000);
        when(mockMainInfo.getMainV()).thenReturn(220);

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("回路灯具数量("+mockBranch.getNumber()+")不得超过25盏", result.getReason());
    }

    /**
     * TC02: 缺少接地说明，返回失败
     */
    @Test
    public void testApply_MissingGround_ReturnsFail() {
        when(mockBranch.getNumber()).thenReturn(10);
        when(mockContext.get(DataKeyConstant.BRANCH_INFO)).thenReturn(Arrays.asList(mockBranch));
        when(mockContext.get(DataKeyConstant.ELECTRIC_LAYOUT)).thenReturn(mockLayout);

        when(mockLayout.getGround()).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("没有接地文字说明", result.getReason());
    }

    /**
     * TC03: 主电缆p数错误，返回失败
     */
    @Test
    public void testApply_InvalidMainCableP_ReturnsFail() {
        setupValidBasicLayout();
        when(mockLayout.getMain_cable_p()).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("电箱220V没对应2P或电箱380V没对应3P", result.getReason());
    }

    /**
     * TC04: 分电缆p数错误，返回失败
     */
    @Test
    public void testApply_InvalidBranchCableP_ReturnsFail() {
        setupValidBasicLayout();
        when(mockLayout.getBranch_cable_p()).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("分电缆规格不是1P或2P", result.getReason());
    }

    /**
     * TC05: 没有节能文字说明，返回失败
     */
    @Test
    public void testApply_MissingEnergySaving_ReturnsFail() {
        setupValidBasicLayout();
        when(mockLayout.getEnergy_saving()).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("没有节能文字说明", result.getReason());
    }

    /**
     * TC06: 没有30mA≤0.1s文字说明，返回失败
     */
    @Test
    public void testApply_MissingThirtymA_ReturnsFail() {
        setupValidBasicLayout();
        when(mockLayout.getThirtymA()).thenReturn("False");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("没有30mA≤0.1s文字说明", result.getReason());
    }

    /**
     * TC07: 回路电流超标，返回失败
     */
    @Test
    public void testApply_OverMaxCurrent_ReturnsFail() {
        setupValidBasicLayout();
        when(mockBranch.getCapacity()).thenReturn(5000); // 容量 / 电压 = 5000 / 220 ≈ 22
        when(mockMainInfo.getMainV()).thenReturn(100);   // 故 maxCurrent = 50 > 30

        when(mockContext.get(DataKeyConstant.TARGET_MAXINCURRENT)).thenReturn("30");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("回路电流("+(mockBranch.getCapacity()/mockMainInfo.getMainV())+"A)不得超过知识库中最大允许电流("+mockContext.get(DataKeyConstant.TARGET_MAXINCURRENT)+"A)", result.getReason());
    }

    /**
     * TC08: 所有条件满足，返回null（代表通过）
     */
    @Test
    public void testApply_AllValid_ReturnsNull() {
        setupValidBasicLayout();
        when(mockBranch.getCapacity()).thenReturn(2000); // 2000 / 220 ≈ 9
        when(mockMainInfo.getMainV()).thenReturn(220);
        when(mockContext.get(DataKeyConstant.TARGET_MAXINCURRENT)).thenReturn("30");

        RuleResult result = rule.apply(null, mockContext);
        assertNull(result);
    }

    private void setupValidBasicLayout() {
        when(mockBranch.getNumber()).thenReturn(10);
        when(mockContext.get(DataKeyConstant.BRANCH_INFO)).thenReturn(Arrays.asList(mockBranch));
        when(mockContext.get(DataKeyConstant.MAIN_INFO)).thenReturn(mockMainInfo);
        when(mockContext.get(DataKeyConstant.ELECTRIC_LAYOUT)).thenReturn(mockLayout);

        when(mockLayout.getGround()).thenReturn("True");
        when(mockLayout.getMain_cable_p()).thenReturn("True");
        when(mockLayout.getBranch_cable_p()).thenReturn("True");
        when(mockLayout.getEnergy_saving()).thenReturn("True");
        when(mockLayout.getThirtymA()).thenReturn("True");
    }
}
