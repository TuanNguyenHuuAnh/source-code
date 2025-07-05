/*******************************************************************************
* Class        JpmHiBusinessService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import vn.com.unit.workflow.entity.JpmBusiness;
import vn.com.unit.workflow.entity.JpmHiBusiness;

/**
 * JpmHiBusinessService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmHiBusinessService {

    /**
     * save JpmHiBusiness with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmBusiness
     *            type {@link JpmBusiness}
     * @return {@link JpmHiBusiness}
     * @author KhuongTH
     */
    JpmHiBusiness saveJpmHiBusiness(JpmBusiness jpmBusiness);
}