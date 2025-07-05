package vn.com.unit.ep2p.core.ers.enumdef;

public enum ErsAgSeftInputExportEnum {
	
	NO("0"),
	
	STATUSFORM("1"),
	
	APPLYFORPOSITION("2"),
	
	CANDIDATENAME("3"),
	
	CURRENTPROVINCENAME("4"),
	
	ADCODE("5"),
	
	ADNAME("6"),
	
	ALLOCATIONADDATE("7"),
	
	RECRUITERCODE("8"),
	
	RECRUITERNAME("9"),
	
	ALLOCATIONRECRUITERDATE("10"),
	
	DOB("11"),
	
	GENDERNAME("12"),

	IDNO("13"),
	
	MOBILE("14"),
	
	EMAIL("15"),

	CREATEDDATE("16"),
	;
	private String value;

	private ErsAgSeftInputExportEnum(String value) {
		this.value = value;
	}

	@Override 
	public String toString() {
		return value;
	}
}
