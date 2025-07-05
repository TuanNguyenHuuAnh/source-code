package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.AuthorityDetailDto;
import vn.com.unit.ep2p.admin.enumdef.AuthorityReportExportEnum;
import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
import vn.com.unit.ep2p.admin.repository.AuthorityDetailRepository;
import vn.com.unit.ep2p.admin.service.AuthorityDetailService;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.constant.CommonConstant;
import vn.com.unit.ep2p.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.utils.ExportExcelUtil;
import vn.com.unit.ep2p.utils.ImportExcelUtil;
import vn.com.unit.ep2p.utils.MultiSearchBoxUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthorityDetailServiceImpl implements AuthorityDetailService {

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	AuthorityDetailRepository authorityDetailRepository;

	@Autowired
	ConstantDisplayService constantDisplayService;

	@Autowired
	private ServletContext servletContext;

	private String constEnabled;
	private String constDisable;
	private String constActivated;
	private String constInactive;

	static HashMap<String, String[]> mapField;
	static {
		 	mapField = new HashMap<>();
			mapField.put("EMAIL", new String[] {"EMAIL"});
			mapField.put("FULLNAME", new String[] {"FULLNAME"});
			mapField.put("USERNAME", new String[] {"USERNAME"});
			mapField.put("GROUP", new String[] {"GROUPCODE"});
			mapField.put("ROLE", new String[] {"ROLECODE"});
			mapField.put("FUNCTION", new String[] {"FUNCTIONCODE"});
	}
	
	
	@Override
	public PageWrapper<AuthorityDetailDto> search(AuthorityDetailDto authorityDetailDto, int page, int pageSize,
			Locale locale) {
		setConst(locale);
		setSearchParm(authorityDetailDto);
		List<Integer> listPageSize = systemConfig.getListPage(pageSize);
		int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
		PageWrapper<AuthorityDetailDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);

		// Set listPageSize, sizeOfPage
		pageWrapper.setListPageSize(listPageSize);
		pageWrapper.setSizeOfPage(sizeOfPage);

		List<AuthorityDetailDto> lstAuthorityDetail = null;
		int count = 0;
		if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
			count = authorityDetailRepository.countRecordsOracle(authorityDetailDto);
			authorityDetailDto.setCurrentPage(page - 1);
			if (count > 0) {
				if (page > (count / sizeOfPage)) {
					authorityDetailDto.setSizePage(count - ((page - 1) * sizeOfPage));
				} else {
					authorityDetailDto.setSizePage(sizeOfPage);
				}
				lstAuthorityDetail = authorityDetailRepository.searchAuthorityDetailOracle(authorityDetailDto, constEnabled,
						constDisable, constActivated, constInactive);
			}

			

		} else {
			count = authorityDetailRepository.countRecordsSQL(authorityDetailDto);

			authorityDetailDto.setCurrentPage(page - 1);
			if (count > 0) {
				if (page > (count / sizeOfPage)) {
					authorityDetailDto.setSizePage(count - ((page - 1) * sizeOfPage));
				} else {
					authorityDetailDto.setSizePage(sizeOfPage);
				}
				lstAuthorityDetail = authorityDetailRepository.searchAuthorityDetailSQL(authorityDetailDto, constEnabled,
						constDisable, constActivated, constInactive);
			}

			
		}
		pageWrapper.setDataAndCount(lstAuthorityDetail, count);
		return pageWrapper;
	}

	
	public void setSearchParm(AuthorityDetailDto authorityDetailDto) {
		MultiSearchBoxUtils.setConditionSearch(authorityDetailDto, authorityDetailDto.getFieldSearch()
				, authorityDetailDto.getFieldValues(), mapField);
	}
	
//	private void setSearchParm(AuthorityDetailDto authorityDetailDto) {
//		if (authorityDetailDto.getFieldValues() != null && !authorityDetailDto.getFieldValues().isEmpty()) {
//			for (String param : authorityDetailDto.getFieldValues()) {
//				if (param.equals("EMAIL")) {
//					authorityDetailDto.setEmail(authorityDetailDto.getFieldSearch() != null
//							? authorityDetailDto.getFieldSearch().trim() : authorityDetailDto.getFieldSearch());
//				}
//				if (param.equals("FULLNAME")) {
//					authorityDetailDto.setFullname(authorityDetailDto.getFieldSearch() != null
//							? authorityDetailDto.getFieldSearch().trim() : authorityDetailDto.getFieldSearch());
//				}
//				if (param.equals("USERNAME")) {
//					authorityDetailDto.setUsername(authorityDetailDto.getFieldSearch() != null
//							? authorityDetailDto.getFieldSearch().trim() : authorityDetailDto.getFieldSearch());
//				}
//				if (param.equals("GROUP")) {
//					authorityDetailDto.setGroupCode(authorityDetailDto.getFieldSearch() != null
//							? authorityDetailDto.getFieldSearch().trim() : authorityDetailDto.getFieldSearch());
//				}
//				if (param.equals("ROLE")) {
//					authorityDetailDto.setRoleCode(authorityDetailDto.getFieldSearch() != null
//							? authorityDetailDto.getFieldSearch().trim() : authorityDetailDto.getFieldSearch());
//				}
//				if (param.equals("FUNCTION")) {
//					authorityDetailDto.setFunctionCode(authorityDetailDto.getFieldSearch() != null
//							? authorityDetailDto.getFieldSearch().trim() : authorityDetailDto.getFieldSearch());
//				}
//			}
//		} else {
//			if (authorityDetailDto.getFieldSearch() != null && !authorityDetailDto.getFieldSearch().isEmpty()) {
//				authorityDetailDto.setEmail(authorityDetailDto.getFieldSearch().trim());
//				authorityDetailDto.setFullname(authorityDetailDto.getFieldSearch().trim());
//				authorityDetailDto.setUsername(authorityDetailDto.getFieldSearch().trim());
//				authorityDetailDto.setGroupCode(authorityDetailDto.getFieldSearch().trim());
//				authorityDetailDto.setRoleCode(authorityDetailDto.getFieldSearch().trim());
//				authorityDetailDto.setFunctionCode(authorityDetailDto.getFieldSearch().trim());
//			}
//		}
//	}

	@Override
	public void exportExcel(AuthorityDetailDto authorityDetailDto, HttpServletResponse response, Locale locale) {
		setConst(locale);
		try {
			String templateName = CommonConstant.TEMPLATE_EXCEL_AUTHORITY_REPORT;
			String template = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
					+ CommonConstant.TYPE_EXCEL;
			String datePattent = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			// take data
			setSearchParm(authorityDetailDto);
			List<AuthorityDetailDto> lstDatas = null;
			int count = 0;
			authorityDetailDto.setCurrentPage(0);
			if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
				count = authorityDetailRepository.countRecordsOracle(authorityDetailDto);
				authorityDetailDto.setSizePage(count);
				lstDatas = authorityDetailRepository.searchAuthorityDetailOracle(authorityDetailDto, constEnabled,
						constDisable, constActivated, constInactive);
			} else {
				count = authorityDetailRepository.countRecordsSQL(authorityDetailDto);
				authorityDetailDto.setSizePage(count);
				lstDatas = authorityDetailRepository.searchAuthorityDetailSQL(authorityDetailDto, constEnabled,
						constDisable, constActivated, constInactive);
			}
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(AuthorityReportExportEnum.class, cols);
			ExportExcelUtil<AuthorityDetailDto> exportExcel = new ExportExcelUtil<>();
			// do export
			exportExcel.exportExcelWithXSSF(template, locale, lstDatas, AuthorityDetailDto.class, cols, datePattent,
					response, templateName, authorityDetailDto.getPassExport());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setConst(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", locale);
		try {
			constEnabled = bundle.getString("constant.enabled");
		} catch (Exception e) {
			constEnabled = "Enable";
		}
		try {
			constDisable = bundle.getString("constant.disable");
		} catch (Exception e) {
			constDisable = "Disable";
		}
		try {
			constActivated = bundle.getString("constant.activated");
		} catch (Exception e) {
			constActivated = "Activated";
		}
		try {
			constInactive = bundle.getString("constant.inactive");
		} catch (Exception e) {
			constInactive = "Inactive";
		}
	}

	@Override
	public List<AuthorityDetailDto> getAuthorityDetailDtoListByFunctionCode(List<String> functionCodeList, Long companyId) {
		return authorityDetailRepository.findAuthorityDetailDtoListByFunctionCodeAndCompanyId(functionCodeList, companyId);
	}

	@Override
	public List<AuthorityDetailDto> getAuthorityDetailDtoListByUserNameAndRoleIdAndFunctionType(String username, Long roleId, String functionType, Long companyId) {
		return authorityDetailRepository.findAuthorityDetailDtoListByUserNameAndRoleIdAndFunctionType(username, roleId, functionType, companyId);
	}
}
