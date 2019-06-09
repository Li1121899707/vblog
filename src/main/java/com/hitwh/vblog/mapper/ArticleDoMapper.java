package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.bean.ArticleDo;
import com.hitwh.vblog.outparam.ArticleOutParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDoMapper {
    List<ArticleAndUserDo> selectArticleByUserId(@Param("start") Integer start,
                                            @Param("num") Integer num,
                                            @Param("userId") Integer userId);

    List<ArticleAndUserDo> selectArticleByTitle(@Param("start") Integer start,
                                                 @Param("num") Integer num,
                                                 @Param("title") String title);

    List<ArticleAndUserDo> selectArticleByType(@Param("start") Integer start,
                                                 @Param("num") Integer num,
                                                 @Param("typeId") Integer typeId);

    List<ArticleAndUserDo> queryCollectionArticleByUser(@Param("start") Integer start,
                                                        @Param("num") Integer num,
                                                        @Param("userId") Integer userId);

    List<ArticleAndUserDo> selectAllArticle();

    List<ArticleAndUserDo> recommend();

    int selectArticleNumber();

    int selectArticleNumByUserId(@Param("userId")Integer userId);

    int selectArticleNumByTitle(@Param("title")String title);

    int selectArticleNumByType(@Param("typeId")Integer typeId);

    ArticleAndUserDo selectSingleArticle(Integer articleId);

    int deleteByPrimaryKey(@Param("articleId")Integer articleId, @Param("uid")Integer uid);

    int insert(ArticleDo record);

    int insertSelective(ArticleDo record);

    ArticleDo selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(ArticleDo record);

    int updateByPrimaryKey(ArticleDo record);
}