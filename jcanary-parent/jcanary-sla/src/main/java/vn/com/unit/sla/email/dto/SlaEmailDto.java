/*******************************************************************************
 * Class        ：SlaEmailDto
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.sla.email.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractEmailDto;

/**
 * SlaEmailDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
@Getter
@Setter
public class SlaEmailDto extends AbstractEmailDto {

    private Map<String, String> mapData;
}
