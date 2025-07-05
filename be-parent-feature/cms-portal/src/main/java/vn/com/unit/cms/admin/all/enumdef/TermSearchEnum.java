package vn.com.unit.cms.admin.all.enumdef;

public enum TermSearchEnum {
	CODE("term.code"),
	NAME("term.title"),
    DESC("term.description");//tungns
	
	private String value;

	private TermSearchEnum(String value) {
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
}
