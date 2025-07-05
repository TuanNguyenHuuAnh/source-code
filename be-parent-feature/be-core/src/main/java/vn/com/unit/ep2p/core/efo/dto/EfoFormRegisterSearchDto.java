/*******************************************************************************
 * Class        ：EfoFormRegisterSearchDto
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：taitt
 * Change log   ：2020/12/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * EfoFormRegisterSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class EfoFormRegisterSearchDto {
    private Long companyId;
    private Long categoryId;
    private String companyName;
}
