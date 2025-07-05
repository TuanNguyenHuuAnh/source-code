package vn.com.unit.cms.admin.all.enumdef;

public enum CurrencySearchEnum {
	TITLE("currency.title"),
	NAME("currency.name");
	
	private String value;

	private CurrencySearchEnum(String value) {
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
}
