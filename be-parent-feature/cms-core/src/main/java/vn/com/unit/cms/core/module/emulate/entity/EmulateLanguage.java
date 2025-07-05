/*******************************************************************************
 * Class        ：NewsLanguage
 * Created date ：2017/02/24
 * Lasted date  ：2017/02/24
 * Author       ：TaiTM
 * Change log   ：2017/02/24：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.emulate.entity;

import javax.persistence.Lob;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * NewsLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
@Table(name = CmsTableConstant.TABLE_EMULATE_LANGUAGE)
public class EmulateLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_TABLE_EMULATE_LANGUAGE)
    private Long id;

    @Column(name = "M_EMULATE_ID")
    private Long mEmulateId;

    @Column(name = "M_LANGUAGE_CODE")
    private String languageCode;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SHORT_CONTENT")
    private String shortContent;

    @Column(name = "CONTENT")
    @Lob
    private byte[] content;

    @Column(name = "LINK_ALIAS")
    private String linkAlias;

    @Column(name = "KEYWORD")
    private String keyWord;

    @Column(name = "KEYWORD_DESCRIPTION")
    private String descriptionKeyword;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "physical_img_url")
    private String physicalImgUrl;
}