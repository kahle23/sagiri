package sagiri.content.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sagiri.content.persistence.entity.ArticleRelation;

import java.util.List;

/**
 * ArticleRelationMapper.
 * @author Kahle
 * @date 2021-03-05T10:23:44.708+0800
 */
@Mapper
public interface ArticleRelationMapper {

    /* (Start) This will be covered, please do not modify. */
    /**
     * Insert.
     * @param record The object to insert
     * @return Number of rows effected
     */
    int insert(ArticleRelation record);

    /**
     * Insert batch.
     * @param recordList A list of records to insert
     * @return Number of rows effected
     */
    int insertBatch(@Param("recordList") List<ArticleRelation> recordList);

    /**
     * Insert selective.
     * @param record The object to insert
     * @return Number of rows effected
     */
    int insertSelective(ArticleRelation record);

    /**
     * Delete by primary key.
     * @param id Primary key
     * @param updaterId Current operator
     * @return Number of rows effected
     */
    int deleteByPrimaryKey(@Param("id") Long id, @Param("updaterId") String updaterId);

    /**
     * Delete by primary key list.
     * @param idList Primary key list
     * @param updaterId Current operator
     * @return Number of rows effected
     */
    int deleteByPrimaryKeyList(@Param("idList") List<Long> idList, @Param("updaterId") String updaterId);

    /**
     * Delete selective.
     * @param record Delete conditions
     * @return Number of rows effected
     */
    int deleteSelective(ArticleRelation record);

    /**
     * Update by primary key.
     * @param record Content to be updated
     * @return Number of rows effected
     */
    int updateByPrimaryKey(ArticleRelation record);

    /**
     * Update by primary key selective.
     * @param record Content to be updated
     * @return Number of rows effected
     */
    int updateByPrimaryKeySelective(ArticleRelation record);

    /**
     * Conditional counting.
     * @param record Query condition
     * @return Count result
     */
    int countSelective(ArticleRelation record);

    /**
     * Query by primary key.
     * @param id Primary key
     * @return Query result
     */
    ArticleRelation queryByPrimaryKey(@Param("id") Long id);

    /**
     * Query by primary key list.
     * @param idList Primary key list
     * @return Query result list
     */
    List<ArticleRelation> queryByPrimaryKeyList(@Param("idList") List<Long> idList);

    /**
     * Find one.
     * @param record Query condition
     * @return Query result
     */
    ArticleRelation findOne(ArticleRelation record);

    /**
     * Query selective.
     * @param record Query condition
     * @return Query result list
     */
    List<ArticleRelation> querySelective(ArticleRelation record);
    /* (End) This will be covered, please do not modify. */
    /* Generated by artoria-extend in 2021-03-05T10:23:45.682+0800. */

    /**
     * 批量删除文章关系
     */
    int batchDelete(@Param("articleId") Long articleId,
                    @Param("type") Integer type,
                    @Param("targetIdList") List<Long> targetIdList,
                    @Param("updaterId") String updaterId);

    /**
     * 根据 文章ID集合 和 文章关系类型 批量查找 文章关系列表
     */
    List<ArticleRelation> findListByArticleIdList(
            @Param("articleIdList") List<Long> articleIdList,
            @Param("type") Integer type);

}
