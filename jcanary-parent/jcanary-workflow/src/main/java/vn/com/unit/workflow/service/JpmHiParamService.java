/*******************************************************************************
* Class        JpmHiParamService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import vn.com.unit.workflow.entity.JpmHiParam;
import vn.com.unit.workflow.entity.JpmParam;

/**
 * JpmHiParamService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmHiParamService {

    /**
     * <p>
     * Save jpm hi param.
     * </p>
     *
     * @param jpmParam
     *            type {@link JpmParam}
     * @return {@link JpmHiParam}
     * @author KhuongTH
     */
    JpmHiParam saveJpmHiParam(JpmParam jpmParam);

}