package vn.com.unit.ep2p.core.ers.enumdef;

public enum ErsAgSeftInputAttachMailADEnum {

	NO("0"),
	
	CANDIDATENAME("1"),
	
	IDNO("2"),
	
	DOB("3"),
	
	GENDERNAME("4"),
	
	MOBILE("5"),
	
	EMAIL("6"),
	
	CURRENTFULLADDRESS("7"),
	
	ADCODE("8"),
	
	ADNAME("9"),
	
	// 10
	
	ALLOCATIONADDATE("11"),
	

	
	;
	private String value;

	private ErsAgSeftInputAttachMailADEnum(String value) {
		this.value = value;
	}

	@Override 
	public String toString() {
		return value;
	}
}
