package vn.com.unit.ep2p.enumdef;

public enum ReportGroupManpowerDetailUMEnum {

	CHILD("0")
,	COUNTACTIVE("1")
,	COUNTSCHEMEFC("2")
,	COUNTPFCFC("3");
	private String value;

	private ReportGroupManpowerDetailUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
