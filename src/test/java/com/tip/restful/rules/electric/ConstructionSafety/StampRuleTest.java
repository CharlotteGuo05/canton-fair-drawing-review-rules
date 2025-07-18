package com.tip.restful.rules.electric.ConstructionSafety;

import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.rules.electric.ConstructionSafety.StampRule.StampInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class StampRuleTest {

    private StampRule rule;
    private RuleContext mockContext;
    private StampInfo mockStamp;

    @Before
    public void setUp() {
        rule = new StampRule();
        mockContext = Mockito.mock(RuleContext.class);
        mockStamp = Mockito.mock(StampInfo.class);
    }

    @After
    public void tearDown() {
        rule = null;
        mockContext = null;
        mockStamp = null;
    }

    /**
     * TC01: 甲方不匹配，返回失败
     */
    @Test
    public void testApply_PartyAMismatch_ReturnsFail() {
        when(mockStamp.getPartyA()).thenReturn("甲方公司A");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司B");
        when(mockContext.get(DataKeyConstant.SAFETY_STAMP)).thenReturn(mockStamp);

        when(mockStamp.getPartyB()).thenReturn("乙方公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方公司");

        when(mockStamp.getSignDate()).thenReturn(Collections.singletonList("2025-07-10 10:00:00"));
        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2025-07-20 09:00:00");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("印章中甲方名称不匹配", result.getReason());
    }

    /**
     * TC02: 乙方不匹配，返回失败
     */
    @Test
    public void testApply_PartyBMismatch_ReturnsFail() {
        when(mockStamp.getPartyA()).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司");

        when(mockStamp.getPartyB()).thenReturn("乙方A");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方B");

        when(mockContext.get(DataKeyConstant.SAFETY_STAMP)).thenReturn(mockStamp);

        when(mockStamp.getSignDate()).thenReturn(Collections.singletonList("2025-07-10 10:00:00"));
        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2025-07-20 09:00:00");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("印章中乙方名称不匹配", result.getReason());
    }

    /**
     * TC03: 签署日期晚于展会开始，返回失败
     */
    @Test
    public void testApply_SignDateTooLate_ReturnsFail() {
        when(mockStamp.getPartyA()).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司");

        when(mockStamp.getPartyB()).thenReturn("乙方公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方公司");

        when(mockContext.get(DataKeyConstant.SAFETY_STAMP)).thenReturn(mockStamp);

        when(mockStamp.getSignDate()).thenReturn(Collections.singletonList("2025-07-25 10:00:00"));
        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2025-07-20 09:00:00");

        RuleResult result = rule.apply(null, mockContext);
        assertFalse(result.isPass());
        assertEquals("合同签署日期不得晚于该年该期展会的最早开始日期", result.getReason());
    }

    /**
     * TC04: 所有信息正确，签署日期合法，返回 null（通过）
     */
    @Test
    public void testApply_AllValid_ReturnsNull() {
        when(mockStamp.getPartyA()).thenReturn("甲方公司");
        when(mockContext.get(DataKeyConstant.COMPANY_NAME)).thenReturn("甲方公司");

        when(mockStamp.getPartyB()).thenReturn("乙方公司");
        when(mockContext.get(DataKeyConstant.SPECIAL_BOOTH_NAME)).thenReturn("乙方公司");

        when(mockContext.get(DataKeyConstant.SAFETY_STAMP)).thenReturn(mockStamp);

        when(mockStamp.getSignDate()).thenReturn(Collections.singletonList("2025-07-10 10:00:00"));
        when(mockContext.get(DataKeyConstant.EXHIBITION_START)).thenReturn("2025-07-20 09:00:00");

        RuleResult result = rule.apply(null, mockContext);
        assertNull(result);
    }
}
