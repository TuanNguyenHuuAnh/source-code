/*******************************************************************************
 * Class        ：Faqs
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import javax.persistence.Lob;

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
@Table(name = "m_faqs_category_import")
@Getter
@Setter
public class FaqsCategoryImport  {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_FAQS_CATEGORY_IMPORT")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "FAQS_CODE")
    private String faqsCode;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "KEYWORDS_DESC")
    private String keywordsDesc;
    
    @Column(name = "KEYWORDS_SEO")
    private String keywordsSeo;

    @Column(name = "KEYWORDS")
    private String keywords;
    
    @Column(name = "content")
    @Lob
    private String content;
    
    @Column(name = "enabled")
    private String enabled;

    @Column(name = "POSTED_DATE")
    private String postedDate;

    @Column(name = "EXPIRATION_DATE")
    private String expirationDate;
    
    @Column(name = "MESSAGE_ERROR")
    private String messageError;

    @Column(name = "MESSAGE_WARNING")
    private String messageWarning;
    
    @Column(name = "IS_ERROR")
    private boolean isError;
    
    @Column(name = "STATUS")
    private int status;

    @Column(name = "REF_ID")
    private Long refId;
    
    @Column(name = "SESSION_KEY")
    private String sessionKey;
    
    @Column(name = "created_date")
    private Date createdDate;
    
    @Column(name = "created_by")
    private String createdBy;
}