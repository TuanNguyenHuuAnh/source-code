package vn.com.unit.ep2p.enumdef;

public enum AutoterOHEnum {

	officecode("0")
,	bdohfullname("1")
,	manager("2")
,	manage("3")
,	tvtcName("4")
,	policycountissue	("5")
,	policycountservice	("6");

	private String value;
	private AutoterOHEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
