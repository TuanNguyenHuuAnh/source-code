package vn.com.unit.cms.admin.all.dto;

public class JobFormApplyEditDto {
	
	private Long id;
	
	private String name;
	
	private String telephone;
	
	private String email;
	
	private String idNumber;
	
	private String title;
	
	private String content;
	
	private String status;
	
	private String reason;
	
	private String physicalFileName;
	
	private String position;

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

    /**
     * Get physicalFileName
     * @return String
     * @author TranLTH
     */
    public String getPhysicalFileName() {
        return physicalFileName;
    }

    /**
     * Set physicalFileName
     * @param   physicalFileName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPhysicalFileName(String physicalFileName) {
        this.physicalFileName = physicalFileName;
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