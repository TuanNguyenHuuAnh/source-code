/*******************************************************************************
 * Class        ：FaqsLanguage
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：vinhnht
 * Change log   ：2017/02/28：01-00 vinhnht create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.faqs.entity;

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
 * FaqsLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author FaqsLanguage
 */
@Table(name = "m_faqs_language")
@Getter
@Setter
public class FaqsLanguage extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_FAQS_LANGUAGE")
    private Long id;

    @Column(name = "m_faqs_id")
    private Long faqsId;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "title")
    private String title;

    @Column(name = "short_content")
    private String shortContent;

    @Column(name = "content")
    @Lob
    private String content;

    @Column(name = "KEYWORDS_SEO")
    private String linkAlias;

    @Column(name = "KEYWORDS")
    private String keyword;

    @Column(name = "KEYWORDS_DESC")
    private String keywordDescription;
}
