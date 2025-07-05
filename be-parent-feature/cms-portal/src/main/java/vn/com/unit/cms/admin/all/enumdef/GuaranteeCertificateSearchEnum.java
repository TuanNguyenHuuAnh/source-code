/*******************************************************************************
 * Class        ：GuaranteeCertificateSearchEnum
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：toannt
 * Change log   ：2017/08/24：01-00 toannt create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;
/**
 * 
 * GuaranteeCertificateSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author toannt
 */
public enum GuaranteeCertificateSearchEnum {

    CERTIFICATE_NUMBER("guarantee.certificate.number"),
    CERTIFICATE_TYPE("guarantee.certificate.type"),
    GUARANTEE("guarantee.certificate.guarantee"),
    BENEFICIARY("guarantee.certificate.beneficiary");
    
    /** value*/
	private String value;

	private GuaranteeCertificateSearchEnum(String value) {
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
}
