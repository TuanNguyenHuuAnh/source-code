package vn.com.unit.ep2p.enumdef;

public enum EnumExportReportListEvents {

		No("0")
,		eventCode("1")
,		groupEventName("2")
,		activityEventName("3")
,		eventTitle("4")
,		linkNotify("5")
,		eventDate("6")
,		endDate("7")
,		eventLocation("8")
,		note("9")
,		status("10")
,		quantity("11")
,		attendanceQuantity("12")
,		attendanceRatio("13")
,		createDate("14")
,		updateDate("15")
;

	
	private String value;
	
	private EnumExportReportListEvents(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
