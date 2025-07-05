package vn.com.unit.ep2p.enumdef;

public enum EnumExportReportGroupDetailTH {
	 NO("0")
	, BDAH("1")
	, REGION("2")
	, BDOH("3")
	, OFFICE("4")
	, GADCODE("5")
	, GADNAME("6")
	, BMCODE("7")
	, BMNAME("8")
	, UMCODE("9")
	, UMNAME("10")
	, AGENTCODETVTC("11")
	, AGENTNAMETVTC("12")
	, AGENTTYPETVTC("13")
	, K2FEETOBEPAIDEPP("14")
	, K2FEEPAIDAPP("15")
	, K2RATIO("16")
	, K2FEEMISSING7("17")
	, K2FEEMISSING75("18")
	, K2FEEMISSING8("19")
	, K2FEEMISSING85("20")
	, K2FEEMISSING9("21")
	
	, KK2FEETOBEPAIDEPP("22")
	, KK2FEEPAIDAPP("23")
	, KK2RATIO("24")
	, KK2FEEMISSING7("25")
	, KK2FEEMISSING75("26")
	, KK2FEEMISSING8("27")
	, KK2FEEMISSING85("28")
	, KK2FEEMISSING9("29");

	
	private String value;
	
	private EnumExportReportGroupDetailTH(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
