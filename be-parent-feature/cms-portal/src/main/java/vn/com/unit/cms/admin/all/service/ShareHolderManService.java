/*******************************************************************************
 * Class        ：ShareHolderManagementService
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;
//import java.util.Locale;

//import org.springframework.web.multipart.MultipartHttpServletRequest;

import vn.com.unit.cms.admin.all.dto.ShareholderDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
//import vn.com.unit.cms.admin.all.enumdef.ShareHolderProcessEnum;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

public interface ShareHolderManService extends DocumentWorkflowCommonService<ShareholderDto, ShareholderDto> {

//	public PageWrapper<ShareholderDto> getAllActive(int page, int sizeOfPage);
//
//	public PageWrapper<ShareholderDto> getActiveByConditions(int page, int sizeOfPage, CommonSearchDto searchDto);
//
	public ShareholderDto getDetailById(Long id);
//
//	public ShareholderDto saveNew(ShareholderDto updateModel, ShareHolderProcessEnum status);
//
//	public ShareholderDto saveUpdate(ShareholderDto updateModel, ShareHolderProcessEnum status);
//
//	public int countById(Long id);
//
	public void delete(Long id);
//
	public int countByCode(String code);
//
//	public List<SearchKeyDto> genSearchKeyList(Locale locale);
//
//	/**
//	 * @param documentDto
//	 * @return
//	 */
//	public ShareholderDto reject(ShareholderDto documentDto);
//
//	/**
//	 * @param documentDto
//	 * @return
//	 */
//	public ShareholderDto submit(ShareholderDto documentDto);
//
//	/**
//	 * @param documentDto
//	 * @return
//	 */
//	public ShareholderDto approve(ShareholderDto documentDto);
//
//	/**
//	 * @return
//	 */
//	ShareholderDto initShareholderDto();
//
//	/**
//	 * 
//	 * @return
//	 */
	public List<ShareholderDto> getAllActive();
//
//	/**
//	 * 
//	 * @param sortPageModel
//	 */
	public void updateModelsSort(SortPageDto sortPageModel);
//
//	public String importExcel(MultipartHttpServletRequest multipartHttpServletRequest);
}
