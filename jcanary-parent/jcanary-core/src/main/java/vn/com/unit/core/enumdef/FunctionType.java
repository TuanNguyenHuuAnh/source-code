/*******************************************************************************
 * Class        ：FunctionType
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：KhuongTH
 * Change log   ：2021/01/15：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> FunctionType </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@AllArgsConstructor
public enum FunctionType {
    TYPE1("1"),
    TYPE2("2"),
    PROCESS("3");
    
    private String value;
}
