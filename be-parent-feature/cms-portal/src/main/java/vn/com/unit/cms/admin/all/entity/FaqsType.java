/*******************************************************************************
 * Class        ：FaqsType
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：vinhnht
 * Change log   ：2017/02/28：01-00 vinhnht create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * FaqsType
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhnht
 */
@Table(name = "m_faqs_type")
public class FaqsType extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_FAQS_TYPE")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;
    
    @Column(name = "note")
    private String note;

    @Column(name = "description")
    private String description;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "enabled")
    private boolean enabled;
    
    @Column(name = "link_alias")
    private String linkAlias;
    
    @Column(name = "m_customer_type_id")
    private Long customerTypeId;
    
    @Column(name = "before_id")
    private Long beforeId;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "approve_by")
    private String approveBy;
    
    @Column(name = "publish_by")
    private String publishBy;

    /**
     * Get id
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  hand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get code
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  hand
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get name
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  hand
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get url
     * @return String
     * @author hand
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  hand
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get note
     * @return String
     * @author hand
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  hand
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get description
     * @return String
     * @author hand
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  hand
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get sort
     * @return Long
     * @author hand
     */
    public Long getSort() {
        return sort;
    }

    /**
     * Set sort
     * @param   sort
     *          type Long
     * @return
     * @author  hand
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get enabled
     * @return boolean
     * @author hand
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * @param   enabled
     *          type boolean
     * @return
     * @author  hand
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get linkAlias
     * @return String
     * @author TranLTH
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * Set linkAlias
     * @param   linkAlias
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    
    public Long getBeforeId() {
        return beforeId;
    }

    
    public void setBeforeId(Long beforeId) {
        this.beforeId = beforeId;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getPublishBy() {
		return publishBy;
	}

	public void setPublishBy(String postBy) {
		this.publishBy = postBy;
	}
    
}