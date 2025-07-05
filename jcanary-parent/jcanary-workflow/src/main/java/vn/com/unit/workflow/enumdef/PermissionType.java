/*******************************************************************************
 * Class        ：PermissionType
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：KhuongTH
 * Change log   ：2021/01/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.enumdef;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * PermissionType
 * </p>
 * .`
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@AllArgsConstructor
public enum PermissionType {
    
    /** The user. */
    USER(1),
    
    /** The group. */
    GROUP(2);
	
    /** The value. */
    private int value;
}