/*******************************************************************************
 * Class        ：NewsCategory
 * Created date ：2017/02/24
 * Lasted date  ：2017/02/24
 * Author       ：hand
 * Change log   ：2017/02/24：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * NewsCategory
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_news_category")
@Getter
@Setter
public class NewsCategory extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_NEWS_CATEGORY")
    private Long id;

    @Column(name = "m_customer_type_id")
    private Long customerTypeId;

    @Column(name = "m_news_type_id")
    private Long mNewsTypeId;

    @Column(name = "code")
    private String code;

    @Column(name = "link_alias")
    private String linkAlias;

    @Column(name = "note")
    private String note;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "DOC_ID")
    private Long docId;
    
    @Column(name = "CATEGORY_TYPE")
    private String categoryType;
    
    @Column(name = "CHANNEL")
    private String channel;
}
