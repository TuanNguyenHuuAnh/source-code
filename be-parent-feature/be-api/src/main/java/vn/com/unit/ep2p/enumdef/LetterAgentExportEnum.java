package vn.com.unit.ep2p.enumdef;

public enum LetterAgentExportEnum {
	NO("0")
	, LETTERCATEGORYNAME("1")
	, CREATEDDATE("2")
	, OLDAGENTTYPE("3")
	, NEWAGENTTYPE("4");

	private String value;
	
	private LetterAgentExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
