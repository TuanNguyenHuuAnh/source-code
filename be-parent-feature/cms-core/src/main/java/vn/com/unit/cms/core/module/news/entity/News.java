/*******************************************************************************
 * Class        ：News
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：hand
 * Change log   ：2017/02/23：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.news.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * News
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_news")
@Getter
@Setter
public class News extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_NEWS")
    private Long id;

    @Column(name = "m_news_type_id")
    private Long mNewsTypeId;

    @Column(name = "m_news_category_id")
    private Long mNewsCategoryId;

    @Column(name = "customer_type_id")
    private Long custTypeId;

    @Column(name = "code")
    private String code;

    @Column(name = "link_alias")
    private String linkAlias;

    @Column(name = "note")
    private String note;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "posted_date")
    private Date postedDate;

    @Column(name = "HOMEPAGE")
    private boolean homepage;

    @Column(name = "EVENT")
    private boolean event;

    @Column(name = "HOT")
    private boolean hot;
    
    @Column(name= "CHANNEL")
    private String channel;
}
