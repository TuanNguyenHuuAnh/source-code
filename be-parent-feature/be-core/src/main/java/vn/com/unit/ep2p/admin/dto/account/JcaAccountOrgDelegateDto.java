package vn.com.unit.ep2p.admin.dto.account;

import java.util.Date;

public class JcaAccountOrgDelegateDto {
    
    private Long orgId;
    
    private String orgName;
    
    private Boolean isCurrent;
    
    private String deletedBy;
    
    private Date createdDate;
    
    private Boolean applied;

    public Long getOrgId() {
        return orgId;
    }
    
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    
    public String getOrgName() {
        return orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Boolean getApplied() {
        return applied;
    }
    
    public void setApplied(Boolean applied) {
        this.applied = applied;
    }

}
