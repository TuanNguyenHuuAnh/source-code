package vn.com.unit.ep2p.enumdef;

public enum AutoterUMEnum {
	manage("0")
	,tvtcName ("1")
,	policycountissue	("2")
,	policycountservice	("3");

	private String value;
	private AutoterUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
