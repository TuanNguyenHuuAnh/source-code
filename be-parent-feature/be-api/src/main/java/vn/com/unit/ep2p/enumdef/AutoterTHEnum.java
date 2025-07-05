package vn.com.unit.ep2p.enumdef;

public enum AutoterTHEnum {
	ncode("0")
,	bdahfullname("1")
, 	officecode("2")
,	bdohfullname("3")
,	manager("4")
,	manage("5")
,	tvtcName("6")
,	policycountissue	("7")
,	policycountservice	("8");




	private String value;
	private AutoterTHEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
