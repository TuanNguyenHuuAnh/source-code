package vn.com.unit.ep2p.enumdef;

public enum AutoterBMEnum {

	manager("0")
,	manage("1")
,	tvtcName("2")
,	policycountissue	("3")
,	policycountservice	("4");

	private String value;
	private AutoterBMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
