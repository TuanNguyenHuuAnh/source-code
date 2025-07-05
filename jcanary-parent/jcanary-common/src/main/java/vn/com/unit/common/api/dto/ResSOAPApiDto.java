/*******************************************************************************
 * Class        ：ResSOAPApiDto
 * Created date ：2020/11/23
 * Lasted date  ：2020/11/23
 * Author       ：taitt
 * Change log   ：2020/11/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.api.dto;

import javax.xml.soap.SOAPMessage;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.api.dto.AbstractResApiExternalDto;

/**
 * SOAPResApiDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class ResSOAPApiDto extends AbstractResApiExternalDto{

    private SOAPMessage soapResponse;
    private Object unmarshalObj;
    private Object fault;
}
