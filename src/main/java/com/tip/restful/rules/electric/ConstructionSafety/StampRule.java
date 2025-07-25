package com.tip.restful.rules.electric.ConstructionSafety;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.resolvers.electric.ConstructionSafetyResolver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StampRule implements Rule {
    @Override
    public RuleResult apply(String imageId, RuleContext context) {
        ConstructionSafetyResolver.StampInfo stamp = (ConstructionSafetyResolver.StampInfo)context.get(DataKeyConstant.SAFETY_STAMP);
        String partyA = stamp.getPartyA();
        String partyB=  stamp.getPartyB();

        //参展商资料-企业名称
        String companyName = (String)context.get(DataKeyConstant.COMPANY_NAME);
        //特装施工服务商资料-特装施工服务商名称
        String specialServiceName = (String)context.get(DataKeyConstant.SPECIAL_BOOTH_NAME);
        if(!partyA.equals(companyName)) return RuleResult.fail("印章中甲方名称("+partyA+")与参展商资料中的企业名称("+companyName+")不匹配");
        if(!partyB.equals(specialServiceName)) return RuleResult.fail("印章中乙方名称("+partyB+")与特装施工服务商资料中的特装施工服务商名称("+specialServiceName+")不匹配");


        List<String> signDates = stamp.getSignDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //汤工系统提取展会开始日期
        String exhibitionStart = (String) context.get(DataKeyConstant.EXHIBITION_START);
        LocalDateTime exhibitionDate = LocalDateTime.parse(exhibitionStart, formatter);
        for(String date : signDates){
            LocalDateTime signDate = LocalDateTime.parse(date, formatter);
            if(!signDate.isBefore(exhibitionDate)) return RuleResult.fail("合同签署日期("+signDate.format(formatter)+")不得晚于该年该期展会的最早开始日期("+exhibitionDate.format(formatter)+")");

        }

        //最后一项用大模型判断？



        return RuleResult.pass();
    }

    @Override
    public String getName() {
        return "印章要求";
    }

}
