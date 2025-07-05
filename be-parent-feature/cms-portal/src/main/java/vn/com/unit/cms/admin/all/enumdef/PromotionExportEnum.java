package vn.com.unit.cms.admin.all.enumdef;

public enum PromotionExportEnum {

	STT("0"), CODE("1"), TITLE("2"), PRODUCTCATEGORYNAME("3"), PRODUCTCATEGORYSUBNAME("4"), PRODUCTNAME("5"),
	STATUSNAME("6"), CREATEBY("7"), CREATEDATE("8");

	private String value;

	private PromotionExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
