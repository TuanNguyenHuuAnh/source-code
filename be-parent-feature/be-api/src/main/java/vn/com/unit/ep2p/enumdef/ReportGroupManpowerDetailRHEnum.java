package vn.com.unit.ep2p.enumdef;

public enum ReportGroupManpowerDetailRHEnum {
	QUANTILYBM	("22")
	,		QUANTILYUM	("23")
	,		QUANTILYPUM	("24")
	,		QUANTILYFC	("25")
	,		QUANTILYSA	("26")
	,		RECRUITMENT	("27")
	,		RECOVERYCODE	("28")
	,		QUANTILYFCACTION	("29")
	,		QUANTILYFCACTIONNEW	("30")
	,		QUANTILYFCACTIONNEWSCHEME	("31")
	,		QUANTILYFCPFC	("32")
	,		LASTYEARRECRUITMENT	("33")
	,		LASTYEARRECOVERYCODE	("34")
	,		LASTYEARQUANTILYACTIONNEW	("35")
	,		LASTYEARQUANTILYACTION	("36");


	private String value;

	private ReportGroupManpowerDetailRHEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
