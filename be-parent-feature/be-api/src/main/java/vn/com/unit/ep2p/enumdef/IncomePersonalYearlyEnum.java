package vn.com.unit.ep2p.enumdef;

public enum IncomePersonalYearlyEnum {
	AGENTCODE("0")
	, AGENTNAME("1")
	, YEAR("2")
	, GROSS("3")
	, TAX("4")
	, DEDUCT("5")
	, REALINCOME("6")
	, LASTYEARBALANCE("7")
	, TOTALGENERATED("8")
	, TOTALTAXPAID("9")
	, TOTALRECOVERIESYEAR("10")
	, YEARENDBALANCE("11");
	
	private String value;
	
	private IncomePersonalYearlyEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
