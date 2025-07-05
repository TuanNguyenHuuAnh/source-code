/*******************************************************************************
 * Class        ：SOAPApiService
 * Created date ：2020/11/20
 * Lasted date  ：2020/11/20
 * Author       ：taitt
 * Change log   ：2020/11/20：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.api.service;

import javax.xml.soap.SOAPElement;
import javax.xml.transform.TransformerException;

import vn.com.unit.dts.api.service.AbstractApiExternalService;

/**
 * SOAPApiService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface SOAPApiService extends AbstractApiExternalService{
    
    public String printSOAPElement(SOAPElement soapElement) throws TransformerException;
    
}
