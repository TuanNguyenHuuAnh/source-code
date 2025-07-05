/*******************************************************************************
 * Class        ：GuaranteeCertificateDuarationTypeEnum
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：hoangnp
 * Change log   ：2017/08/24：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * GuaranteeCertificateDuarationTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */

public enum GuaranteeCertificateDuarationTypeEnum {

    /** month*/
	MONTH("guarantee.certificate.month", "guarantee.certificate.month"),

    /** year */
    YEAR("guarantee.certificate.year", "guarantee.certificate.year");
	
    /** value*/
	private String value;
	
	/** name*/
	private String name;
	
	/**
	 * @param value
	/** @param name
	 * @author hoangnp
	 */
	private GuaranteeCertificateDuarationTypeEnum(String value, String name) {
	      this.value = value;
	      this.name = name;
	}

	/**
	 * getValue
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getValue() {
	        return value;
	}

	/**
	 * getName
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getName() {
	        return name;
	}
}
