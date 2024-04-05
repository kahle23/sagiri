package sagiri.core.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * The entity of the table "t_article_category".
 * @author Kahle
 * @date 2020-11-30T16:23:04.911+0800
 */
@Data
public class ArticleCategory implements Serializable {

    /* (Start) This will be covered, please do not modify. */
    /**
     * 文章分类ID
     */
    private Long id;
    /**
     * 父分类ID
     */
    private Long parentId;
    /**
     * 分类代码
     */
    private String code;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类描述
     */
    private String description;
    /**
     * 创建者ID
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者ID
     */
    private String updaterId;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 数据状态
     */
    private Integer aliveFlag;
    /* (End) This will be covered, please do not modify. */
    /* Generated by artoria-extend in 2020-11-30T16:23:05.498+0800. */

}
