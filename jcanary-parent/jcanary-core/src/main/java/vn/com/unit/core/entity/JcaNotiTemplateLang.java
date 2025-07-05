/*******************************************************************************
 * Class        ：JcaNotiTemplateLang
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/

package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * <p>
 * JcaNotiTemplateLang
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_NOTI_TEMPLATE_LANG)
public class JcaNotiTemplateLang extends AbstractAuditTracking {

    /** The noti id. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "NOTI_ID")
    private Long notiId;
    
    /** The lang id. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "LANG_ID")
    private Long langId;

    /** The lang code. */
    @Column(name = "LANG_CODE")
    private String langCode;

    /** The template title. */
    @Column(name = "TEMPLATE_TITLE")
    private String templateTitle;
    
    /** The template content. */
    @Column(name = "TEMPLATE_CONTENT")
    private String templateContent;
}
