/*******************************************************************************
 * Class        ：ParamPagingDto
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：taitt
 * Change log   ：2020/12/09：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.repository.param;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.PageAbleDto;

/**
 * ParamPagingDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class ParamPagingDto extends PageAbleDto{

    private boolean paging;
}
