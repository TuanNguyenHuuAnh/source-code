/*******************************************************************************
 * Class        ：EmulateResultService
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import vn.com.unit.cms.core.module.emulate.dto.EmulateResultEditDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;

/**
 * FaqsService
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface EmulateResultService extends
        CmsCommonSearchFillterService<EmulateResultSearchDto, EmulateResultSearchResultDto, EmulateResultEditDto> {

}
