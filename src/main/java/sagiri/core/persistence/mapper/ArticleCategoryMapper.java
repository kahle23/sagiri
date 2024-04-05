package sagiri.core.persistence.mapper;

import sagiri.core.persistence.entity.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ArticleCategoryMapper.
 * @author Kahle
 * @date 2020-11-30T16:23:04.911+0800
 */
@Mapper
public interface ArticleCategoryMapper {

    /* (Start) This will be covered, please do not modify. */
    /**
     * Insert.
     * @param record The object to insert
     * @return Number of rows effected
     */
    int insert(ArticleCategory record);

    /**
     * Insert batch.
     * @param recordList A list of records to insert
     * @return Number of rows effected
     */
    int insertBatch(@Param("recordList") List<ArticleCategory> recordList);

    /**
     * Insert selective.
     * @param record The object to insert
     * @return Number of rows effected
     */
    int insertSelective(ArticleCategory record);

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
    int deleteSelective(ArticleCategory record);

    /**
     * Update by primary key.
     * @param record Content to be updated
     * @return Number of rows effected
     */
    int updateByPrimaryKey(ArticleCategory record);

    /**
     * Update by primary key selective.
     * @param record Content to be updated
     * @return Number of rows effected
     */
    int updateByPrimaryKeySelective(ArticleCategory record);

    /**
     * Conditional counting.
     * @param record Query condition
     * @return Count result
     */
    int countSelective(ArticleCategory record);

    /**
     * Query by primary key.
     * @param id Primary key
     * @return Query result
     */
    ArticleCategory queryByPrimaryKey(@Param("id") Long id);

    /**
     * Query by primary key list.
     * @param idList Primary key list
     * @return Query result list
     */
    List<ArticleCategory> queryByPrimaryKeyList(@Param("idList") List<Long> idList);

    /**
     * Find one.
     * @param record Query condition
     * @return Query result
     */
    ArticleCategory findOne(ArticleCategory record);

    /**
     * Query selective.
     * @param record Query condition
     * @return Query result list
     */
    List<ArticleCategory> querySelective(ArticleCategory record);
    /* (End) This will be covered, please do not modify. */
    /* Generated by artoria-extend in 2020-11-30T16:23:05.395+0800. */

}