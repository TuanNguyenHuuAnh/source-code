package vn.com.unit.ep2p.enumdef;

public enum EnumExportReportListGuests {

		No("0")
,		idNumber("1")
,		agentCode("2")
,		name("3")
,		position("4")
,		guestType("5")
,		attendanceTime("6")
,		office("7")
,		GAD("8")
,		BDOH("9")
,		territorry("10")
,		BDRH("11")
,		area("12")
,		BDAH("13")
,		region("14")
,		BDTH("15")
;

	
	private String value;
	
	private EnumExportReportListGuests(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
