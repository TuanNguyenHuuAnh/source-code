package vn.com.unit.process.workflow.dto;

public class AppFunctionDto {
    private Long id;
    private String code;
    private String name;
    private Long processId;
    private String permissionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

	/**
	 * @return the permissionType
	 */
	public String getPermissionType() {
		return permissionType;
	}

	/**
	 * @param permissionType the permissionType to set
	 */
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}
}
