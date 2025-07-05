/*******************************************************************************
 * Class        ：EmulateService
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.core.module.emulate.dto.EmulateEditDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchResultDto;
import vn.com.unit.cms.core.module.emulate.entity.ContestSummary;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

/**
 * FaqsService
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface EmulateService extends DocumentWorkflowCommonService<EmulateEditDto, EmulateEditDto>,
        CmsCommonSearchFillterService<EmulateSearchDto, EmulateSearchResultDto, EmulateEditDto> {

	public void deleteContest(ContestSummary contest);

    public  List<EmulateSearchDto> getType();



}
