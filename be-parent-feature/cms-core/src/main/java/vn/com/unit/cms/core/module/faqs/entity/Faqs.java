/*******************************************************************************
 * Class        ：Faqs
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.faqs.entity;

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
 * Faqs
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = "m_faqs")
@Getter
@Setter
public class Faqs extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_FAQS")
    private Long id;

    @Column(name = "m_customer_type_id")
    private Long customerId;

    @Column(name = "m_faqs_category_id")
    private Long categoryId;

    @Column(name = "code")
    private String code;

    @Column(name = "note")
    private String note;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "DOC_ID")
    private Long docId;

    @Column(name = "POSTED_DATE")
    private Date postedDate;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;
}