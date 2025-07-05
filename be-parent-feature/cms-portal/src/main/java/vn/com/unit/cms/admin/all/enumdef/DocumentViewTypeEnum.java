package vn.com.unit.cms.admin.all.enumdef;

public enum DocumentViewTypeEnum {

	FINANCIAL_REPORT("FINANCIAL_REPORT", "document.shareholder-viewtype.financial-report");

	private String viewTypeName;
	private String selectDisplayMsgKey;

	private DocumentViewTypeEnum(String typeName, String displayMsgKey) {
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