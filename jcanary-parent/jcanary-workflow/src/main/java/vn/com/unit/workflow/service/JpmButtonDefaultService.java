/*******************************************************************************
* Class        JpmButtonDefaultService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmButtonDto;

/**
 * JpmButtonDefaultService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmButtonDefaultService {
    
    /**
     * <p>
     * Gets the list button default.
     * </p>
     *
     * @return the list button default
     * @author KhuongTH
     */
    List<JpmButtonDto> getListButtonDefault();
}