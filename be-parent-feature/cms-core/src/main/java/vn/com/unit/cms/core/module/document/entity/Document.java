/*******************************************************************************
 * Class        ：Document
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：TaiTM
 * Change log   ：2017/04/18：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.entity;

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
 * Document
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = "m_document")
@Getter
@Setter
public class Document extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_DOCUMENT")
    private Long id;

    @Column(name = "m_customer_type_id")
    private Long customerTypeId;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "code")
    private String code;

    @Column(name = "M_CATEGORY_ID")
    private Long categoryId;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "SORT")
    private Long sort;

    @Column(name = "DOC_ID")
    private Long docId;

    @Column(name = "POSTED_DATE")
    private Date postedDate;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;
    
    @Column(name = "CHANNEL")
    private String channel;
}
