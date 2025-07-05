/**
 * @author TaiTM
 * @date Aug 18, 2020
 */

package vn.com.unit.cms.admin.all.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Acl.Entity;
import com.ibm.db2.jcc.am.em;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.provider.ConnectionProvider;
import lombok.Getter;
import net.sf.jasperreports.engine.export.oasis.CellStyle;
import vn.com.unit.cms.admin.all.dto.EmulateResultParams;
import vn.com.unit.cms.admin.all.dto.FasqCategoryParams;
import vn.com.unit.cms.admin.all.entity.EmulateResultImport;
import vn.com.unit.cms.admin.all.entity.FaqsCategory;
import vn.com.unit.cms.admin.all.entity.FaqsCategoryImport;
import vn.com.unit.cms.admin.all.enumdef.exports.EmulateResultExportEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.EmulateResultListImportRepository;
import vn.com.unit.cms.admin.all.repository.EmulateImportRepository;
import vn.com.unit.cms.admin.all.repository.EmulateLanguageRepository;
import vn.com.unit.cms.admin.all.repository.EmulateResultImportRepository;
import vn.com.unit.cms.admin.all.service.EmulateImportService;
import vn.com.unit.cms.admin.all.service.EmunateResultImportService;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.module.emulate.dto.ContestDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportSearchDto;
import vn.com.unit.cms.core.module.emulate.entity.ContestSummary;
import vn.com.unit.cms.core.module.emulate.entity.Emulate;
import vn.com.unit.cms.core.module.emulate.entity.EmulateLanguage;
import vn.com.unit.cms.core.module.emulate.repository.ContestApplicableDetailRepository;
import vn.com.unit.cms.core.module.emulate.repository.ContestRepository;
import vn.com.unit.cms.core.module.emulate.repository.ContestSummaryRepository;
import vn.com.unit.cms.core.module.emulate.repository.EmulateRepository;
import vn.com.unit.cms.core.module.emulate.repository.EmulateResultRepository;
import vn.com.unit.cms.core.module.faqs.dto.FaqsCategoryImportDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.efo.entity.EfoDoc;
import vn.com.unit.ep2p.core.efo.repository.EfoDocRepository;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.imp.excel.annotation.CoreReadOnlyTx;
import vn.com.unit.imp.excel.annotation.CoreTx;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;
import vn.com.unit.workflow.activiti.entity.JpmProcessInstAct;
import vn.com.unit.workflow.activiti.repository.JpmProcessInstActRepository;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.repository.JpmBusinessRepository;
import vn.com.unit.workflow.repository.JpmProcessDeployRepository;
import vn.com.unit.workflow.repository.JpmStatusCommonRepository;

@SuppressWarnings("rawtypes")
@Service
@Qualifier(value = "emunateResultImportServiceImpl")
@CoreReadOnlyTx
public class EmulateResultImportServiceImpl implements EmunateResultImportService {

	@Autowired
	private ContestSummaryRepository contestSummaryRepository;
	
	@Autowired
	private EmulateImportService contestSummaryService;
	
	
	@Autowired
	private EmulateResultImportRepository emulateResultImportRepository;

	@Autowired
	private EmulateImportRepository emulateImportRepository;

	@Autowired
	private ContestRepository contestRepository;

	/** MessageSource */
	@Autowired
	private MessageSource msg;

	/** SystemConfigure */
	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	@Qualifier("sqlManagerServicePr")
	private SqlManagerServiceImpl sqlManager;

	@Autowired
	private ConnectionProvider connectionProvider;

	@Override
	public int countData(String sessionKey) {
		return emulateResultImportRepository.countData(sessionKey);
	}

	@Override
	public List<EmulateResultImportDto> getListData(int page, String sessionKey, int sizeOfPage) {
		return emulateResultImportRepository.findListData(page, sizeOfPage, sessionKey);
	}

	@Override
	public int countError(String sessionKey) {
		return emulateResultImportRepository.countError(sessionKey);
	}

	@Override
	public List<EmulateResultImportDto> getListDataExport(String sessionKey) {
		return emulateResultImportRepository.findListDataExport(sessionKey);
	}

	@Override
	public List<EmulateResultImportDto> getAllDatas(String sessionKey) {
		return emulateResultImportRepository.findAllDatas(sessionKey);
	}

	@Override
	public Class getImportDto() {
		return EmulateResultImportDto.class;
	}

	@Override
	public Class getEntity() {
		return null;
	}

	@Override
	public ConnectionProvider getConnectionProvider() {
		return connectionProvider;
	}

	@Override
	public MessageSource getMessageSource() {
		return msg;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	@Override
	public boolean validateBusiness(String sessionKey, ImportExcelSearchDto searchDto, Map<String, Object> mapParams,
			List<EmulateResultImportDto> listDataValidate) {

		EmulateResultParams params = new EmulateResultParams();
		params.sessionKey = sessionKey;
		sqlManager.call("SP_IMPORT_EMULATE_VALID", params);
		//updateColumn(sessionKey);
		return countError(sessionKey) > 0;
	}

	public void updateColumn(String sessionKey) {

		List<EmulateImportDto> listData = emulateImportRepository.findListDataImport(sessionKey);

		emulateImportRepository.save(listData);
	}

	@Override
	public List<EmulateResultImportDto> getListDataExportBySearchDto(EmulateResultImportSearchDto searchDto) {
		return emulateResultImportRepository.findListDataExportBySearchDto(searchDto);
	}

	@Override
	public PageWrapper<EmulateResultImportDto> doSearchDetail(int page, EmulateResultImportSearchDto searchDto,
			int pageSize, Locale locale) throws Exception {
		PageWrapper<EmulateResultImportDto> pageWrapper = new PageWrapper<>();
		int sizeOfPage = systemConfig.settingPageSizeList(pageSize, pageWrapper, page);

		int count = emulateResultImportRepository.countDataBySearchDto(searchDto);

		List<EmulateResultImportDto> result = new ArrayList<>();
		if (count > 0) {
			int currentPage = pageWrapper.getCurrentPage();
			int startIndex = (currentPage - 1) * sizeOfPage;
			result = emulateResultImportRepository.findListDataBySearchDto(startIndex, sizeOfPage, searchDto);
			parseData(result, false, locale);
		}
		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	@Override
	public void exportExcel(EmulateResultImportSearchDto searchDto, HttpServletRequest req, HttpServletResponse res,
			Locale locale) throws Exception {
		// TODO
		String templateName = "";

		List<EmulateResultImportDto> lstData = getListDataExportBySearchDto(searchDto);

		if (CollectionUtils.isNotEmpty(lstData)) {
			parseData(lstData, true, locale);
		}

		doExport(templateName, searchDto, lstData, EmulateResultExportEnum.class, res, locale);
	}

	@SuppressWarnings("unchecked")
	private void doExport(String templateName, EmulateResultImportSearchDto searchDto,
			List<EmulateResultImportDto> lstData, Class enumClass, HttpServletResponse res, Locale locale)
			throws Exception {

		String template = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
				+ ".xlsx";

		String datePattern = systemConfig.getConfig("dd/MM/yyyy");

		List<ItemColsExcelDto> cols = new ArrayList<>();

		// start fill data to workbook
		ImportExcelUtil.setListColumnExcel(enumClass, cols);

		ExportExcelUtil exportExcel = new ExportExcelUtil<>();

		Map<String, String> mapColFormat = null;

		Map<String, Object> setMapColDefaultValue = null;

		// do export
		try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(template);) {

			Map<String, CellStyle> mapColStyle = null;

			exportExcel.doExportExcelHeaderWithColFormat(xssfWorkbook, 0, locale, lstData, EmulateResultImportDto.class,
					cols, datePattern, "A5", mapColFormat, mapColStyle, setMapColDefaultValue, null, true, res,
					templateName, true);
		} catch (Exception e) {
			logger.error("#####exportExcelDownline#####: ", e);
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@CoreTx
	public void updateDataBySessionKey(String sessionKey) {
		emulateResultImportRepository.updateDataBySessionKey(sessionKey, UserProfileUtils.getUserNameLogin());
	}

	@Override
	public void deleteDataBySessionKey(EmulateResultImportSearchDto searchDto) throws Exception {
//        SpEditDeleteMovementDto params = new SpEditDeleteMovementDto();
//        params.channel = searchDto.getChannel();
//        params.sessionKey = searchDto.getSessionKey();
//        params.listMovementIdStr = searchDto.getMovementIdList();
//        params.username = UserProfileUtils.getUserNameLogin();
//        if (params.listMovementIdStr != null && !StringUtils.isEmpty(searchDto.getReasonDelete())) {
//            params.reasonDelete = searchDto.getReasonDelete();
//        }
//        sqlManager.call("SP_EDIT_DELETE_MOVEMENT", params);
//
//        if (params.results == -1) {
//            throw new Exception(params.messageError);
//        }
	}

	@Override
	public List<String> initHeaderTemplate() {
		List<String> result = new ArrayList<String>();

		result.add("import.emulate.id");
		result.add("import.emulate.memo");
		result.add("import.emulate.TVTC");
		result.add("import.emulate.mannger");
		result.add("import.emulate.BM");
		result.add("import.emulate.GAD");
		result.add("import.emulate.GA");
		result.add("import.emulate.BDOH");
		result.add("import.emulate.BDRH");
		result.add("import.emulate.BDAH");
		result.add("import.emulate.BDTH");
		result.add("import.emulate.HD");
		result.add("import.emulate.date.receive");
		result.add("import.emulate.date.release");
		result.add("import.emulate.prize");
		result.add("import.emulate.advance");
		result.add("import.emulate.additional");
		result.add("import.emulate.recall");
		result.add("import.emulate.note");
		result.add("template.header.message.error");

		return result;
	}

	@Override
	public SqlManager getSqlManager() {
		return sqlManager;
	}

	@Override
	public void saveListImport(List<EmulateResultImportDto> listDataSave, String sessionKey, Locale locale,
			String username) throws Exception {
		
		
		try {
			long userId = UserProfileUtils.getAccountId();
			List<EmulateResultImportDto> list = emulateResultImportRepository.findAllDatas(sessionKey);

			for (EmulateResultImportDto item : list) {
				List<ContestDto> getCode = emulateResultImportRepository.findByMemoAndCode(item.getMemoNo());
				if (getCode != null) {
					// delete data old
					for(ContestDto ls: getCode) {
						ls.setDeletedDate(new Date());
						ls.setDeletedBy(username);
						contestRepository.save(ls);
					}		
				}
			}
					
			for (EmulateResultImportDto item : list) {
				ContestDto conTest = new ContestDto();
				conTest.setMemoNo(item.getMemoNo());
				conTest.setAgentCode(item.getAgentCode());
				conTest.setCreatedDate(new Date());
				conTest.setCreatedBy(username);

				conTest.setReportingtoCode(item.getReportingtoCode());
				conTest.setBmCode(item.getBmCode());
				conTest.setGadCode(item.getGadCode());
				conTest.setGaCode(item.getGaCode());
				conTest.setBdohCode(item.getBdohCode());
				conTest.setBdrhCode(item.getBdrhCode());
				conTest.setBdahCode(item.getBdahCode());
				conTest.setBdthCde(item.getBdthCde());
				conTest.setPolicyNo(item.getPolicyNo());
				if (item.getAppliedDate() != null) {
					conTest.setAppliedDate(item.getAppliedDate());
				}
				if (item.getIssuedDate() != null) {
					conTest.setIssuedDate(item.getIssuedDate());
				}
				conTest.setResult(item.getResult());
				conTest.setAdvance(item.getAdvance());
				conTest.setBonus(item.getBonus());
				conTest.setClawback(item.getClawback());
				conTest.setNote(item.getNote());
				contestRepository.save(conTest);

				//update field update summary
//				ContestSummary getSummary = contestSummaryRepository.findByMemo(conTest.getMemoNo());
//				getSummary.setUpdatedDate(new Date());
//				getSummary.setUpdatedBy(username);
				
//				contestSummaryService.updateData(conTest.getMemoNo(),username,getSummary);
			}

		} catch (Exception e) {
			logger.error("####saveListImport####", e);
			throw new Exception(e);
		}
	}
}