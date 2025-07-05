package vn.com.unit.ep2p.enumdef;

public enum ReportGroupPremiumDetailRHEnum {
	BDOH("0")
	,		OFFICE("1")
	,		SUBMITTOTALFILE("2")
	,		SUBMITTOTALFYP("3")
	,		RELEASETOTALACTIVE("4")
	,		RELEASETOTALFYP("5")
	, 		RELEASENETTOTALACTIVE("6")
	,		RELEASETNETTOTALFYP("7")
	,		CANCELTOTALFILE("8")
	,		CANCELFYP("9")
	,		FYPEDIT("10")
	,	 	FYP ("11")
	,		RYP	("12")
	,		K2	("13")
	,		K2PLUS("14")
	,		LASTYEARSUBMITTOTALFILE	("15")
	,		LASTYEARSUBMITTOTALFYP	("16")
	,		LASTYEARRELEASETOTALFILE("17")
	,		LASTYEARRELEASETOTALFYP	("18")
	,		LASTYEARACTIONTOTALFILE	("19")
	,		LASTYEARACTIONTOTALFYP	("20")
	,		LASTYEARTOTALRYP("21");


	private String value;

	private ReportGroupPremiumDetailRHEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
