package com.tip.restful.constant;

public class DataKeyConstant {
    // input
    public static final String BOOTH_ID = "boothId"; //展位号

    public static final String PERIOD = "period"; //期数

    // ================

    // 整个报图信息
    public static final String REPORT_DRAWING_INFO = "reportDrawingInfo";

    // 大模型提取的信息
    public static final String MODEL_EXTRACT_INFO = "modelExtractInfo";

    //    ================ 展位三视图 ================

    // 系统内 报图信息
    public static final String DRAWING_KIND = "drawingKind"; //报图类型

    public static final String SECOND_EXHIBITION = "secondExhibition"; //是否为二层结构

    public static final String TARGET_WIDTH = "targetWidth"; //参考宽度

    public static final String TARGET_LENGTH = "targetLength"; //参考长度


    // 大模型提取信息
    public static final String FRONT_HEIGHT = "frontHeight"; //正视图高度
    public static final String SIDE_HEIGHT = "sideHeight"; //侧视图高度

    public static final String WIDTH = "width"; //宽

    public static final String LENGTH = "length"; //长

    public static final String HAS_FRONT_VIEWS = "hasFrontViews"; //基本图片要求--包含正视图
    public static final String HAS_SIDE_VIEWS = "hasSideViews"; //基本图片要求--包含侧视图
    public static final String HAS_TOP_VIEWS = "hasTopViews"; //基本图片要求--包含平视图

    //知识库信息
    public static final String ALLOW_SECOND="allowSecond"; //是否允许二层结构


    //    ================ 展位效果图 ================


    //数据库
    public static final String GREEN_TYPE = "greenType"; //系统“绿色特装-绿色特装申报类型”
    public static final String COMPANY_NAME = "companyName"; //参展商资料-企业名称

    public static final String HONOR_GRAPH = "honorGraph";//荣誉称号证明图片

    //大模型提取
    public static final String DISPLAY_WIDTH = "displayWidth"; //展示柜宽度
    public static final String DISPLAY_HEIGHT = "displayHeight"; //展示柜高度
    public static final String MATERIAL_LIST="materialList"; //图片中所有材料

    public static final String FASCIA_COMPANY_NAME = "fasciaCompanyName"; //门楣上的公司名称

    public static final String GRAPH_CONTENT = "graphContent"; //图中所有信息
    //知识库
    public static final String FORBIDDEN_MATERIAL = "forbiddenMaterial"; //知识库-禁止材料
    public static final String WOOD_MATERIAL = "woodMaterial"; //知识库-木制材料


    public static final String VIOLATION = "violationContent"; //知识库-违规关键词、违规图形名称
    public static final String HONOR = "honorContent"; // //知识库-荣誉称号



    //    ================ 瓷砖图 ================



    //大模型提取
    public static final String TILE_AREA="tileArea"; //单片瓷砖面积
    public static final String TILE_COMPANY_NAME="tileCompanyName"; //瓷砖图上的企业名称
    public static final String HAS_BOOTH="hasBooth"; //门楣是否出现企业名称







    //     ================ 基本信息 ================

    //汤工提供的接口
    public static final String COMPANY_ID="companyID";

    //报图信息
    public static final String DRAWING_COMPANY_ID="drawingCompanyID";

    //     ================  保险审核 ================




    //大模型提取
    public static final String HAS_INSURANCE="hasInsurance"; //是否含有保险单号和被保险人2个字段信息
    public static final String HAS_STAMP="hasStamp"; //是否有盖章

    public static final String INSURANCE_COMPANY="insuranceCompany"; //提取的参展企业名称
    public static final String INSURANCE_POLICY = "insurancePolicy"; //是否有“人身伤害约定无免赔”或“免赔说明
    public static final String INSURANCE_AMOUNT = "insuranceAmount"; //是否有每次赔付额

    public static final String BOOTH_AREA = "boothArea"; //一层面积大小

    public static final String ACCUMULATED_AMOUNT="accumulatedAmount"; //保单累计赔付额度

    public static final String INSURANCE_PERIOD = "insurancePeriod"; //保险期间/期限

    public static final String INSURANCE_BOOTH_ID="insuranceBoothID"; //页面或附表清单中的展位号

    // 知识库
    public static final String PREMISES_LIABILITY="premisesLiability"; //场地责任
    public static final String PERSONAL_INJURY="personalInjury"; //雇员责任
    public static final String EMPLOYER_LIABILITY="employerLiability"; //第三者的人身损害




    //     ================  施工合同 ================

    //数据库
    public static final String SPECIAL_BOOTH_NAME="specialBoothName"; //特装施工服务商资料-特装施工服务商名称

    public static final String EXHIBITION_START="exhibitionStart"; //汤工库表--展会开始时间
    public static final String EXHIBITION_END="exhibitionEnd"; //汤工库表--展会结束时间

    //大模型
    public static final String IS_CONTRACT="isContract"; //图片内容是否为合同
    public static final String PARTY_A="partyA"; //甲方字段信息
    public static final String PARTY_B="partyB"; //乙方字段信息
    public static final String CONTRACT_PERIOD="contractPeriod"; //合同期限/合同有效期





    //      ===============  展位消防效果图 ================


    //大模型提取
    public static final String FIRE_MATERIAL_REQUIREMENT = "fireMaterialRequirement"; //基本图片要求：是否包含“搭建材料全部使用B1级难燃材料”字段
    public static final String FIRE_MATERIAL_LIST = "fireMaterialList"; //图片中所有材料列表

    //知识库
    public static final String FLAME_PROOF_MATERIAL = "flameProofMaterial"; //知识库-阻燃/难燃材料


    //      ===============  展位配电系统图 ================

    //大模型
    public static final String ELECTRIC_BASIC = "electricBasicRequirement"; //是否包含“配电系统”或“总开关电箱”或“客量”字段的判断
    public static final String MAIN_INFO ="mainInfo"; //主电缆信息
    public static final String BRANCH_INFO ="branchInfo"; //分电缆信息
    public static final String ELECTRIC_LAYOUT ="electricLayout"; //配电图其他信息

    //知识库
    public static final String TARGET_MAXINCURRENT="targetMaxIncurrent"; //知识库中允许最大电流
    public static final String TARGET_CABLE_TYPE="targetCableType"; //知识库“电缆型号”清单中


    //    ===============  展位电气平面图 ================

    //数据库
    public static final String TARGET_BOX_TYPE="targetBoxType"; //报图资料-展示用电电箱规格
    public static final String TARGET_BOX_NUMBER="targetBoxNumber";//"展位配电系统图"中的电箱数量


    //大模型
    public static final String ELECTRIC_TOP_REQUIREMENT="electricTopRequirement"; //电气平面图基本要求
    public static final String ELECTRIC_BOX="electricBox"; //电箱信息类，包含电箱数量和电箱规格


    //    ===============  施工安全承诺书 ================


    //大模型
    public static final String CONSTRUCTION_SAFETY_REQUIREMENT="constructionSafeetyRequirement"; //图片是否包含“广交会特装施工安全责任承诺书”
    public static final String SAFETY_STAMP="safetyStamp"; //合同上的印章信息




}
