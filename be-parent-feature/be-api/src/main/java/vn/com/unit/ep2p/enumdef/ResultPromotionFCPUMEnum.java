package vn.com.unit.ep2p.enumdef;

public enum ResultPromotionFCPUMEnum {
	  BDTH("0")
	, BDAH("1")
	, BDOH("2")
	, AGENT("3")
	, OFFICE("4")
	, WORKINGDAY("5")
	, DATEREVIEWPROMOORDEMO("6")
	, MONTHSOFSERVICETOTERM("7")
	, K2PLUSSTR("8")
	, FYP3MONTH("9")
	, afc3month("10")
	, RECRUITMENT3MONTH("11")
	, EVALUATEPROMOTION("12")
	, K2PLUSADDSTR("13")
	, FYP3MONTHADD("14")
	, afc3monthAdd("15")
	, RECRUITMENT3MONTHADD("16");

	private String value;
	
	private ResultPromotionFCPUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
