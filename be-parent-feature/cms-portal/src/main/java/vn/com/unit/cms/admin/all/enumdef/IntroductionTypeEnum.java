package vn.com.unit.cms.admin.all.enumdef;

public enum IntroductionTypeEnum {

	COMMON("COMMON", "introduction.type.common"),
	INVENDOR_PUBLISH_INFO("INVENDOR_PUBLISH_INFO", "introduction.type.invendor.publish-info"),
	SHAREHOLDER_PUBLISH_INFO("SHAREHOLDER_PUBLISH_INFO", "introduction.type.invendor.shareholder.publish-info"),
	INVENDOR_PUBLISH_INFO_INTRO("INVENDOR_PUBLISH_INFO_INTRO", "introduction.type.invendor.publish-info.intro"),
	INVENDOR_FINANCIAL_REPORT_INTRO("INVENDOR_FINANCIAL_REPORT_INTRO", "introduction.type.invendor.financial-report.intro"),
	INVENDOR_YEARS_REPORT_INTRO("INVENDOR_YEARS_REPORT_INTRO", "introduction.type.invendor.years-report.intro"),
	INVENDOR_FORM_INTRO("INVENDOR_FORM_INTRO", "introduction.type.invendor.form.intro"),
	INVENDOR_SHAREHOLDER_INTRO("INVENDOR_SHAREHOLDER_INTRO", "introduction.type.invendor.shareholder.intro"),
	INVENDOR_INTRO("INVENDOR_INTRO", "introduction.type.invendor.intro");

	private String typeName;
	private String selectDisplayMsgKey;

	private IntroductionTypeEnum(String typeName, String displayMsgKey) {
		this.typeName = typeName;
		this.selectDisplayMsgKey = displayMsgKey;
	}

	public String getTypeName(){
		return typeName;
	}
	
	public String getSelectMsgKey(){
		return selectDisplayMsgKey;
	}
}