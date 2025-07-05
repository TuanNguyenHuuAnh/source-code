package vn.com.unit.ep2p.core.ers.enumdef;

public enum CopListExportEnum {
	
	NO("0"),
	STUDYDATE("1"),
	CANDIDATENAME("2"),
	IDNO("3"),
	MOBILE("4"),
	RECRUITERCODEID("5"),
	RECRUITERNAME("6"),
	MANAGERCODEID("7"),
	MANAGERNAME("8"),
	ADNAME("9"),
	RDNAME("10"),
	REGIONNAME("11"),
	CREATEDBY("12"),
	CREATEDDATE("13");
	
	private String value;

    private CopListExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
