/*******************************************************************************
* Class        JpmHiButtonService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import vn.com.unit.workflow.entity.JpmButton;
import vn.com.unit.workflow.entity.JpmHiButton;

/**
 * JpmHiButtonService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmHiButtonService {
    
    /**
     * <p>
     * Save jpm hi button.
     * </p>
     *
     * @param jpmButton
     *            type {@link JpmButton}
     * @return {@link JpmHiButton}
     * @author KhuongTH
     */
    JpmHiButton saveJpmHiButton(JpmButton jpmButton);
}