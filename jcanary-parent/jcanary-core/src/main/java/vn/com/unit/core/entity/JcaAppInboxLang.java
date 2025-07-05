/*******************************************************************************
 * Class        ：JcaAppInboxLang
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：SonND
 * Change log   ：2021/02/01：01-00 SonND create a new
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
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaAppInboxLang
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_APP_INBOX_LANG)
public class JcaAppInboxLang extends AbstractTracking {

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_APP_INBOX_LANG)
    private Long id;

    @Column(name = "APP_INBOX_ID")
    private Long appInboxId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LANG_CODE")
    private String langCode;
}
