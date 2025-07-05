/*******************************************************************************
 * Class        ：JcaRepository
 * Created date ：2020/07/28
 * Lasted date  ：2020/07/28
 * Author       ：tantm
 * Change log   ：2020/07/28：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;
import vn.com.unit.storage.constant.StorageConstant;

/**
 * 
 * JcaRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
@Table(name = StorageConstant.TABLE_JCA_REPOSITORY)
public class JcaRepository extends AbstractTracking {

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = StorageConstant.SEQ + StorageConstant.TABLE_JCA_REPOSITORY)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHYSICAL_PATH")
    private String physicalPath;

//    @Column(name = "DURATION_START")
//    private Date durationStart;
//
//    @Column(name = "DURATION_END")
//    private Date durationEnd;

    @Column(name = "SUB_FOLDER_RULE")
    private String subFolderRule;
    
    @Column(name = "TYPE_REPO")
    private String typeRepo;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "FILE_PROTOCOL")
    private Long fileProtocol;
    
    @Column(name = "SITE")
    private String site;

    @Column(name = "PATH")
    private String path;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACTIVED")
    private Boolean actived = false;

    @Column(name = "COMPANY_ID")
    private Long companyId;

}
