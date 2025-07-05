package vn.com.unit.ep2p.enumdef;

public enum EnumExportReportGroupDetailAH {
	 NO("0")
	, BDRH("1")
	, BDOH("2")
	, OFFICE("3")
	, GADCODE("4")
	, GADNAME("5")
	, BMCODE("6")
	, BMNAME("7")
	, UMCODE("8")
	, UMNAME("9")
	, AGENTCODETVTC("10")
	, AGENTNAMETVTC("11")
	, AGENTTYPETVTC("12")
	, K2FEETOBEPAIDEPP("13")
	, K2FEEPAIDAPP("14")
	, K2RATIO("15")
	, K2FEEMISSING7("16")
	, K2FEEMISSING75("17")
	, K2FEEMISSING8("18")
	, K2FEEMISSING85("19")
	, K2FEEMISSING9("20")
	
	, KK2FEETOBEPAIDEPP("21")
	, KK2FEEPAIDAPP("22")
	, KK2RATIO("23")
	, KK2FEEMISSING7("24")
	, KK2FEEMISSING75("25")
	, KK2FEEMISSING8("26")
	, KK2FEEMISSING85("27")
	, KK2FEEMISSING9("28");

	
	private String value;
	
	private EnumExportReportGroupDetailAH(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
