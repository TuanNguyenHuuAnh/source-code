package vn.com.unit.cms.admin.all.enumdef;

public enum ProductConsultingStatusEnum {

	PRODUCT_CONSULTING__WAITING("WAITING", "product.consulting.infor.waiting"),

	PRODUCT_CONSULTING_STATUS_PROCESSING("PROCESSING", "product.consulting.infor.processing"),

	PRODUCT_CONSULTING__DONE("DONE", "product.consulting.infor.done"),

	PRODUCT_CONSULTING__REJECTED("REJECTED", "product.consulting.infor.rejected");

	private String statusName;
	private String statusAlias;

	private ProductConsultingStatusEnum(String name, String alias) {
	        this.statusName = name;
	        this.statusAlias = alias;
	    }

	public String getStatusName() {
		return statusName;
	}

	public String getStatusAlias() {
		return statusAlias;
	}
}
