package vn.com.unit.cms.admin.all.enumdef;

public enum IntroductionCategoryStatusEnum {

	SAVEDRAFF("master.status.savedraff"),

	SUBMIT("master.status.submit"),

	APPROVE("master.status.approve"),

	REJECT("master.status.reject");

	private String value;

	private IntroductionCategoryStatusEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
