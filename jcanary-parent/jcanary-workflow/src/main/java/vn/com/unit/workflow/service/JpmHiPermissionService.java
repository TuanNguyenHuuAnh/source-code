/*******************************************************************************
* Class        JpmHiPermissionService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import vn.com.unit.workflow.entity.JpmHiPermission;
import vn.com.unit.workflow.entity.JpmPermission;

/**
 * JpmHiPermissionService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmHiPermissionService {

    /**
     * <p>
     * Save jpm hi permission.
     * </p>
     *
     * @param jpmPermission
     *            type {@link JpmPermission}
     * @return {@link JpmHiPermission}
     * @author KhuongTH
     */
    JpmHiPermission saveJpmHiPermission(JpmPermission jpmPermission);

}