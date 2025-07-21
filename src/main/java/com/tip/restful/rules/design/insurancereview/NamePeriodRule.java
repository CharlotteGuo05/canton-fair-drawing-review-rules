package com.tip.restful.rules.design.insurancereview;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class NamePeriodRule implements Rule {

    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        // 数据库提取
        String targetCompany = (String) context.get(DataKeyConstant.COMPANY_NAME);
        String boothID = (String) context.get(DataKeyConstant.BOOTH_ID);
        String specialBoothName = (String) context.get(DataKeyConstant.SPECIAL_BOOTH_NAME);

        // 汤工接口提取
        String exhibitionStart = (String) context.get(DataKeyConstant.EXHIBITION_START);
        String exhibitionEnd = (String) context.get(DataKeyConstant.EXHIBITION_END);

        // 大模型提取
        String insurance = (String) context.get(DataKeyConstant.INSURANCE_COMPANY);
        String insuranceBoothID = (String) context.get(DataKeyConstant.INSURANCE_BOOTH_ID);
        List<String> insurancePeriod = (List<String>) context.get(DataKeyConstant.INSURANCE_PERIOD);

        // 1. 被保险人信息校验
        if (!(insurance != null && (insurance.equals(targetCompany) || insurance.equals(specialBoothName)))) {
            return RuleResult.fail("被保险人信息("+insurance+")与企业名称或特装施工服务商名称不匹配");
        }

        // 2. 展位号校验
        if (!(boothID != null && boothID.equals(insuranceBoothID))) {
            return RuleResult.fail("页面或附表清单中的展位号与基本信息中的展位号不匹配");
        }

        // 3. 保险期限校验
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime eStartTime = LocalDateTime.parse(exhibitionStart, formatter);
            LocalDateTime eEndTime = LocalDateTime.parse(exhibitionEnd, formatter);

            // 展会开始前3天
            LocalDateTime requiredStart = eStartTime.minusDays(3);
            LocalDateTime requiredEnd = eEndTime;

            LocalDateTime iStartTime = LocalDateTime.parse(insurancePeriod.get(0), formatter);
            LocalDateTime iEndTime = LocalDateTime.parse(insurancePeriod.get(1), formatter);

            if (iStartTime.isAfter(requiredStart) || iEndTime.isBefore(requiredEnd)) {
                return RuleResult.fail("保险期限（"+iStartTime.format(formatter)+"至"+iEndTime.format(formatter)+"）不足展会时间加前三日布展期");
            }
        } catch (Exception e) {
            return RuleResult.fail("保险时间格式错误或缺失");
        }

        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "公司名称、展位号、日期、届数期数是否合规";
    }
}
