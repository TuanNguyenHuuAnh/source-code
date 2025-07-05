/*******************************************************************************
 * Class        ：AbstractApiResult
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：taitt
 * Change log   ：2020/12/31：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AbstractApiResult
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public abstract class AbstractApiResult {
    private int status;
    private String message;
}
