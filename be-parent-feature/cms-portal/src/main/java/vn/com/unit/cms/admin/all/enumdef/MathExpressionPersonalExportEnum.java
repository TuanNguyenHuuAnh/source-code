package vn.com.unit.cms.admin.all.enumdef;

public enum MathExpressionPersonalExportEnum {

	STT("0"), CODE("1"), EXPRESSIONTYPE("2"), NAME("3"), ISHIGHLIGHTSSTRING("4"), DESCRIPTION("5"), MAXINTERESTRATE("6"), TERMVALUE("7"),
	MAXLOANAMOUNT("8"), STATUSNAME("9"), CREATEBY("10"), CREATEDATE("11");

	private String value;

	private MathExpressionPersonalExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

}
