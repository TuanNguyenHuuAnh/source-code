/*******************************************************************************
* Class        JpmButtonStepDefaultService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import vn.com.unit.workflow.dto.JpmButtonStepDefaultDto;
import vn.com.unit.workflow.entity.JpmButtonStepDefault;

/**
 * JpmButtonStepDefaultService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmButtonStepDefaultService {

    /**
     * get JpmButtonStepDefaultDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmButtonStepDefaultDto}
     * @author KhuongTH
     */
    JpmButtonStepDefaultDto getJpmButtonStepDefaultDtoById(Long id);

    /**
     * check flag DELETED_ID by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmButtonStepDefault with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmButtonStepDefault
     *            type {@link JpmButtonStepDefault}
     * @return {@link JpmButtonStepDefault}
     * @author KhuongTH
     */
    JpmButtonStepDefault saveJpmButtonStepDefault(JpmButtonStepDefault jpmButtonStepDefault);

    /**
     * save JpmButtonStepDefaultDto
     * 
     * @param jpmButtonStepDefaultDto
     *            type {@link JpmButtonStepDefaultDto}
     * @return {@link JpmButtonStepDefault}
     * @author KhuongTH
     */
    JpmButtonStepDefault saveJpmButtonStepDefaultDto(JpmButtonStepDefaultDto jpmButtonStepDefaultDto);

}