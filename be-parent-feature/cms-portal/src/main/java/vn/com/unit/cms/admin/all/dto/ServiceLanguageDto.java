
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
 * TermLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns
 */
public class ServiceLanguageDto {
	
	private Long id;    
	private String name;
    private String mLanguageCode;
    private String title;
    private String code;
    private Long sortOrder;
    private String descriptionAbv;
    private String descriptionSlogan;
    private String note;
    private Date createDate;
    private Long mServiceId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getmLanguageCode() {
		return mLanguageCode;
	}
	public void setmLanguageCode(String mLanguageCode) {
		this.mLanguageCode = mLanguageCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescriptionAbv() {
		return descriptionAbv;
	}
	public void setDescriptionAbv(String description) {
		this.descriptionAbv = description;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getmServiceId() {
		return mServiceId;
	}
	public void setmServiceId(Long mServiceId) {
		this.mServiceId = mServiceId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getName() {
		return name;
	}
	public void setName(String nane) {
		this.name = nane;
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
