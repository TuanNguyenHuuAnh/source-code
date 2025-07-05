/*******************************************************************************
 * Class        :JcaEmailTemplateLang
 * Created date :2020/01/15
 * Lasted date  :2020/01/15
 * Author       :SonND
 * Change log   :2020/01/15:01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CoreConstant.TABLE_JCA_EMAIL_TEMPLATE_LANG)
public class JcaEmailTemplateLang extends AbstractTracking {

    /** Column: ID type decimal(20,0) NOT NULL. */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_EMAIL_TEMPLATE_LANG)
    private Long id;

    /** Column: EMAIL_TEMPLATE_ID type decimal(20,0) NOT NULL. */
    @Column(name = "EMAIL_TEMPLATE_ID")
    private Long emailTemplateId;

    /** Column: LANG_CODE type nvarchar(20) NULL. */
    @Column(name = "LANG_CODE")
    private String langCode;

    /** Column: TITLE type nvarchar(225) NULL. */
    @Column(name = "TITLE")
    private String title;
    
    /** Column: NOTIFICATION type nvarchar(225) NULL. */
    @Column(name = "NOTIFICATION")
    private String notification;
}
