package vn.com.unit.cms.admin.all.enumdef;

public enum ExportInvestorCategoryExportEnum {

	STT("0"),
	INVESTORCATEGORYNAMELEVEL1("1"),
	INVESTORCATEGORYNAMELEVEL2("2"),
	INVESTORCATEGORYNAMELEVEL3("3"),
	CODE("4"),
	TITLE("5"),
	CATEGORYTYPENAME("6"),
	LOCATIONLEFT("7"),
	LOCATIONRIGHTTOP("8"),
	LOCATIONRIGHTBOTTOM("9"),
	STATUSNAME("10"),
	CREATEBY("11"),
	CREATEDATE("12");
	
	private String value;

	private ExportInvestorCategoryExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
	
}
