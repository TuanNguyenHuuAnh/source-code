package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

public class JobFormApllySearchDto {

	private Long id;
	
	private String name;
	
	private String telephone;
	
	private String email;
	
	private String idNumber;
	
	private String title;
	
	private String content;
	
	private String status;
	
	private String reason;
	
	private Date createDate;
	
	private String position;
	
	private List<String> fieldValues;
	
	private String fieldSearch;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<String> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public String getFieldSearch() {
		return fieldSearch;
	}

	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

    /**
     * Get position
     * @return String
     * @author TranLTH
     */
    public String getPosition() {
        return position;
    }

    /**
     * Set position
     * @param   position
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPosition(String position) {
        this.position = position;
    }
}