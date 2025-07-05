package vn.com.unit.cms.admin.all.enumdef;

public enum OrgInformationViewTypeEnum {

	FINANCIAL_REPORT("FINANCIAL_REPORT", "org-info.viewtype.financial-report"),
	PUBLIC_INFORMATION("PUBLIC_INFORMATION", "org-info.viewtype.public-information"),
	YEARS_REPORT("YEARS_REPORT", "org-info.viewtype.years-report"),
	FORM("FORM", "org-info.viewtype.form"),
	ONLY_SHAREHOLDERS("ONLY_SHAREHOLDERS", "org-info.viewtype.only-shareholders");

	private String viewTypeName;
	private String selectDisplayMsgKey;

	private OrgInformationViewTypeEnum(String typeName, String displayMsgKey) {
		this.viewTypeName = typeName;
		this.selectDisplayMsgKey = displayMsgKey;
	}

	public String getViewTypeName(){
		return viewTypeName;
	}
	
	public String getSelectMsgKey(){
		return selectDisplayMsgKey;
	}
}