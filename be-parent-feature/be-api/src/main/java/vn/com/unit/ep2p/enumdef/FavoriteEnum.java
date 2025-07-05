package vn.com.unit.ep2p.enumdef;

public enum FavoriteEnum {
	NO("0")
	, TYPE("1")
	, TITLE("2")
	, LINK("3")
	, NAMED("4");
	
	private String value;
	
	private FavoriteEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
