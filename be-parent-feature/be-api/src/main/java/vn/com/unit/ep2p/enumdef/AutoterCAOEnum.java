package vn.com.unit.ep2p.enumdef;

public enum AutoterCAOEnum {
	tdcode("0")
,	bdthfullname("1")
,	ncode("2")
,	bdahfullname("3")
// ,	rcode("4")
// ,	bdrhfullname("5")
, 	officecode("4")
,	bdohfullname("5")
,	manager("6")
,	manage("7")
,	tvtcName("8")
,	policycountissue	("9")
,	policycountservice	("10");




	private String value;
	private AutoterCAOEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
