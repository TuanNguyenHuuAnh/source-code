package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;

@Table( name = "m_service")
public class Service {
	
	@Id
    @Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_SERVICE")
	private Long id;	
	
	@Column(name = "m_customer_type_id")
	private String customerTypeIdList;
	@Column(name = "m_customer_type_name")
	private String mCustomerTypeName;
	@Column(name = "code")
	private String code;
	@Column(name = "name")
    private String name;
	@Column(name = "description")
    private String description;
	@Column(name = "note")
	private String note;
	@Column(name = "sort_order")
	private int sortOrder;
	@Column(name = "image_url")
    private String imageUrl;
	@Column(name = "image_name")
	private String imageName;
	
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
     * Get code
     * @return String
     * @author tungns
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  tungns
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get createDate
     * @return DateTime
     * @author tungns
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * @param   createDate
     *          type DateTime
     * @return
     * @author  tungns
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getCustomerTypeIdList() {
		return customerTypeIdList;
	}

	public void setCustomerTypeIdList(String customerTypeIdList) {
		this.customerTypeIdList = customerTypeIdList;
	}

	public String getmCustomerTypeName() {
		return mCustomerTypeName;
	}

	public void setmCustomerTypeName(String mCustomerTypeName) {
		this.mCustomerTypeName = mCustomerTypeName;
	}

    /**
     * Get imageUrl
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Set imageUrl
     * @param   imageUrl
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Get imageName
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Set imageName
     * @param   imageName
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
