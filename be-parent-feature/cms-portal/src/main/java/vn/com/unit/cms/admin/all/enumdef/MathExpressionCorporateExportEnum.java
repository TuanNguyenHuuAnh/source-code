package vn.com.unit.cms.admin.all.enumdef;

public enum MathExpressionCorporateExportEnum {

	STT("0"), CODE("1"), 
	EXPRESSIONTYPE("2"), 
	NAME("3"), 
	ISHIGHLIGHTSSTRING("4"), 
	DESCRIPTION("5"), 
	STATUSNAME("6"), 
	CREATEBY("7"), 
	CREATEDATE("8");

	private String value;

	private MathExpressionCorporateExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
