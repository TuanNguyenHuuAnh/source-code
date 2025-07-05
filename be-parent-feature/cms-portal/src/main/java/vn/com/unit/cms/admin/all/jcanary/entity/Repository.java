/*******************************************************************************
 * Class        Repository
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/08 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
//import vn.com.unit.jcanary.constant.DatabaseConstant;

@Table(name = "JCA_M_REPOSITORY") // DatabaseConstant.TABLE_REPOSITORY
public class Repository extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_REPOSITORY")
    private Long id;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "duration_start")
    private Date durationStart;
    
    @Column(name = "duration_end")
    private Date durationEnd;
    
    @Column(name = "physical_path")
    private String physicalPath;
    
    @Column(name = "sub_folder_rule")
    private String subFolderRule;
    
    @Column(name = "active")
    private Boolean active = false;
    
    @Column(name = "type_repo")
    private String typeRepo;
    
	@Column(name = "description")
	private String description;
    
    /**
     * Get id
     * @return Long
     * @author KhoaNA
     */
    public Long getId() {
        return id;
    }
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Get name
     * @return String
     * @author KhoaNA
     */
    public String getName() {
        return name;
    }
    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get durationStart
     * @return Date
     * @author KhoaNA
     */
    public Date getDurationStart() {
        return durationStart;
    }
    /**
     * Set durationStart
     * @param   durationStart
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setDurationStart(Date durationStart) {
        this.durationStart = durationStart;
    }
    /**
     * Get durationEnd
     * @return Date
     * @author KhoaNA
     */
    public Date getDurationEnd() {
        return durationEnd;
    }
    /**
     * Set durationEnd
     * @param   durationEnd
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setDurationEnd(Date durationEnd) {
        this.durationEnd = durationEnd;
    }
    /**
     * Get physicalPath
     * @return String
     * @author KhoaNA
     */
    public String getPhysicalPath() {
        return physicalPath;
    }
    /**
     * Set physicalPath
     * @param   physicalPath
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setPhysicalPath(String physicalPath) {
        this.physicalPath = physicalPath;
    }
    /**
     * Get typeRepo
     * @return String
     * @author hand
     */
    public String getTypeRepo() {
        return typeRepo;
    }
    /**
     * Set typeRepo
     * @param   typeRepo
     *          type String
     * @return
     * @author  hand
     */
    public void setTypeRepo(String typeRepo) {
        this.typeRepo = typeRepo;
    }
    
	public String getSubFolderRule() {
		return subFolderRule;
	}
	
	public void setSubFolderRule(String subFolderRule) {
		this.subFolderRule = subFolderRule;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
