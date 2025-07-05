/*******************************************************************************
 * Class        ：ErrorMessage
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ErrorMessage
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

    private String errorCode;
    private String errorDesc;
}
