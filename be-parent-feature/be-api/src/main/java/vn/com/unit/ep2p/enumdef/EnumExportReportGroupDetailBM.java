package vn.com.unit.ep2p.enumdef;

public enum EnumExportReportGroupDetailBM {
	 NO("0")
	, UMCODE("1")
	, UMNAME("2")
	, AGENTCODETVTC("3")
	, AGENTNAMETVTC("4")
	, AGENTTYPETVTC("5")
	, K2FEETOBEPAIDEPP("6")
	, K2FEEPAIDAPP("7")
	, K2RATIO("8")
	, K2FEEMISSING7("9")
	, K2FEEMISSING75("10")
	, K2FEEMISSING8("11")
	, K2FEEMISSING85("12")
	, K2FEEMISSING9("13")
	
	, KK2FEETOBEPAIDEPP("14")
	, KK2FEEPAIDAPP("15")
	, KK2RATIO("16")
	, KK2FEEMISSING7("17")
	, KK2FEEMISSING75("18")
	, KK2FEEMISSING8("19")
	, KK2FEEMISSING85("20")
	, KK2FEEMISSING9("21");

	
	private String value;
	
	private EnumExportReportGroupDetailBM(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
