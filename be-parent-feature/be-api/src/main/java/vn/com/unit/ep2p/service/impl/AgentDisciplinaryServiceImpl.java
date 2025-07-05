package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletContext;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDetailDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDetailParam;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDetailSearchDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryPagingParam;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinarySearchDto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.enumdef.AgentDCAhEnum;
import vn.com.unit.ep2p.enumdef.AgentDCBmEnum;
import vn.com.unit.ep2p.enumdef.AgentDCCaoEnum;
import vn.com.unit.ep2p.enumdef.AgentDCRhEnum;
import vn.com.unit.ep2p.enumdef.AgentDCThEnum;
import vn.com.unit.ep2p.enumdef.AgentDCUmEnum;
import vn.com.unit.ep2p.service.AgentDisciplinaryService;
import vn.com.unit.ep2p.service.AgentSAService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class AgentDisciplinaryServiceImpl implements AgentDisciplinaryService{
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private SystemConfig systemConfig;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	AgentSAService agentSAService;

	@Autowired
	private Db2ApiService db2ApiService;
	
	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;
	
	private static final String DS_SP_GET_AGENT_DISCIPLINE = "RPT_ODS.DS_SP_GET_AGENT_DISCIPLINE";
	private static final String DS_SP_GET_LIST_AGENTS_DISCIPLINED = "RPT_ODS.DS_SP_GET_LIST_AGENTS_DISCIPLINED";
	
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	public ObjectDataRes<AgentDisciplinaryDto> getListAgentDisciplinaryStore(AgentDisciplinarySearchDto dto) {
		List<AgentDisciplinaryDto> data = new ArrayList<>();
		int total = 0;
		try {
			AgentDisciplinaryPagingParam param = new AgentDisciplinaryPagingParam();
			dto.setFunctionCode("AGENT_DISCIPLINED");
	    	ObjectMapper mapper = new ObjectMapper();
	    	String stringJsonParam ="";
	    	try {
				stringJsonParam = mapper.writeValueAsString(dto);
			} catch (JsonProcessingException e) {
				logger.error("JsonProcessingException: ", e);
			}
			CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "AGENT_DISCIPLINED");
			param.agentCode = dto.getAgentCode();
			param.agentGroup = dto.getAgentGroup();
			param.page=common.getPage();
			param.pageSize=common.getPageSize();
			param.sort = common.getSort();
			param.search = this.getSearchAgent(dto, common.getSearch());
			sqlManagerDb2Service.call(DS_SP_GET_LIST_AGENTS_DISCIPLINED, param);
			if(CommonCollectionUtil.isNotEmpty(param.data)) {
				data=param.data;
				total = param.totalRows;
			}
		} catch (Exception e) {
			logger.error("getListAgentSAByConditionStore: ", e);
		}
		ObjectDataRes<AgentDisciplinaryDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}
	
	@Override
	public ObjectDataRes<AgentDisciplinaryDto> getListAgentDisciplinaryByCondition( AgentDisciplinarySearchDto searchDto) {
		ObjectDataRes<AgentDisciplinaryDto> resObj = getListAgentDisciplinaryStore(searchDto);
		return resObj;
	}

	@Override
	public AgentDisciplinaryDetailDto getAgentDisciplinaryDettailByCondition(AgentDisciplinaryDetailSearchDto searchDto) {
		AgentDisciplinaryDetailParam param = new AgentDisciplinaryDetailParam();
		param.agentCode=searchDto.getAgentCode();
		param.agentName=searchDto.getAgentName();
		param.teamType=searchDto.getTeamType();
		sqlManagerDb2Service.call(DS_SP_GET_AGENT_DISCIPLINE, param);
		AgentDisciplinaryDetailDto entity = new AgentDisciplinaryDetailDto();
		if(CommonCollectionUtil.isNotEmpty(param.data)) {
			entity=param.data.get(0);
		}
		return entity;
	}

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity exportAgentDisciplinary(AgentDisciplinarySearchDto searchDto, Locale locale) {
		ResponseEntity res = null;
		try {
			List<AgentDisciplinaryDto> lstdata = new ArrayList();
			searchDto.setPage(0);
			searchDto.setPageSize(0);
			ObjectDataRes<AgentDisciplinaryDto> resObj = getListAgentDisciplinaryStore(searchDto);
			lstdata=resObj.getDatas();
			
			//check position
			if (CollectionUtils.isNotEmpty(lstdata)) {
				Long total = lstdata.stream().map(x -> x.getAgentCode()).distinct().filter(Objects::nonNull).count();
				resObj.setTotalData((int)(long)total);
			}
			AgentInfoDb2 dataz = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(),searchDto.getAgentGroup(),null);
			
			for (AgentDisciplinaryDto dto : lstdata) {
				dto.setTerritory(dto.getTdName());
				dto.setBdth(dto.getBdthAgenttype()+": "+ dto.getBdthCode() +" - "+dto.getBdthName());
				dto.setArea(dto.getNCode() +" - "+dto.getNName());
				dto.setBdah(dto.getBdahAgenttype()+": "+ dto.getBdahCode() +" - "+dto.getBdahName());
				dto.setRegion(dto.getRCode() +" - "+dto.getRName());
				dto.setBdrh(dto.getBdrhAgenttype()+": "+ dto.getBdrhCode() +" - "+dto.getBdrhName());
				dto.setBdoh(dto.getBdohAgenttype()+": "+ dto.getBdohCode() +" - "+dto.getBdohName());
				if(StringUtils.isNotEmpty(dto.getGadCode())) {
					dto.setGad(dto.getGadType()+": "+ dto.getGadCode() +" - "+dto.getGadName());
				}
				dto.setOffice(dto.getOrgCode() +" - "+dto.getOrgName());
				dto.setBranch(dto.getHeadOfDepartmentType() +": "+dto.getHeadOfDepartmentCode().replace(dto.getOrgCode(), "").replace("A", "").replace("B", "").replace("C", "")+" - "+ dto.getHeadOfDepartmentName());
				dto.setUnit(dto.getManagerType()+": " + dto.getManagerCode().replace(dto.getOrgCode(), "").replace("A", "").replace("B", "").replace("C", "")+" - "+ dto.getManagerName());
				dto.setTvtc(dto.getAgentType()+": "+ dto.getAgentCode() +" - "+dto.getAgentName());
			}

			List<ItemColsExcelDto> cols = new ArrayList<>();
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String template = "";
			String templatePath = "";
			String templateName = "";
			switch (searchDto.getAgentGroup()) {
			case "CAO":
				template = "Danh_sach_dai_ly_vi_pham_ky_luat_cao.xlsx";
				templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				templateName = "Danh_sach_dai_ly_vi_pham_ky_luat_cao.xlsx";
				ImportExcelUtil.setListColumnExcel(AgentDCCaoEnum.class, cols);
				break;
			case "TH":
				template = "Danh_sach_dai_ly_vi_pham_ky_luat_th.xlsx";
				templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				templateName = "Danh_sach_dai_ly_vi_pham_ky_luat_th.xlsx";
				ImportExcelUtil.setListColumnExcel(AgentDCThEnum.class, cols);
				break;
			case "AH":
				template = "Danh_sach_dai_ly_vi_pham_ky_luat_ah.xlsx";
				templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				templateName = "Danh_sach_dai_ly_vi_pham_ky_luat_ah.xlsx";
				ImportExcelUtil.setListColumnExcel(AgentDCAhEnum.class, cols);
				break;
			case "RH":
				template = "Danh_sach_dai_ly_vi_pham_ky_luat_rh.xlsx";
				templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				templateName = "Danh_sach_dai_ly_vi_pham_ky_luat_rh.xlsx";
				ImportExcelUtil.setListColumnExcel(AgentDCRhEnum.class, cols);
				break;
			case "OH":
				template = "Danh_sach_dai_ly_vi_pham_ky_luat_rh.xlsx";
				templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				templateName = "Danh_sach_dai_ly_vi_pham_ky_luat_oh.xlsx";
				ImportExcelUtil.setListColumnExcel(AgentDCRhEnum.class, cols);
				break;
			case "BM":
				template = "Danh_sach_dai_ly_vi_pham_ky_luat_bm.xlsx";
				templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				templateName = "Danh_sach_dai_ly_vi_pham_ky_luat_bm.xlsx";
				ImportExcelUtil.setListColumnExcel(AgentDCBmEnum.class, cols);
				break;
			case "UM":
				template = "Danh_sach_dai_ly_vi_pham_ky_luat_um.xlsx";
				templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				templateName = "Danh_sach_dai_ly_vi_pham_ky_luat_um.xlsx";
				ImportExcelUtil.setListColumnExcel(AgentDCUmEnum.class, cols);
				break;
			default:
				template = "Danh_sach_dai_ly_vi_pham_ky_luat_um.xlsx";
				templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				templateName = "Danh_sach_dai_ly_vi_pham_ky_luat_um.xlsx";
				ImportExcelUtil.setListColumnExcel(AgentDCUmEnum.class, cols);
				break;
			}
			String startRow = "A8";
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();
				writeDateNow(searchDto.getAgentGroup(), xssfWorkbook, 0, new Date(),resObj.getTotalData() ,dataz.getOrg(), dataz.getAgentType() +": "+ dataz.getAgentCode().replace(dataz.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "") + " - " + dataz.getAgentName(),mapColStyle);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						AgentDisciplinaryDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				logger.error("exportAgentDisciplinary", e);
			}
		} catch (Exception e) {
			logger.error("exportAgentDisciplinary", e);
		}
		return res;
	}
	public void writeDateNow(String agentType, XSSFWorkbook xssfWorkbook, int sheetNumber, Date runDate,int total,String org, String leader,Map<String, CellStyle> mapColStyle)
			throws IOException {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
		Font fontTitle = xssfWorkbook.createFont();
		fontTitle.setColor(IndexedColors.BLUE.index);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short)15);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setFont(fontTitle);

		CellStyle titleCell = xssfSheet.getWorkbook().createCellStyle();
		Font fontCell = xssfWorkbook.createFont();
		fontCell.setFontName("Times New Roman");
		fontCell.setBold(true);
		titleCell.setFont(fontCell);
		titleCell.setBorderTop(BorderStyle.THIN);
		titleCell.setBorderLeft(BorderStyle.THIN);
		titleCell.setBorderRight(BorderStyle.THIN);
		titleCell.setBorderBottom(BorderStyle.THIN);
		mapColStyle.put("PAREN",titleCell);
		
		CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();

		Font fontTitleDate = xssfWorkbook.createFont();
		fontTitleDate.setColor(IndexedColors.BLUE.index);
		fontTitleDate.setFontName("Times New Roman");
		titleStyleDate.setFont(fontTitleDate);
		if (xssfSheet.getRow(1) != null)
			xssfSheet.getRow(1).getCell(0).setCellValue(org);
		else xssfSheet.createRow(1).createCell(0).setCellValue(org);

		if (xssfSheet.getRow(2) != null)
			xssfSheet.getRow(2).getCell(0).setCellValue(leader);
		else
			xssfSheet.createRow(2).createCell(0).setCellValue(leader);

		if (xssfSheet.getRow(3) != null)
			xssfSheet.getRow(3).getCell(0).setCellValue("Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
		else
			xssfSheet.createRow(3).createCell(0).setCellValue("Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));

		if (xssfSheet.getRow(4) != null) xssfSheet.getRow(4).getCell(0).setCellValue("Tổng số TVTC vi phạm: " + total);
		else xssfSheet.createRow(4).createCell(0).setCellValue("Tổng số TVTC vi phạm: " + total);
	}
	
	private String getSearchAgent(AgentDisciplinarySearchDto searchDto, String searchStr) {
		String search1 = "";
		String search2 = "";
		String search3 = "";
		String keyword1 = "";
		String keyword2 = "";
		String keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getAgentName())) {
			JSONObject jsonObj = new JSONObject(searchDto.getAgentName().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(A.LV3_AGENTNAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(A.LV3_AGENTCODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(A.LV3_AGENTTYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(A.LV3_AGENTNAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(A.LV3_AGENTCODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(A.LV3_AGENTTYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(A.LV3_AGENTNAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(A.LV3_AGENTCODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(A.LV3_AGENTTYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getAgentName())) {
			String search = "(" + search1 + " or " + search2 + " or " + search3 + ")";
			searchStr = searchStr.replace(search1, search);
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			String keyword = "(" + keyword1 + " or " + keyword2 + " or " + keyword3 + ")";
			searchStr = searchStr.replace(keyword1, keyword);
		}
		
		return searchStr;
	}
}
