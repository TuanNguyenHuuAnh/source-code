package vn.com.unit.cms.admin.all.enumdef;

public enum ProductConsultingCommentEnum {
	
	 	WAITING("product.consulting.infor.waiting", "product.consulting.infor.waiting"),

	    PROCESSING("product.consulting.infor.processing", "product.consulting.infor.processing"),

	    DONE("product.consulting.infor.done", "product.consulting.infor.done"),
		
		ORTHERS(null, "contact.booking.comment.orthers");

	    private String commentValue;
	    private String commentTitle;

	    private ProductConsultingCommentEnum(String value, String title) {
	        this.commentValue = value;
	        this.commentTitle = title;
	    }

		public String getCommentValue() {
			return commentValue;
		}

		public String getCommentTitle() {
			return commentTitle;
		}
}
