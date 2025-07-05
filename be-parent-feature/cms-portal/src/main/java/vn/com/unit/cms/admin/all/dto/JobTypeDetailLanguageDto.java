package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

public class JobTypeDetailLanguageDto {
	
	private Long id;
	
	private String mLanguageCode;
	
	private Long mTypeDetail;
	
	private String jtdTitle;
	
	@Size(max=65535)
	private String jtdDescription;

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

	public Long getmTypeDetailId() {
		return mTypeDetail;
	}

	public void setmTypeDetail(Long mTypeDetail) {
		this.mTypeDetail = mTypeDetail;
	}

	public String getJtdTitle() {
		return jtdTitle;
	}

	public void setJtdTitle(String jtdTitle) {
		this.jtdTitle = jtdTitle;
	}

	public String getJtdDescription() {
		return jtdDescription;
	}

	public void setJtdDescription(String jtdDescription) {
		this.jtdDescription = jtdDescription;
	}
	
	
}
