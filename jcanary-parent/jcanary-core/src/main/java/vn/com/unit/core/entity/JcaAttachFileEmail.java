/*******************************************************************************
 * Class        ：JcaAttachFile
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
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
 * JcaAttachFile
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_ATTACH_FILE_EMAIL)
public class JcaAttachFileEmail extends AbstractTracking {
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_ATTACH_FILE_EMAIL)
    private Long id;
    
    @Column(name = "FILE_NAME")
    private String fileName;
    
    @Column(name = "FILE_SIZE")
    private Long fileSize;
    
    @Column(name = "FILE_TYPE")
    private String fileType;
    
    @Column(name = "CONTENT_TYPE")
    private String contentType;
    
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    @Column(name = "REPOSITORY_ID")
    private Long repositoryId;
    
    @Column(name = "FILE_PATH")
    private String filePath;
    
    @Column(name = "REFERENCE_ID")
    private Long referenceId;
    
    @Column(name = "REFERENCE_KEY")
    private String referenceKey;
    
    @Column(name = "ATTACH_TYPE")
    private String attachType;
    
    @Column(name ="UUID_EMAIL")
    private String uuidEmail;

}
