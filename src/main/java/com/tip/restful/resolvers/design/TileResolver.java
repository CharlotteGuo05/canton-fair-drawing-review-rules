package com.tip.restful.resolvers.design;

import com.tip.restful.DataResolver;
import com.tip.restful.RuleContext;
import com.tip.restful.constant.DataKeyConstant;
import com.tip.restful.service.KnowledgeRepositoryService;

import java.util.List;

public class TileResolver implements DataResolver {

    // 数据库获取
//    private final IDaReportDrawingInfoService drawingInfoService;

    //知识库
    private final KnowledgeRepositoryService knowledgeRepositoryService;

    // 大模型
//    private final ModelExtractionService modelExtractionService;

    public TileResolver(KnowledgeRepositoryService knowledgeRepositoryService) {
        this.knowledgeRepositoryService = knowledgeRepositoryService;
    }

    @Override
    public Object resolve(String key, RuleContext context) {
        switch(key) {
            case DataKeyConstant.REPORT_DRAWING_INFO:
                // 数据库获取整个报图信息
//                return drawingInfoService.getOneReportDrawingInfo("id,测试，后面需要改");
                return "todo";
            case DataKeyConstant.MODEL_EXTRACT_INFO:
                // 获取大模型信息
//               return modelExtractionService.getExtraction(key);
                return "todo";
            case DataKeyConstant.KNOWLEDGE_BASE_INFO:
                // 获取知识库信息
                return knowledgeRepositoryService.getKnowledge(key);
            case DataKeyConstant.COMPANY_ID:
                return "todo";
            case DataKeyConstant.DRAWING_KIND:
                return "todo";
            case DataKeyConstant.TILE:
                return ((AreaRequirement)getModel(context)).getTile();
            case DataKeyConstant.TILE_COMPANY_NAME:
                return ((CompanyName)getModel(context)).getTileComapnyName();
            case DataKeyConstant.HAS_BOOTH:
                return ((BoothRequirement)getModel(context)).getHasBooth();

            default:
                throw new IllegalArgumentException("Invalid key: " + key);

        }
    }
    private Object getModel(RuleContext context) {
        return context.get(DataKeyConstant.MODEL_EXTRACT_INFO);
    }

    public class AreaRequirement{
        public List<Tile> tile;

        public List<Tile> getTile(){
            return tile;
        }

    }

    public class Tile{
        public int width;
        public int length;

        public Tile(int width, int length) {
            this.width = width;
            this.length = length;
        }

        public int getArea() {
            return width * length;
        }

    }

    public class CompanyName{
        public String tileComapnyName;

        public String getTileComapnyName(){
            return tileComapnyName;
        }
    }

    public class BoothRequirement{
        public String hasBooth;

        public String getHasBooth(){
            return hasBooth;
        }
    }
}
