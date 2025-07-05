/*******************************************************************************
 * Class        ：JpmStatusCommonService
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：KhuongTH
 * Change log   ：2021/03/04：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.entity.JpmStatusCommon;

/**
 * <p>
 * JpmStatusCommonService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface JpmStatusCommonService extends DbRepositoryService<JpmStatusCommon, Long> {

    List<Select2Dto> getStatusCommonSelect2Dtos(String lang);

    /**
     * <p>
     * Gets the status common id converter.
     * </p>
     *
     * @return the status common id converter
     * @author KhuongTH
     */
    Map<String, Long> getStatusCommonIdConverter();
    
    public JpmStatusCommonDto getStatusCommon(String statusCode, String lang);
}
