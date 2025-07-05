package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.agent.dto.AgentPositionDto;
import vn.com.unit.cms.core.module.agent.dto.AgentPositionParam;
import vn.com.unit.cms.core.module.agent.dto.AgentSADto;
import vn.com.unit.cms.core.module.agent.dto.AgentSAPagingParam;
import vn.com.unit.cms.core.module.agent.dto.AgentSASearchDto;
import vn.com.unit.cms.core.module.agent.dto.DataRange;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentSearchDto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.enumdef.AgentSAEnumCAO;
import vn.com.unit.ep2p.enumdef.AgentSAEnumViewAH;
import vn.com.unit.ep2p.enumdef.AgentSAEnumViewBM;
import vn.com.unit.ep2p.enumdef.AgentSAEnumViewRHOH;
import vn.com.unit.ep2p.enumdef.AgentSAEnumViewTH;
import vn.com.unit.ep2p.service.AgentSAService;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.ApiEmulateService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class AgentSAServiceImpl implements AgentSAService {
	private static final String DS_SP_GET_LIST_AGENT_SA = "RPT_ODS.DS_SP_GET_LIST_AGENT_SA";
	private static final String DS_SP_POSITION_NAME = "RPT_ODS.DS_SP_GET_AGENT_TYPE";

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private ServletContext servletContext;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;

	public ObjectDataRes<AgentSADto> getListAgentSAByConditionStore(AgentSASearchDto dto) {
		List<AgentSADto> data = new ArrayList<>();
		int total = 0;
		try {
			AgentSAPagingParam param = new AgentSAPagingParam();
			dto.setFunctionCode("AGENT_SA");
			ObjectMapper mapper = new ObjectMapper();
			String stringJsonParam = "";
			try {
				stringJsonParam = mapper.writeValueAsString(dto);
			} catch (JsonProcessingException e) {
				logger.error("JsonProcessingException", e);
			}
			CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
					"AGENT_SA");
			param.agentCode = dto.getAgentCode();
			param.agentGroup = dto.getAgentGroup();
			param.page = common.getPage();
			param.pageSize = common.getPageSize();
			param.sort = common.getSort();
			param.search = this.getSearchAgent(dto, common.getSearch());
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("RH");
			arr.add("AH");
			arr.add("TH");
			arr.add("CAO");
			sqlManagerDb2Service.call(DS_SP_GET_LIST_AGENT_SA, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				data = param.data;
				
				data.stream().forEach(ds -> {
					if (arr.contains(dto.getAgentGroup())) {
						ds.setPhoneNum(null);
						ds.setIdCard(null);

					}
					if(StringUtils.isNotBlank(ds.getTerritoryName()) ||StringUtils.isNotBlank(ds.getAgentCodeBdth()) ||StringUtils.isNotBlank(ds.getNameBdth()) ) {
						 if (StringUtils.contains(ds.getNameBdth(), "Dummy Sales")) {
							 ds.setNameBdth(ds.getNameBdth());
						}else {
							ds.setNameBdth(ds.getTerritoryCode() + "-" + ds.getAgentCodeBdth() + "-" + ds.getNameBdth());
						}
					}
					if(StringUtils.isNotBlank(ds.getAreaName()) ||StringUtils.isNotBlank(ds.getAgentCodeBdah()) ||StringUtils.isNotBlank(ds.getNameBdah()) ) {
						 if (StringUtils.contains(ds.getNameBdah(), "Dummy Sales")) {
							 ds.setNameBdah(ds.getNameBdah());
						}else {
							ds.setNameBdah(ds.getAreaCode() + "-" + ds.getAgentCodeBdah() + "-" + ds.getNameBdah());
						}
					}
					if(StringUtils.isNotBlank(ds.getRegionName()) ||StringUtils.isNotBlank(ds.getAgentCodeBdrh()) ||StringUtils.isNotBlank(ds.getNameBdrh()) ) {
						 if (StringUtils.contains(ds.getNameBdrh(), "Dummy Sales")) {
							 ds.setNameBdrh(ds.getNameBdrh());
						}else {
							ds.setNameBdrh(ds.getRegionCode() + "-" + ds.getAgentCodeBdrh() + "-" + ds.getNameBdrh());
						}
					}
					if(StringUtils.isNotBlank(ds.getOfficeName()) ||StringUtils.isNotBlank(ds.getAgentCodeBdoh()) ||StringUtils.isNotBlank(ds.getNameBdoh()) ) {
						if (StringUtils.contains(ds.getNameBdoh(), "Dummy Sales")) {
							 ds.setNameBdoh(ds.getNameBdoh());
						}else {
							ds.setNameBdoh(ds.getOfficeCode() + "-" + ds.getAgentCodeBdoh() + "-" + ds.getNameBdoh());
						}
					}
					if(StringUtils.isNotBlank(ds.getOfficeCode()) ||StringUtils.isNotBlank(ds.getOfficeName()) ) {
						ds.setOfficeName(ds.getOfficeCode() + "-" + ds.getOfficeName());
					}
					if(StringUtils.isNotBlank(ds.getHeadOfDepartmentType()) ||StringUtils.isNotBlank(ds.getHeadOfDepartmentCode()) ||StringUtils.isNotBlank(ds.getHeadOfDepartmentName()) ) {
						if (StringUtils.contains(ds.getHeadOfDepartmentName(), "Dummy Sales")) {
							 ds.setHeadOfDepartmentName(ds.getHeadOfDepartmentName());
						}else {
							ds.setHeadOfDepartmentName(ds.getHeadOfDepartmentType() + ":" + ds.getHeadOfDepartmentCode().replace(ds.getOfficeCode(), "") + "-"+ ds.getHeadOfDepartmentName());
						}
					}
					if(StringUtils.isNotBlank(ds.getLv2Agenttype()) ||StringUtils.isNotBlank(ds.getManagerCode()) ||StringUtils.isNotBlank(ds.getManagerName()) ) {
						if (StringUtils.contains(ds.getManagerName(), "Nhóm thuộc")) {
							 ds.setManagerName(ds.getManagerName());
						}else if (StringUtils.contains(ds.getManagerName(), "Dummy Sales")) {
							 ds.setManagerName(ds.getManagerName());
						}else {
							ds.setManagerName(ds.getLv2Agenttype() + ":" + ds.getManagerCode().replace("A", "").replace("B", "").replace("C", "") + "-" + ds.getManagerName());
						}
					}
					if(StringUtils.isNotBlank(ds.getTvtcType()) ||StringUtils.isNotBlank(ds.getTvtcCode()) ||StringUtils.isNotBlank(ds.getTvtcName()) ) {
						if (StringUtils.contains(ds.getTvtcName(), "Dummy Sales")) {
							 ds.setTvtcName(ds.getTvtcName());
						}else {
							ds.setTvtcName(ds.getTvtcType() + ":" + ds.getTvtcCode() + "-" + ds.getTvtcName());
						}
					}
				});
				total = param.totalRows;
			}
		} catch (Exception e) {
			logger.error("getListAgentSAByConditionStore", e);
		}
		ObjectDataRes<AgentSADto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}

	@Override
	public ObjectDataRes<AgentSADto> getListAgentSAByCondition(AgentSASearchDto dto) {
		ObjectDataRes<AgentSADto> resObj = getListAgentSAByConditionStore(dto);
		return resObj;
	}

	@Override
	public List<DataRange> getDataRange(String userName) {
		DataRangeParam param = new DataRangeParam();
		param.userName = userName;
		sqlManagerDb2Service.call(DS_SP_GET_LIST_AGENT_SA, param);
		List<DataRange> data = new ArrayList<>();
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
		}
		return data;
	}

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity exportAgentSA(AgentSASearchDto dto, Locale locale) {
		ResponseEntity res = null;
		try {
			List<AgentSADto> lstdata = new ArrayList();
			dto.setPage(0);
			dto.setPageSize(0);
			 ObjectDataRes<AgentSADto> data = getListAgentSAByConditionStore(dto);
			

			 lstdata = data.getDatas();

			// check position
			AgentPositionDto agent = getAgentTypeName(dto.getAgentCode());
			String position = agent.getAgentTypeName() + " " + agent.getAgentName();

			for (AgentSADto agentSADto : lstdata) {
					
				agentSADto.setBdth(agentSADto.getNameBdth());
				agentSADto.setBdah(agentSADto.getNameBdah());
				agentSADto.setBdrh(agentSADto.getNameBdrh());
				agentSADto.setBdoh(agentSADto.getNameBdoh());
				agentSADto.setOffice(agentSADto.getOfficeName());
				if(StringUtils.isNotBlank(agentSADto.getGadType()) ||StringUtils.isNotBlank(agentSADto.getGadCode()) ||StringUtils.isNotBlank(agentSADto.getGadName()) ) {
					agentSADto.setGad(agentSADto.getGadType() + ": "  + agentSADto.getGadCode() + " - " + agentSADto.getGadName());
				}
				agentSADto.setHeadOfDepartment(agentSADto.getHeadOfDepartmentName());			
				agentSADto.setManager(agentSADto.getManagerName());		
				agentSADto.setTvtc(agentSADto.getTvtcName());
				agentSADto.setAddress(CommonStringUtil.isNotEmpty(agentSADto.getTemporaryAddress()) ? agentSADto.getTemporaryAddress() : agentSADto.getPermanentAddress());
			}
			
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = "Danh_sach_TVTC_phuc_vu_SA.xlsx";
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);

			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {

			String[] titleHeader = null;
            List<ItemColsExcelDto> cols = new ArrayList<>();
            if(dto.getAgentGroup().equalsIgnoreCase("CAO")
                    || dto.getAgentGroup().equalsIgnoreCase("TH")
                    || dto.getAgentGroup().equalsIgnoreCase("AH")
                    || dto.getAgentGroup().equalsIgnoreCase("RH")
                    || dto.getAgentGroup().equalsIgnoreCase("OH")
					|| dto.getAgentGroup().equalsIgnoreCase("SO")
					|| dto.getAgentGroup().equalsIgnoreCase("BM")
                    || dto.getAgentGroup().equalsIgnoreCase("SBM")){
            	
                if(dto.getAgentGroup().equalsIgnoreCase("CAO")) {
                    titleHeader = new String[] {
                            "STT","Territory", "BDTH", "Area","BDAH", "Office/GA","BDOH","GAD", "Trưởng phòng","Quản lý","TVTC","Ngày bắt đầu hoạt động","Địa chỉ","Số HĐ phục vụ","Số tháng chưa hoạt động"};
                    ImportExcelUtil.setListColumnExcel(AgentSAEnumCAO.class, cols);
					xssfWorkbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 0, 16));
                }
                else if(dto.getAgentGroup().equalsIgnoreCase("TH")) {
                    titleHeader = new String[] {
                            "STT", "Area","BDAH", "Office/GA","BDOH","GAD", "Trưởng phòng","Quản lý","TVTC","Ngày bắt đầu hoạt động","Địa chỉ","Số HĐ phục vụ","Số tháng chưa hoạt động"};
                    ImportExcelUtil.setListColumnExcel(AgentSAEnumViewTH.class, cols);
					xssfWorkbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
                }
                else if(dto.getAgentGroup().equalsIgnoreCase("AH")) {
                    titleHeader = new String[] {
                            "STT", "Office/GA","BDOH","GAD", "Trưởng phòng","Quản lý","TVTC","Ngày bắt đầu hoạt động","Địa chỉ","Số HĐ phục vụ","Số tháng chưa hoạt động"};
                    ImportExcelUtil.setListColumnExcel(AgentSAEnumViewAH.class, cols);
					xssfWorkbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 0, 12));

				}
                else if(dto.getAgentGroup().equalsIgnoreCase("RH") || dto.getAgentGroup().equalsIgnoreCase("OH")|| dto.getAgentGroup().equalsIgnoreCase("SO")) {
                    titleHeader = new String[] {
                            "STT", "Office/GA","BDOH","GAD", "Trưởng phòng","Quản lý","TVTC","Ngày bắt đầu hoạt động","Địa chỉ","Số HĐ phục vụ","Số tháng chưa hoạt động"};
                    ImportExcelUtil.setListColumnExcel(AgentSAEnumViewRHOH.class, cols);
					xssfWorkbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

				}
                else if(dto.getAgentGroup().equalsIgnoreCase("BM") || dto.getAgentGroup().equalsIgnoreCase("SBM")) {
                    titleHeader = new String[] {
                            "STT", "Quản lý","TVTC","CCCD/CMND","Ngày bắt đầu hoạt động","ĐTDĐ","Địa chỉ","Số HĐ phục vụ","Số tháng chưa hoạt động"};
                    ImportExcelUtil.setListColumnExcel(AgentSAEnumViewBM.class, cols);
					xssfWorkbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
				}
            }
            else {
            	 titleHeader = new String[] {
                         "STT","Territory", "BDTH", "Area","BDAH", "Office/GA","BDOH","GAD", "Trưởng phòng","Quản lý","TVTC","Ngày bắt đầu hoạt động","Địa chỉ","Số HĐ phục vụ","Số tháng chưa hoạt động"};
                ImportExcelUtil.setListColumnExcel(AgentSAEnumCAO.class, cols);
				xssfWorkbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 0, 16));
			}
				Map<String, CellStyle> mapColStyle = null;
				List<String> datas = Arrays.asList(titleHeader);
				titleHeader = datas.toArray(new String[0]);
				setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, position, new Date(), titleHeader);

				CellStyle headerCellStyle = xssfWorkbook.getSheetAt(0).getWorkbook().createCellStyle();
				Font font = xssfWorkbook.createFont();
				font.setColor(IndexedColors.BLUE.index);
				font.setFontName("Times new roman");
				headerCellStyle.setFont(font);

				xssfWorkbook.getSheetAt(0).getRow(4).getCell(0).setCellValue("Tổng Số SA: " + lstdata.size());
				xssfWorkbook.getSheetAt(0).getRow(4).getCell(0).setCellStyle(headerCellStyle);

				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
						AgentSADto.class, cols, datePattern, "A8", mapColFormat, mapColStyle, setMapColDefaultValue,
						null, true, templateName, true,path);
			} catch (Exception e) {
				logger.error("exportAgentSA", e);
			}
		} catch (Exception e) {
			logger.error("exportAgentSA", e);
		}
		return res;
	}

	@SuppressWarnings("unused")
    public void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String dataString,
			Date runDate, String[] titleHeader) {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
        Font font = xssfWorkbook.createFont();
        font.setColor(IndexedColors.BLUE.index);
        font.setFontName("Times new roman");
        headerCellStyle.setFont(font);
        
		XSSFRow row = null;
		int cellIndex = 0;
		row = xssfSheet.getRow(2);
		XSSFCell cellData = row.getCell(cellIndex);
		if (cellData == null)
			cellData = row.createCell(cellIndex);
		    cellData.setCellStyle(headerCellStyle);
		row = xssfSheet.getRow(3);
		XSSFCell cellDate = row.getCell(cellIndex);
		if (cellDate == null)
			cellDate = row.createCell(cellIndex);
		cellDate.setCellStyle(headerCellStyle);
		cellData.setCellValue("Phạm vi dữ liệu: " + dataString);
		cellDate.setCellValue("Ngày cập nhật: " + DateUtils.formatDateToString(runDate, "dd/MM/yyyy"));

        CellStyle titleCellStyle = xssfSheet.getWorkbook().createCellStyle();
        titleCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font titleFont = xssfWorkbook.createFont();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleFont.setBold(true);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        if(titleHeader!=null && titleHeader.length>0) {
          for (int i = 0; i < titleHeader.length; i++) {
              XSSFRow row4 = xssfSheet.getRow(6);
              XSSFCell cell4 = null;
              if(row4==null) {
                  row4=xssfSheet.createRow(6);
                  cell4 = row4.getCell(i);
                  if(cell4==null) {
                      cell4=row4.createCell(i);
                  }
              }
              else {
                  if(cell4==null) {
                      cell4=row4.createCell(i);
                  }
                  else {
                      cell4 = row4.getCell(i);
                  }
              }
          cell4.setCellValue(titleHeader[i]);
          cell4.setCellStyle(titleCellStyle);
          }
      }
	}

	
	
	@Override
	public AgentPositionDto getAgentTypeName(String agentCode) {
		AgentPositionDto entity = new AgentPositionDto();
		AgentPositionParam param = new AgentPositionParam();
		try {
			param.agentCode = agentCode;
			sqlManagerDb2Service.call(DS_SP_POSITION_NAME, param);
			entity = param.data.get(0);
		} catch (Exception e) {
			logger.error("getAgentTypeName", e);
		}
		return entity;
	}

	private String getSearchAgent(AgentSASearchDto searchDto, String searchStr) {
		String search1 = "";
		String search2 = "";
		String search3 = "";
		String keyword1 = "";
		String keyword2 = "";
		String keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getNameBdth())) {
			JSONObject jsonObj = new JSONObject(searchDto.getNameBdth().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDTH_NAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(BDTH_CODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(BDTH_AGENTTYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDTH_NAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(BDTH_CODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(BDTH_AGENTTYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDTH_NAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(BDTH_CODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(BDTH_AGENTTYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getNameBdth())) {
			String search = "(" + search1 + " or " + search2 + " or " + search3 + ")";
			searchStr = searchStr.replace(search1, search);
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			String keyword = "(" + keyword1 + " or " + keyword2 + " or " + keyword3 + ")";
			searchStr = searchStr.replace(keyword1, keyword);
		}
		
		search1 = "";
		search2 = "";
		search3 = "";
		keyword1 = "";
		keyword2 = "";
		keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getNameBdah())) {
			JSONObject jsonObj = new JSONObject(searchDto.getNameBdah().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDAH_NAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(BDAH_CODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(BDAH_AGENTTYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDAH_NAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(BDAH_CODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(BDAH_AGENTTYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDAH_NAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(BDAH_CODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(BDAH_AGENTTYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getNameBdah())) {
			String search = "(" + search1 + " or " + search2 + " or " + search3 + ")";
			searchStr = searchStr.replace(search1, search);
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			String keyword = "(" + keyword1 + " or " + keyword2 + " or " + keyword3 + ")";
			searchStr = searchStr.replace(keyword1, keyword);
		}
		
		search1 = "";
		search2 = "";
		search3 = "";
		keyword1 = "";
		keyword2 = "";
		keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getNameBdrh())) {
			JSONObject jsonObj = new JSONObject(searchDto.getNameBdrh().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDRH_NAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(BDRH_CODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(BDRH_AGENTTYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDRH_NAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(BDRH_CODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(BDRH_AGENTTYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDRH_NAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(BDRH_CODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(BDRH_AGENTTYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getNameBdrh())) {
			String search = "(" + search1 + " or " + search2 + " or " + search3 + ")";
			searchStr = searchStr.replace(search1, search);
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			String keyword = "(" + keyword1 + " or " + keyword2 + " or " + keyword3 + ")";
			searchStr = searchStr.replace(keyword1, keyword);
		}
		
		search1 = "";
		search2 = "";
		search3 = "";
		keyword1 = "";
		keyword2 = "";
		keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getNameBdoh())) {
			JSONObject jsonObj = new JSONObject(searchDto.getNameBdoh().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDOH_NAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(BDOH_CODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(BDOH_AGENTTYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDOH_NAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(BDOH_CODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(BDOH_AGENTTYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(BDOH_NAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(BDOH_CODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(BDOH_AGENTTYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getNameBdoh())) {
			String search = "(" + search1 + " or " + search2 + " or " + search3 + ")";
			searchStr = searchStr.replace(search1, search);
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			String keyword = "(" + keyword1 + " or " + keyword2 + " or " + keyword3 + ")";
			searchStr = searchStr.replace(keyword1, keyword);
		}
		
		search1 = "";
		search2 = "";
		search3 = "";
		keyword1 = "";
		keyword2 = "";
		keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getHeadOfDepartmentName())) {
			JSONObject jsonObj = new JSONObject(searchDto.getHeadOfDepartmentName().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(LV1_AGENTNAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(LV1_AGENTCODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(LV1_AGENTTYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(LV1_AGENTNAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(LV1_AGENTCODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(LV1_AGENTTYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(LV1_AGENTNAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(LV1_AGENTCODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(LV1_AGENTTYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getHeadOfDepartmentName())) {
			String search = "(" + search1 + " or " + search2 + " or " + search3 + ")";
			searchStr = searchStr.replace(search1, search);
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			String keyword = "(" + keyword1 + " or " + keyword2 + " or " + keyword3 + ")";
			searchStr = searchStr.replace(keyword1, keyword);
		}
		
		search1 = "";
		search2 = "";
		search3 = "";
		keyword1 = "";
		keyword2 = "";
		keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getManagerName())) {
			JSONObject jsonObj = new JSONObject(searchDto.getManagerName().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(LV2_AGENTNAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(LV2_AGENTCODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(LV2_AGENTTYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(LV2_AGENTNAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(LV2_AGENTCODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(LV2_AGENTTYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(LV2_AGENTNAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(LV2_AGENTCODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(LV2_AGENTTYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getManagerName())) {
			String search = "(" + search1 + " or " + search2 + " or " + search3 + ")";
			searchStr = searchStr.replace(search1, search);
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			String keyword = "(" + keyword1 + " or " + keyword2 + " or " + keyword3 + ")";
			searchStr = searchStr.replace(keyword1, keyword);
		}
		
		search1 = "";
		search2 = "";
		search3 = "";
		keyword1 = "";
		keyword2 = "";
		keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getTvtcName())) {
			JSONObject jsonObj = new JSONObject(searchDto.getTvtcName().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(LV3_AGENTNAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(LV3_AGENTCODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(LV3_AGENTTYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(LV3_AGENTNAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(LV3_AGENTCODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(LV3_AGENTTYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(LV3_AGENTNAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(LV3_AGENTCODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(LV3_AGENTTYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getTvtcName())) {
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
