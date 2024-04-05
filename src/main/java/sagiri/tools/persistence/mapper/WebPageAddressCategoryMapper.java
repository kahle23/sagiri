package sagiri.tools.persistence.mapper;

import sagiri.tools.persistence.entity.WebPageAddressCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WebPageAddressCategoryMapper.
 * @author Kahle
 * @date 2020-10-10T18:22:02.946+0800
 */
@Mapper
public interface WebPageAddressCategoryMapper {

    /* (Start) This will be covered, please do not modify. */
    /**
     * Insert.
     * @param record The object to insert
     * @return Number of rows effected
     */
    int insert(WebPageAddressCategory record);

    /**
     * Insert batch.
     * @param recordList A list of records to insert
     * @return Number of rows effected
     */
    int insertBatch(@Param("recordList") List<WebPageAddressCategory> recordList);

    /**
     * Insert selective.
     * @param record The object to insert
     * @return Number of rows effected
     */
    int insertSelective(WebPageAddressCategory record);

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
    int deleteSelective(WebPageAddressCategory record);

    /**
     * Update by primary key.
     * @param record Content to be updated
     * @return Number of rows effected
     */
    int updateByPrimaryKey(WebPageAddressCategory record);

    /**
     * Update by primary key selective.
     * @param record Content to be updated
     * @return Number of rows effected
     */
    int updateByPrimaryKeySelective(WebPageAddressCategory record);

    /**
     * Conditional counting.
     * @param record Query condition
     * @return Count result
     */
    int countSelective(WebPageAddressCategory record);

    /**
     * Query by primary key.
     * @param id Primary key
     * @return Query result
     */
    WebPageAddressCategory queryByPrimaryKey(@Param("id") Long id);

    /**
     * Query by primary key list.
     * @param idList Primary key list
     * @return Query result list
     */
    List<WebPageAddressCategory> queryByPrimaryKeyList(@Param("idList") List<Long> idList);

    /**
     * Find one.
     * @param record Query condition
     * @return Query result
     */
    WebPageAddressCategory findOne(WebPageAddressCategory record);

    /**
     * Query selective.
     * @param record Query condition
     * @return Query result list
     */
    List<WebPageAddressCategory> querySelective(WebPageAddressCategory record);
    /* (End) This will be covered, please do not modify. */
    /* Generated by artoria-extend in 2020-10-10T18:22:03.340+0800. */

}
