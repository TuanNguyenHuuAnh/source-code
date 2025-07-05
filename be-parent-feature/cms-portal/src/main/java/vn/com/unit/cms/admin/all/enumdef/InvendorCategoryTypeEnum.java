/*******************************************************************************
 * Class        ：DocumentTypeSearchEnum
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：thuydtn
 * Change log   ：2017/04/18：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * DocumentTypeSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public enum InvendorCategoryTypeEnum {
	
	INVENDOR_PUBLISH_INFO("INVENDOR_PUBLISH_INFO", "invendor.type.invendor.publish-info"),
	INVENDOR_FINANCIAL_REPORT("INVENDOR_FINANCIAL_REPORT", "invendor.type.invendor.financial-report"),
	INVENDOR_YEARS_REPORT("INVENDOR_FINANCIAL_REPORT", "invendor.type.invendor.years-report"),
	INVENDOR_FORM("INVENDOR_FORM", "invendor.type.invendor.form"),
	INVENDOR_SHAREHOLDER("INVENDOR_SHAREHOLDER", "invendor.type.invendor.shareholder"),
	SHAREHOLDER_PUBLISH_INFO("INVENDOR_PUBLISH_INFO", "invendor.type.invendor.publish-info"),
	SHAREHOLDER_FINANCIAL_REPORT("INVENDOR_FINANCIAL_REPORT", "invendor.type.invendor.financial-report"),
	SHAREHOLDER_YEARS_REPORT("INVENDOR_FINANCIAL_REPORT", "invendor.type.invendor.years-report"),
	SHAREHOLDER_FORM("INVENDOR_FORM", "invendor.type.invendor.form");
	

    private String typeName;
    private String typeLabel;

    private InvendorCategoryTypeEnum(String typeName, String typeLabel) {
        this.typeName = typeName;
        this.typeLabel = typeLabel;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

}
