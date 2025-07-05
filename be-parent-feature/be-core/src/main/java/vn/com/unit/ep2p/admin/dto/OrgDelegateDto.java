package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

public class OrgDelegateDto {
    
    private Long accountId;
    
    private Long performerId;
    
    private Long orgId;
    
    private String orgName;
    
    private Date createdDate;
    
    public Long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
    public Long getPerformerId() {
        return performerId;
    }
    
    public void setPerformerId(Long performerId) {
        this.performerId = performerId;
    }
    
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
    
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
}
