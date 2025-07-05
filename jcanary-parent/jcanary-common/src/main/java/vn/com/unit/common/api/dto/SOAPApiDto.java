/*******************************************************************************
 * Class        ：SOAPApiDto
 * Created date ：2020/11/20
 * Lasted date  ：2020/11/20
 * Author       ：taitt
 * Change log   ：2020/11/20：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.api.dto;

import javax.xml.soap.SOAPMessage;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.api.dto.AbstractApiExternalDto;

/**
 * SOAPApiDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */

@Getter
@Setter
public class SOAPApiDto extends AbstractApiExternalDto{
    
    private SOAPMessage soapMessage;
    
}
