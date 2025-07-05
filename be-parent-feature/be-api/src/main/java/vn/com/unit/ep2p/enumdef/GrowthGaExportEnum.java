package vn.com.unit.ep2p.enumdef;

public enum GrowthGaExportEnum {
	OFFICE("0"),
	DATA("1"),
	QUANTITYBM("2"),
	QUANTITYUMORBM("3"),
	QUANTITYAGENT("4"),
	QUANTITYRECRUITNEW("5"),
	QUANTITYACTIVE("6"),
	QUANTITYSUBMITDOC("7"),
	QUANTITYSUBMITFYP("8"),
	QUANTITYDOCRELEASE("9"),
	QUANTITYFYPRELEASE("10"),
	QUANTITYRYP("11"),
	RATIOK2("12"),
	RATIOK2PLUS("13");

	private String value;

	private GrowthGaExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
