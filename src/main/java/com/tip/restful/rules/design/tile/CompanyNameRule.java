package com.tip.restful.rules.design.tile;

import com.tip.restful.Rule;
import com.tip.restful.RuleContext;
import com.tip.restful.RuleResult;
import com.tip.restful.constant.DataKeyConstant;

public class CompanyNameRule implements Rule {
    @Override
    public RuleResult apply(String imageId,RuleContext context) {
        String tileCompanyName = (String) context.get(DataKeyConstant.TILE_COMPANY_NAME);
        String companyName = (String)context.get(DataKeyConstant.COMPANY_NAME);
        if(tileCompanyName == null) return RuleResult.pass();

        String normalizedCompany= normalize(companyName);
        String normalizedTileCompany= normalize(tileCompanyName);

        if(normalizedCompany.contains(normalizedTileCompany) || normalizedTileCompany.contains(normalizedCompany)) return RuleResult.pass();


        return RuleResult.fail("公司名称不符");
    }

    @Override
    public String getName() {
        return "企业名称要求";
    }

    private String normalize(String name) {
        if (name == null) return null;
        return name.replaceAll("(公司|有限公司|有限责任公司|股份有限公司)", "")
                .replaceAll("\\s+", "") ;// 去掉多余空格
    }
}
