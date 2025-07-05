/*******************************************************************************
 * Class        ：AbstractApiExternalDto
 * Created date ：2020/11/20
 * Lasted date  ：2020/11/20
 * Author       ：taitt
 * Change log   ：2020/11/20：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.api.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.api.service.AbstractApiExternalService;

/**
 * AbstractApiExternalDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AbstractApiExternalDto {

    protected AbstractApiExternalService abstractApiExternalService;
    protected Integer timeoutSeconds;
    
}
