package vn.com.unit.cms.admin.all.enumdef;

public enum HomepageSearchEnum {
	STATUS("term.title"),
    DESC("term.description");
	
	private String value;

	private HomepageSearchEnum(String value) {
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
}
