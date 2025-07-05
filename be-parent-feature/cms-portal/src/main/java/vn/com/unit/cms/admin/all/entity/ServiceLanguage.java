package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;

@Table( name = "m_service_language")
public class ServiceLanguage {
	
	@Id
    @Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_SERVICE_LANGUAGE")
	private Long id;
    @Column(name = "m_service_id")
	private Long serviceId;
    @Column(name = "m_language_code")
    private String mLanguageCode;		
	@Column(name = "title")
    private String title;
	@Column(name = "m_customer_type_name")
	private String mCustomerTypeName;
	@Column(name = "description_abv")
	private String descriptionAbv;
	@Column(name = "description_slogan")
	private String descriptionSlogan;
	
	@Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP) 
    private Date createDate;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP) 
    private Date updateDate;
    @Column(name = "delete_date")
    @Temporal(TemporalType.TIMESTAMP) 
    private Date deleteDate;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "delete_by")
    private String deleteBy;
    
    /**
     * Get id
     * @return Long
     * @author tungns
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  tungns
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get mLanguageCode
     * @return String
     * @author tungns
     */
    public String getmLanguageCode() {
        return mLanguageCode;
    }

    /**
     * Set mLanguageCode
     * @param   mLanguageCode
     *          type String
     * @return
     * @author  tungns
     */
    public void setmLanguageCode(String mLanguageCode) {
        this.mLanguageCode = mLanguageCode;
    }
   
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public String getmCustomerTypeName() {
		return mCustomerTypeName;
	}

	public void setmCustomerTypeName(String mCustomerTypeName) {
		this.mCustomerTypeName = mCustomerTypeName;
	}

    /**
     * Get description
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getDescriptionAbv() {
        return descriptionAbv;
    }

    /**
     * Set descriptionAbv
     * @param   descriptionAbv
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setDescriptionAbv(String description) {
        this.descriptionAbv = description;
    }

    /**
     * Get descriptionSlogan
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getDescriptionSlogan() {
        return descriptionSlogan;
    }

    /**
     * Set descriptionSlogan
     * @param   descriptionSlogan
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setDescriptionSlogan(String descriptionSlogan) {
        this.descriptionSlogan = descriptionSlogan;
    }
    
}
