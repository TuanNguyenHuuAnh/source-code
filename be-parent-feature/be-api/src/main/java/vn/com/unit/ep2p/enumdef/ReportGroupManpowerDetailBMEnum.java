package vn.com.unit.ep2p.enumdef;

public enum ReportGroupManpowerDetailBMEnum {

	PAREN("0")
,	CHILD("1")
,	COUNTNEWRECRUITMENT("2")
,	COUNTREINSTATE("3")
,	countActive("4")
,	COUNTNEWRECRUITMENTACTIVE("5")
, 	COUNTPFCFC("6");

	private String value;

	private ReportGroupManpowerDetailBMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
