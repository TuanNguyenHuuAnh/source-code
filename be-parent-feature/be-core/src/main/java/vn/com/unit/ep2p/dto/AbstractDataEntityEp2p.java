package vn.com.unit.ep2p.dto;

import jp.sf.amateras.mirage.annotation.Column;

public abstract class AbstractDataEntityEp2p {

    @Column(name = "COMPANY_CODE")
    private String companyCode;
    
    @Column(name = "DELETE_FLAG")
    private int deleteFlag = 0;
    
    
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
