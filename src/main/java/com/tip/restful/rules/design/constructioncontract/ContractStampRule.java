package com.tip.restful.rules.design.constructioncontract;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ContractStampRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        //大模型提取
        String partyA = (String)context.get(DataKeyConstant.PARTY_A);
        String partyB = (String)context.get(DataKeyConstant.PARTY_B);
        Map<String, String> contractPeriod = (Map<String, String>) context.get(DataKeyConstant.CONTRACT_PERIOD);

        //汤工系统提取
        String exhibitionStart = (String) context.get(DataKeyConstant.EXHIBITION_START);
        String exhibitionEnd = (String) context.get(DataKeyConstant.EXHIBITION_END);

        //数据库提取
        String targetCompany = (String) context.get(DataKeyConstant.COMPANY_NAME);
        String specialBoothName = (String) context.get(DataKeyConstant.SPECIAL_BOOTH_NAME);

        if(partyA == null || partyA.isEmpty() || !partyA.equals(targetCompany)) return RuleResult.fail("甲方信息不匹配");
        if(partyB == null || partyB.isEmpty() || !partyB.equals(specialBoothName)) return RuleResult.fail("乙方信息不匹配");

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime eStartTime = LocalDateTime.parse(exhibitionStart, formatter);
            LocalDateTime eEndTime = LocalDateTime.parse(exhibitionEnd, formatter);

            // 展会开始前3天
            LocalDateTime requiredStart = eStartTime.minusDays(3);
            LocalDateTime requiredEnd = eEndTime;

            LocalDateTime contractStart = LocalDateTime.parse(contractPeriod.get("start"), formatter);
            LocalDateTime contractEnd = LocalDateTime.parse(contractPeriod.get("end"), formatter);

            if (contractStart.isAfter(requiredStart) || contractEnd.isBefore(requiredEnd)) {
                return RuleResult.fail("保险期限不足");
            }
        } catch (Exception e) {
            return RuleResult.fail("保险时间格式错误或缺失");
        }

        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "基本信息与印章必须一致";
    }
}
