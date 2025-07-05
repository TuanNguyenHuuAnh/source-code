/*******************************************************************************
 * Class        ：DocumentLanguage
 * Created date ：2017/04/20
 * Lasted date  ：2017/04/20
 * Author       ：TaiTM
 * Change log   ：2017/04/20：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * DocumentLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = "m_document_language")
@Getter
@Setter
public class DocumentLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_DOCUMENT_LANGUAGE")
    private Long id;

    @Column(name = "m_document_id")
    private Long documentId;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "KEYWORDS")
    private String keyword;

    @Column(name = "KEYWORDS_DESC")
    private String keywordDescription;

    @Column(name = "KEYWORDS_SEO")
    private String linkAlias;
}
