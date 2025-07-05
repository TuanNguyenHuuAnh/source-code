/*******************************************************************************
 * Class        ：UserGuide
 * Created date ：2019/11/12
 * Lasted date  ：2019/11/12
 * Author       ：taitt
 * Change log   ：2019/11/12：01-00 taitt create a new
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
 * UserGuide
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Table(name = CoreConstant.TABLE_JCA_USER_GUIDE)
@Getter
@Setter
public class JcaUserGuide extends AbstractTracking {

    /** Column: ID type decimal(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_USER_GUIDE)
    private Long id;

    @Column(name = "APP_CODE")
    private String appCode;

    @Column(name = "LANG_ID")
    private Long langId;

    @Column(name = "LANG_CODE")
    private String langCode;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "FILE_REPO_ID")
    private String fileRepoId;

    @Column(name = "FILE_SIZE")
    private String fileSize;

    @Column(name = "COMPANY_ID")
    private Long companyId;

}
