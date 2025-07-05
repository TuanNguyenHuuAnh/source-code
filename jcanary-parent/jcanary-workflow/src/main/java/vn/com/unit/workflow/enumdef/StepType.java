/*******************************************************************************
 * Class        ：StepType
 * Created date ：2020/11/24
 * Lasted date  ：2020/11/24
 * Author       ：KhuongTH
 * Change log   ：2020/11/24：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.workflow.enumdef;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * StepType
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@AllArgsConstructor
public enum StepType {
    NORMAL_STEP("NORMAL_STEP"),
    SYSTEM_STEP("SYSTEM_STEP");
    
    private String value;
}
