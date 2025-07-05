package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.admin.all.dto.CustomerVipEditDto;
import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageResultDto;
import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageSearchDto;
import vn.com.unit.cms.admin.all.entity.CustomerVip;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

public interface CustomerVipService extends DocumentWorkflowCommonService<CustomerVipEditDto, CustomerVipEditDto>{

	PageWrapper<CustomerVipLanguageResultDto> doSearch(int page, CustomerVipLanguageSearchDto productSearch,
			Locale locale);

	public boolean doEdit(CustomerVipEditDto productEditDto, Locale locale, HttpServletRequest request)
			throws IOException;

	public CustomerVipEditDto getCustomerVip(Long id, Locale locale);
	
	public CustomerVip getCustomerVipById(Long id);
	
	public void deleteData(Long id, String username);

	public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);
	
	public void exportExcel(CustomerVipLanguageSearchDto searchDto, HttpServletResponse res, Locale locale) throws Exception;
}
