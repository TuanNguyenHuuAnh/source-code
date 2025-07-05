/*******************************************************************************
 * Class        JpmBusinessDto
 * Created date 2019/04/12
 * Lasted date  2019/04/12
 * Author       KhoaNA
 * Change log   2019/04/12 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * JpmBusinessDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class PPLJpmBusinessDto {

    private Long id;
    
    @Size(min=1,max=255)
    private String code;
    
    @Size(min=1,max=500)
    private String name;
    
    private boolean isActive;
    
    private boolean isAuthority;
    
    @NotNull
    private Long companyId;
    
    private String description;
    
    private Long majorVersion;
    
    private Long minorVersion;
    
    private boolean hasParam;
    
    private String processType;
    

    
    /** Only view */
    private String companyName;
    private boolean isSystem;
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

	public Long getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(Long majorVersion) {
		this.majorVersion = majorVersion;
	}

	public Long getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(Long minorVersion) {
		this.minorVersion = minorVersion;
	}

    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public boolean getHasParam() {
        return hasParam;
    }
    
    public void setHasParam(boolean hasParam) {
        this.hasParam = hasParam;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public boolean getIsAuthority() {
        return isAuthority;
    }

    public void setIsAuthority(boolean isAuthority) {
        this.isAuthority = isAuthority;
    }

}
