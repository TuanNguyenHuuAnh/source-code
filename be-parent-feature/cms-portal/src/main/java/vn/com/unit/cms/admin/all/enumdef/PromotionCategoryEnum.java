package vn.com.unit.cms.admin.all.enumdef;

public enum PromotionCategoryEnum {
	Top(1,"news.promotion.top"),
	Middle(2,"news.promotion.middle"),
	Bottom(3,"news.promotion.bottom")
	;
	
	private int value;
	
	private String code;

	private PromotionCategoryEnum(int value, String code) {
		this.value = value;
		this.code = code;
	}
	
	public int getValue(){
		return value;
	};
	
	public String getCode(){
		return code;
	};
}
