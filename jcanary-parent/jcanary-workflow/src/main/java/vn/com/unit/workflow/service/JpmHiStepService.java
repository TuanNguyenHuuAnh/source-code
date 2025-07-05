/*******************************************************************************
* Class        JpmHiStepService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import vn.com.unit.workflow.entity.JpmHiStep;
import vn.com.unit.workflow.entity.JpmStep;

/**
 * JpmHiStepService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmHiStepService {

    /**
     * <p>
     * Save jpm hi step.
     * </p>
     *
     * @param jpmStep
     *            type {@link JpmStep}
     * @return {@link JpmHiStep}
     * @author KhuongTH
     */
    JpmHiStep saveJpmHiStep(JpmStep jpmStep);

}