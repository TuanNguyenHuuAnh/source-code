package vn.com.unit.ep2p.enumdef;

public enum ReportActiveMonthEnum {
	  NO("0")
	, policykey("1")
	, poname("2")
	, receiveddate("3")
	, polissueeff("4")
	, polagtshrpct("5")
	, fypissue("6")
	, fypcancel("7")
	, sumfyp("8")
	, fyppending("9")
	, fypreject("10");

	
	private String value;
	
	private ReportActiveMonthEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
