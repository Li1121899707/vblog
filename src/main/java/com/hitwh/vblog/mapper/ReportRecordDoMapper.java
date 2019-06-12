package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ReportAndArticleDo;
import com.hitwh.vblog.bean.ReportRecordDo;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportRecordDoMapper {
    int insert(ReportRecordDo record);

    int insertSelective(ReportRecordDo record);

    List<ReportAndArticleDo> selectAllReportRecords(@Param("start") Integer start,
                                                   @Param("num") Integer num);

    Integer selectAllReportRecordsNum();

    List<ReportAndArticleDo> selectReportRecordsByArticleId(@Param("start") Integer start,
                                                    @Param("num") Integer num,
                                                            @Param("articleId") Integer articleId);

    Integer selectReportRecordsByArticleIdNum(@Param("articleId") Integer articleId);

    List<ReportAndArticleDo> selectReportRecordsByHandleResult(@Param("start") Integer start,
                                                            @Param("num") Integer num,
                                                            @Param("handleResult") Integer handleResult);

    Integer selectReportRecordsByHandleResultNum(@Param("handleResult") Integer handleResult);

    Integer updateByArticleId(ReportRecordDo record);

    Integer selectIfReported(ReportRecordDo record);

    Integer selectIfArticleExist(ReportRecordDo record);
}