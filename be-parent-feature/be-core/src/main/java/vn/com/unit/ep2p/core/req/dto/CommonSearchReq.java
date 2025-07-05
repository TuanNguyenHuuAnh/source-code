/*******************************************************************************
 * Class        ：CommonSearchReq
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：KhuongTH
 * Change log   ：2020/12/08：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * CommonSearchReq
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class CommonSearchReq<T> extends PagingReq {

    @ApiModelProperty(notes = "Search by name, code", example = "theanh", required = true, position = 0)
    private String keySearch;
    
    @ApiModelProperty(notes = "Search by condition", example = "", required = true, position = 0)
    private T conditionsSearch;
}
