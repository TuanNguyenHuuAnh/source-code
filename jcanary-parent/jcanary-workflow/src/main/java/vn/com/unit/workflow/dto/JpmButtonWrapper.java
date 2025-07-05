/*******************************************************************************
 * Class        ：JpmButtonWrapper
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：tantm
 * Change log   ：2020/11/18：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * JpmButtonWrapper
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class JpmButtonWrapper<T> {

    private boolean saveForm;

    private boolean saveEform;

    private boolean reference;

    private boolean useClaimButton;

    private String stepCode;

    private List<T> data;

    public boolean isEmpty() {
        boolean isEmptyFlag = true;

        if (data != null && !data.isEmpty()) {
            isEmptyFlag = false;
        }

        return isEmptyFlag;
    }

}
