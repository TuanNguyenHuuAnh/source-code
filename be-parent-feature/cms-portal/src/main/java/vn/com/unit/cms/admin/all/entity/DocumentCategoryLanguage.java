/*******************************************************************************
 * Class        ：DocumentCategoryLanguage
 * Created date ：2017/04/17
 * Lasted date  ：2017/04/17
 * Author       ：TaiTM
 * Change log   ：2017/04/17：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * DocumentCategoryLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = "M_DOCUMENT_CATEGORY_LANGUAGE")
@Getter
@Setter
public class DocumentCategoryLanguage extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_DOCUMENT_CATEGORY_LANGUAGE")
    private Long id;

    @Column(name = "M_CATEGORY_ID")
    private Long categoryId;

    @Column(name = "M_LANGUAGE_CODE")
    private String languageCode;

    @Column(name = "title")
    private String title;

    @Column(name = "KEYWORDS_SEO")
    private String keywordsSeo;

    @Column(name = "KEYWORDS")
    private String keywords;

    @Column(name = "KEYWORDS_DESC")
    private String keywordsDesc;
}