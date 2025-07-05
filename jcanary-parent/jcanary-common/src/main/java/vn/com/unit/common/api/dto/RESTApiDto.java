/*******************************************************************************
 * Class        ：RESTApiDto
 * Created date ：2020/11/20
 * Lasted date  ：2020/11/20
 * Author       ：taitt
 * Change log   ：2020/11/20：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.api.dto;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.api.dto.AbstractApiExternalDto;

/**
 * RESTApiDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class RESTApiDto extends AbstractApiExternalDto{
    protected HttpMethod httpMethod;
    protected HttpHeaders headers;
}
