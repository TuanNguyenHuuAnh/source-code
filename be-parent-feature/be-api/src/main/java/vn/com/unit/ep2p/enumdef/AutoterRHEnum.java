package vn.com.unit.ep2p.enumdef;

public enum AutoterRHEnum {

	officecode("0")
,	bdohfullname("1")
,	lv1agentcode("2")
,	lv1agentname("3")
,	lv2agentcode("4")
,	lv2agentname ("5")
,	lv3agentcode	("6")
,	lv3agentname("7")
,	lv3agenttype	("8")
,	policycountissue	("9")
,	policycountservice	("10");

	private String value;
	private AutoterRHEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
