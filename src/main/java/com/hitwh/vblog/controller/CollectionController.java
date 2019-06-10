package com.hitwh.vblog.controller;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.inparam.CollectionInParam;
import com.hitwh.vblog.model.CollectionModel;
import com.hitwh.vblog.outparam.ArticleOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.response.PageResponse;
import com.hitwh.vblog.service.CollectionService;
import com.hitwh.vblog.service.impl.CollectionServiceImpl;
import com.hitwh.vblog.util.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 臧博浩
 * @date 2019/6/5 17:23
 */

/**
 * 用于处理文章的收藏管理
 */
@RestController
@RequestMapping("/collection")
public class CollectionController extends BaseController{

    @Autowired
    CollectionServiceImpl collectionService;

    @LoginRequired
    @RequestMapping("/add")
    public CommonReturnType addCollection (@RequestBody CollectionInParam collectionInParam) throws BusinessException {
        CollectionModel collectionModel = new CollectionModel();
        collectionModel.setArticleId(collectionInParam.getArticle_id());
        collectionModel.setUserId(collectionInParam.getUid());
        collectionService.addCollection(collectionModel);

        return CommonReturnType.success();
    }

    @LoginRequired
    @RequestMapping("/delete")
    public CommonReturnType deleteCollection (@RequestBody CollectionInParam collectionInParam) throws BusinessException {
        CollectionModel collectionModel = new CollectionModel();
        collectionModel.setArticleId(collectionInParam.getArticle_id());
        collectionModel.setUserId(collectionInParam.getUid());
        collectionService.deleteCollection(collectionModel);

        return CommonReturnType.success();
    }

    @RequestMapping("/query_collection_number")
    public CommonReturnType queryCollectionNumber (@RequestBody CollectionInParam collectionInParam) throws BusinessException {
        CollectionModel collectionModel = new CollectionModel();
        collectionModel.setArticleId(collectionInParam.getArticle_id());

        return CommonReturnType.create(collectionService.queryCollectionNum(collectionModel));
    }

    @RequestMapping("/query_collection")
    public CommonReturnType queryCollection (@RequestBody CollectionInParam collectionInParam) throws BusinessException {
        Map<String,Object> map = collectionService.queryCollection(
                        collectionInParam.getStart(),
                        collectionInParam.getEnd(),
                        collectionInParam.getUid()
                );
        List<ArticleOutParam> articleOutParams = (ArrayList)map.get("list");

        int sum = (int)map.get("sum");

        if(articleOutParams.size() ==
                collectionInParam.getEnd() - collectionInParam.getStart() + 1)
            return CommonReturnType.create(PageResponse.create(collectionInParam.getStart(),
                    collectionInParam.getEnd(),sum,articleOutParams));
        else
            return CommonReturnType.create(PageResponse.create(collectionInParam.getStart(),
                    collectionInParam.getStart() + articleOutParams.size() - 1
                    ,sum,articleOutParams));
    }

    @RequestMapping("/query_if_collected")
    public CommonReturnType queryIfCollected (@RequestBody CollectionInParam collectionInParam) throws BusinessException {
        CollectionModel collectionModel = new CollectionModel();
        collectionModel.setUserId(collectionInParam.getUid());
        collectionModel.setArticleId(collectionInParam.getArticle_id());

        return CommonReturnType.create(collectionService.queryIfCollect(collectionModel));
    }
}
