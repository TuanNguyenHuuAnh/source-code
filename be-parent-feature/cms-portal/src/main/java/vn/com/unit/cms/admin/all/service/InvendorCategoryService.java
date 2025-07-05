package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.cms.admin.all.dto.InvendorCategoryDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.enumdef.InvendorCategoryProcessEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

public interface InvendorCategoryService
        extends DocumentWorkflowCommonService<InvendorCategoryDto, InvendorCategoryDto> {

	PageWrapper<InvendorCategoryDto> getAllActive(int page, int intConfig);

	List<SearchKeyDto> genSearchKeyList(Locale locale);

	int countByCode(String code);

	InvendorCategoryDto getDetailById(Long id);

	PageWrapper<InvendorCategoryDto> getActiveByConditions(int page, int intConfig, CommonSearchDto searchDto);

	InvendorCategoryDto saveNew(InvendorCategoryDto updateDto, Object object);

	InvendorCategoryDto approve(InvendorCategoryDto invendorCategoryDto);

	InvendorCategoryDto reject(InvendorCategoryDto invendorCategoryDto);

	InvendorCategoryDto submit(InvendorCategoryDto invendorCategoryDto);

	InvendorCategoryDto saveUpdate(InvendorCategoryDto updateModel, InvendorCategoryProcessEnum create);

	InvendorCategoryDto initInvendorCategoryDto();

	void delete(Long id);

	List<InvendorCategoryDto> getAllActive();

	void updateModelsSort(SortPageDto sortPageModel);

}
