package vn.com.unit.ep2p.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.report.dto.ReportResultBusinessGadPremiumParam;
import vn.com.unit.cms.core.module.report.dto.ReportResultSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportResultSumaryDto;
import vn.com.unit.cms.core.module.report.dto.ReportResultSumaryParam;
import vn.com.unit.cms.core.module.report.dto.ReportResultViewGADTabPremiumDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.enumdef.ReportBusinessResultManpowerEnum;
import vn.com.unit.ep2p.enumdef.ReportBusinessResultPremiumEnum;
import vn.com.unit.ep2p.enumdef.ReportBusinessResultQtdYtdPremiumEnum;
import vn.com.unit.ep2p.enumdef.*;
import vn.com.unit.ep2p.service.ApiIncomeReportService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.ep2p.service.ReportBusinessResultGaService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.CellStyleDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ReportBusinessResultServiceImpl implements ReportBusinessResultGaService{

    private enum DataType {
		LONG, DOUBLE, INTEGER, STRING, DATE, TIMESTAMP, INT, BIGDECIMAL, BOOLEAN, BYTE;
	}

	private static String EMP_STRING = "";
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
    private SystemConfig systemConfig;
	
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String STORE_REPORT_BUSINESS_SUMARY = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_LEADER_SUMMARY";
	private static final String STORE_REPORT_BUSINESS_MTD_VIEW_GAD_PREMIUM = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_GROUP_PREMIUM";
	private static final String STORE_REPORT_BUSINESS_MTD_VIEW_GAD_MANPOWER = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_GROUP_MANPOWER";
	
	@Autowired
    protected ObjectMapper objectMapper;
	
	private String formatYyyyMM(String year, String month) {
        Date currentDate= new Date();
        if(month!=null) {
            month=("0"+Integer.parseInt(month));
        }
        //default current date
        if(year==null ) {
            year= (new SimpleDateFormat("yyyy").format(currentDate));
        }
        if(month==null ) {
            month=(new SimpleDateFormat("MM").format(currentDate));
        }
        String yyyyMM = year + month;
        return yyyyMM;
    }
	
	@Override
	public ObjectDataRes<ReportResultViewGADTabPremiumDto> getListResultViewGadPremium(ReportResultSearchDto searchDto) {
        searchDto.setAgentCode(UserProfileUtils.getFaceMask());
        searchDto.setFunctionCode("GROUP_OH");
		List<ReportResultViewGADTabPremiumDto> datas = new ArrayList<>();
		ObjectDataRes<ReportResultViewGADTabPremiumDto> resultData = new ObjectDataRes<>();
		try {
		    ReportResultBusinessGadPremiumParam param = new ReportResultBusinessGadPremiumParam();
			param.agentCode=searchDto.getAgentCode();
			param.orgCode=searchDto.getOrgCode();
			param.agentGroup=searchDto.getAgentGroup();
			param.time=searchDto.getTime();
			param.dataType=searchDto.getDataType();
			param.dataLevel=searchDto.getDataLevel();
            if(StringUtils.isEmpty(searchDto.getTime())) {
                param.time=formatYyyyMM(searchDto.getYear(), searchDto.getMonth());
            }
			param.page=searchDto.getPage();
            param.pageSize=searchDto.getPageSize();
            param.sort=searchDto.getSort();
            param.search = searchAdvance(searchDto);
            
            sqlManagerDb2Service.call(STORE_REPORT_BUSINESS_MTD_VIEW_GAD_PREMIUM, param);
            datas = param.data;
            boolean first = false;
            // group data
            List<ReportResultViewGADTabPremiumDto>  lstFinal = new ArrayList<>();
            // lv0
			ReportResultViewGADTabPremiumDto lv0 = sumByObject(datas.stream().filter(e -> e.getTreeLevel() == 0).collect(Collectors.toList()), 0);
			lstFinal.add(lv0);
			// lv1
			Map<String, List<ReportResultViewGADTabPremiumDto>> maplv1 = datas.stream()
					.filter(e -> e.getTreeLevel() == 1)
					.collect(Collectors.groupingBy(ga ->ga.getOrgId()));
			
			for (Entry<String, List<ReportResultViewGADTabPremiumDto>> entry : maplv1.entrySet()) {
			    String key = entry.getKey();
			    if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
			    	ReportResultViewGADTabPremiumDto item = sumByObject(entry.getValue(), 1);
			    	lstFinal.add(item);
			    }
			}
			// lv2
			List<ReportResultViewGADTabPremiumDto> lv2 = datas.stream()
					.filter(e -> e.getTreeLevel() == 1).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(lv2)) {
				for (ReportResultViewGADTabPremiumDto dto : lv2) {
					dto.setTreeLevel(2);
					dto.setChildGroup("BM");
				}
			}
			lstFinal.addAll(lv2);
            for (ReportResultViewGADTabPremiumDto dto : lstFinal) {
                try{
                    mapAgent(dto, searchDto.getAgentGroup(), dto.getTreeLevel() == 0);

                    if (!isNullOrZero(dto.getK2App()) && !isNullOrZero(dto.getK2Epp())) {
                    	dto.setK2((dto.getK2App().multiply(new BigDecimal(100))).divide(dto.getK2Epp(), 2, RoundingMode.FLOOR));
                    } else {
                    	dto.setK2((new BigDecimal( (Integer) 0)));
                    }
                    if (!isNullOrZero(dto.getK2plusApp()) && !isNullOrZero(dto.getK2plusEpp())) {
                    	dto.setK2Plus((dto.getK2plusApp().multiply(new BigDecimal(100))).divide(dto.getK2plusEpp(), 2, RoundingMode.FLOOR));
                    } else {
                    	dto.setK2Plus((new BigDecimal( (Integer) 0)));
                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                }

            }
			resultData.setDatas(lstFinal);
			resultData.setTotalData(param.totalRow);
 		} catch(Exception e) {
 		   logger.error("getListResultViewGadPremium: ", e);
 		}
 		return resultData;
	}
	
	@Override
	public ObjectDataRes<ReportResultViewGADTabPremiumDto> getListResultViewGadManpower(ReportResultSearchDto searchDto) {
	    ObjectDataRes<ReportResultViewGADTabPremiumDto> resultData = new ObjectDataRes<>();
	    List<ReportResultViewGADTabPremiumDto> datas = new ArrayList<>();
		try {
		    ReportResultBusinessGadPremiumParam param = new ReportResultBusinessGadPremiumParam();
	        param.agentCode=searchDto.getAgentCode();
            param.orgCode=searchDto.getOrgCode();
            param.agentGroup=searchDto.getAgentGroup();
            param.time=searchDto.getTime();
            param.dataType=searchDto.getDataType();
            param.dataLevel=searchDto.getDataLevel();
            if(StringUtils.isEmpty(searchDto.getTime())) {
                param.time=formatYyyyMM(searchDto.getYear(), searchDto.getMonth());
            }
            param.page=searchDto.getPage();
            param.pageSize=searchDto.getPageSize();
            param.sort=searchDto.getSort();
            param.search = searchAdvance(searchDto);
			sqlManagerDb2Service.call(STORE_REPORT_BUSINESS_MTD_VIEW_GAD_MANPOWER, param);
			datas = param.data;
			// group data
            List<ReportResultViewGADTabPremiumDto>  lstFinal = new ArrayList<>();
            // lv0
			ReportResultViewGADTabPremiumDto lv0 = sumByObject(datas.stream().filter(e -> e.getTreeLevel() == 0).collect(Collectors.toList()), 0);
			lstFinal.add(lv0);
			// lv1
			Map<String, List<ReportResultViewGADTabPremiumDto>> maplv1 = datas.stream()
					.filter(e -> e.getTreeLevel() == 1)
					.collect(Collectors.groupingBy(ga ->ga.getOrgId()));
			
			for (Entry<String, List<ReportResultViewGADTabPremiumDto>> entry : maplv1.entrySet()) {
			    String key = entry.getKey();
			    if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
			    	ReportResultViewGADTabPremiumDto item = sumByObject(entry.getValue(), 1);
			    	lstFinal.add(item);
			    }
			}
			// lv2
			List<ReportResultViewGADTabPremiumDto> lv2 = datas.stream()
					.filter(e -> e.getTreeLevel() == 1).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(lv2)) {
				for (ReportResultViewGADTabPremiumDto dto : lv2) {
					dto.setTreeLevel(2);
					dto.setChildGroup("BM");
				}
			}
			lstFinal.addAll(lv2);
            for (ReportResultViewGADTabPremiumDto dto : lstFinal) {
                mapAgent(dto, searchDto.getAgentGroup(), dto.getTreeLevel() == 0);
            }
            resultData.setDatas(lstFinal);
            resultData.setTotalData(param.totalRow);
 		} catch(Exception e) {
 		   logger.error("getListResultViewGadManpower: ", e);
 		}
 		return resultData;
	}
	
	@Autowired
	ApiIncomeReportService apiIncomeReportService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResponseEntity exportGad(ReportResultSearchDto dto, Integer type) {
        ResponseEntity res = null;
        try {
            dto.setPage(0);
            dto.setPageSize(0);
            List<ReportResultViewGADTabPremiumDto> lstdata = new ArrayList<>();
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            String templateName = "Bao_cao_ket_qua_KD_cua_GA_doanh_so.xlsx";
            if(type==1) {
                if(!dto.getDataType().equalsIgnoreCase("MTD")) {
                    templateName = "Bao_cao_ket_qua_KD_cua_GA_qtd_ytd_doanh_so.xlsx";
                    ImportExcelUtil.setListColumnExcel(ReportBusinessResultQtdYtdPremiumEnum.class, cols);
                }else {
                    templateName = "Bao_cao_ket_qua_KD_cua_GA_doanh_so.xlsx";
                    ImportExcelUtil.setListColumnExcel(ReportBusinessResultPremiumEnum.class, cols);
                }
                lstdata=getListResultViewGadPremium(dto).getDatas();
            }
            else{
                templateName = "Bao_cao_ket_qua_KD_cua_GA_luc_luong_tu_van.xlsx";
                ImportExcelUtil.setListColumnExcel(ReportBusinessResultManpowerEnum.class, cols);
                lstdata=getListResultViewGadManpower(dto).getDatas();
            }
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A7";
            ReportResultViewGADTabPremiumDto root = lstdata.stream().filter(e->e.getTreeLevel().equals(0)).findFirst().get();
            List<ReportResultViewGADTabPremiumDto> allData = new ArrayList<>();
            // get lv1
            List<ReportResultViewGADTabPremiumDto> lv1 = lstdata.stream().filter(e->e.getTreeLevel() == 1).collect(Collectors.toList());
            for (ReportResultViewGADTabPremiumDto groupLv1 : lv1) {
                allData.add(groupLv1);
                // get lv2
                List<ReportResultViewGADTabPremiumDto> lv2 = lstdata.stream().filter(e->e.getTreeLevel() == 2
                        && e.getParentAgentCode().equals(groupLv1.getAgentCode()) && e.getOrgParentId().equals(groupLv1.getOrgId())).collect(Collectors.toList());
                allData.addAll(lv2);
            }
            allData.add(root);
            String typeDate="";
            List<String> datas = new ArrayList<String>();
            if(dto.getDataType().equalsIgnoreCase("MTD")) {
                typeDate="THÁNG ";
            }
            else if(dto.getDataType().equalsIgnoreCase("QTD")) {
                typeDate="QUÝ ";
            }
            else if(dto.getDataType().equalsIgnoreCase("YTD")) {
                typeDate="NĂM ";
            }

            allData.forEach(x->{
            	if (x.getK2Epp() == null || x.getK2Epp().intValue() == 0) {
            		x.setK2(null);
            	}
            	if (x.getK2plusEpp() == null || x.getK2plusEpp().intValue() == 0) {
            		x.setK2Plus(null);
            	}
                if(ObjectUtils.isNotEmpty(x.getK2()))
                    x.setK2Str(String.valueOf(x.getK2()).concat("%"));
                if(ObjectUtils.isNotEmpty(x.getK2Plus()))
                    x.setK2PlusStr(String.valueOf(x.getK2Plus()).concat("%"));
            });

            datas.add("BÁO CÁO KẾT QUẢ KINH DOANH GA "+typeDate+ formatDate(dto.getTime(), dto.getDataType()));
            datas.add("");
            datas.add("Cập nhật đến ngày: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;

                if(dto.getDataType().equals("MTD")) {
                    if(xssfWorkbook.getSheetAt(0).getRow(3) != null) {
                       if(type==1) xssfWorkbook.getSheetAt(0).getRow(3).getCell(14).setCellValue("Chi tiết theo tháng cùng kỳ năm trước");
                       else  xssfWorkbook.getSheetAt(0).getRow(3).getCell(13).setCellValue("Chi tiết theo tháng cùng kỳ năm trước");
                    }
                    else{
                        if(type==1) xssfWorkbook.getSheetAt(0).createRow(3).createCell(14).setCellValue("Chi tiết theo tháng cùng kỳ năm trước");
                        else  xssfWorkbook.getSheetAt(0).createRow(3).createCell(13).setCellValue("Chi tiết theo tháng cùng kỳ năm trước");
                    }
                }else if(dto.getDataType().equals("QTD")){
                    if(xssfWorkbook.getSheetAt(0).getRow(3) != null) {
                        if(type==1) xssfWorkbook.getSheetAt(0).getRow(3).getCell(12).setCellValue("Quý cùng kỳ năm trước");
                        else xssfWorkbook.getSheetAt(0).getRow(3).getCell(13).setCellValue("Quý cùng kỳ năm trước");
                    }
                    else {
                        if(type==1)xssfWorkbook.getSheetAt(0).createRow(3).createCell(12).setCellValue("Quý cùng kỳ năm trước");
                        else xssfWorkbook.getSheetAt(0).createRow(3).createCell(13).setCellValue("Quý cùng kỳ năm trước");
                    }
                }else if(dto.getDataType().equals("YTD")){

                    if(xssfWorkbook.getSheetAt(0).getRow(3) != null) {
                        if(type==1)xssfWorkbook.getSheetAt(0).getRow(3).getCell(12).setCellValue("Cùng kỳ năm trước");
                        else  xssfWorkbook.getSheetAt(0).getRow(3).getCell(13).setCellValue("Cùng kỳ năm trước");
                    }
                    else  {
                        if(type==1)xssfWorkbook.getSheetAt(0).createRow(3).createCell(12).setCellValue("Cùng kỳ năm trước");
                        else  xssfWorkbook.getSheetAt(0).createRow(3).createCell(13).setCellValue("Cùng kỳ năm trước");
                    }
                }

                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                apiIncomeReportService.setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, null, "", "A1", datas);
                List<ReportResultViewGADTabPremiumDto> allDataNull = new ArrayList<>();
                res =  doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, allData.size() > 1 ?  allData:allDataNull, ReportResultViewGADTabPremiumDto.class
                        , cols, datePattern, startRow, null, mapColStyle,
                        null, null, true, templateName, true, path);
            } catch (Exception e) {
                logger.error("##doExport##", e);
            }
          } catch (Exception e) {
              logger.error("##exportGad##", e);
        }
        return res;
    }

    private String formatDate(String time, String typeDate){
	    if(typeDate.equalsIgnoreCase("MTD")) {
	       return (time.substring(0, 6)).substring(4) +"/" +time.substring(0, 4);
	    }
	    else if(typeDate.equalsIgnoreCase("QTD")) {
	        return (time.substring(0, 6)).substring(4) +"/" +time.substring(0, 4);
        }
        else if(typeDate.equalsIgnoreCase("YTD")) {
            return time.substring(0, 4);
        }
	    return "";
	}
	
    @Override
    public ReportResultSumaryDto getSumary(String agentCode, String orgCode, String agentGroup) {
        ReportResultSumaryDto resultData = new ReportResultSumaryDto();
        try {
            ReportResultSumaryParam param = new ReportResultSumaryParam(agentCode, orgCode, agentGroup, null);
            sqlManagerDb2Service.call(STORE_REPORT_BUSINESS_SUMARY, param);
            resultData=param.datas.get(0);
        } catch(Exception e) {
            logger.error("getSumary", e);
        }
            return resultData;
    }


    @Override
    public void setConditionSearch(CommonSearchWithPagingDto data, int level, String agentGroup) {
        if(StringUtils.isNotEmpty(agentGroup)) {
            switch (agentGroup) {
                case "CAO":
                    if(level == 1) data.setBdahName(null);
                    if(level == 2) data.setBdthName(null);
                    break;
                case "TH":
                    if(level == 1) data.setBdrhName(null);
                    if(level == 2) data.setBdahName(null);
                    break;
                case "AH":
                    if(level == 1) data.setBdohName(null);
                    if(level == 2) data.setBdrhName(null);
                    break;
                case "RH":
                    if(level == 1) data.setGaName(null);
                    if(level == 2) data.setBdohName(null);
                    break;
                case "OH":
                    if(level == 1) data.setBranchName(null);
                    if(level == 2) data.setGaName(null);
                    break;
                case "GAD":
                    if(level == 1) data.setBranchName(null);
                    if(level == 2) data.setGaName(null);
                    break;
                case "GA":
                    if(level == 1) data.setUnitName(null);
                    if(level == 2) data.setBranchName(null);
                    break;
                default:
                    break;
            }
        }
    }
    
    private void setConditionSearch(ReportResultSearchDto data, int level) {
        if(StringUtils.isNotEmpty(data.getAgentGroup())) {
            switch (data.getAgentGroup()) {
            case "CAO":
                if(level == 1) data.setBdahName(null);
                if(level == 2) data.setBdthName(null);
                break;
            case "TH":
                if(level == 1) data.setBdrhName(null);
                if(level == 2) data.setBdahName(null);
                break;
            case "AH":
                if(level == 1) data.setBdohName(null);
                if(level == 2) data.setBdrhName(null);
                break;
            case "RH":
                if(level == 1) data.setGaName(null);
                if(level == 2) data.setBdohName(null);
                break;
            case "OH":
                if(level == 1) data.setBranchName(null);
                if(level == 2) data.setGaName(null);
                break;
            case "GAD":
                if(level == 1) data.setBranchName(null);
                if(level == 2) data.setGaName(null);
                break;
            case "GA":
                if(level == 1) data.setUnitName(null);
                if(level == 2) data.setBranchName(null);
                break;
            default:
                break;
            }
        }
        
    }
    
    private void mapAgent(ReportResultViewGADTabPremiumDto data, String agentGroup, boolean first) {
        if (StringUtils.isNotEmpty(data.getChildGroup())) {
            if (first) {
                switch (data.getChildGroup()) {
                    case "GAD":
                        data.setGaCode(data.getAgentCode());
                        data.setGaName("Tổng cộng");
                        data.setGaType(data.getAgentType());
                        break;
                    case "GA":
                        data.setBranchCode(data.getAgentCode());
                        data.setBranchName("Tổng cộng");
                        data.setBranchType(data.getAgentType());
                        break;
                    case "BM":
                        data.setUnitCode(data.getAgentCode());
                        data.setUnitName("Tổng cộng");
                        data.setUnitType(data.getAgentType());
                        break;
                    case "UM":
                        data.setUnitCode(data.getAgentCode());
                        data.setUnitName("Tổng cộng");
                        data.setUnitType(data.getAgentType());
                    default:
                        data.setAgentCode(data.getAgentCode());
                        break;
                }
            } else {
                switch (data.getChildGroup()) {
                    case "GAD":
                        data.setGaCode(data.getAgentCode());
                        data.setGaName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
                        data.setGaType(data.getAgentType());
                        break;
                    case "GA":
                        data.setGaCode(data.getAgentCode());
                        data.setGaName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
                        data.setGaType(data.getAgentType());
                        break;
                    case "BM":
        				data.setBranchCode(data.getAgentCode());
        				data.setBranchName(data.getAgentType() + ": " + data.getAgentCode().replace(data.getOrgId(), "") + "-" + data.getAgentName());
        				data.setBranchType(data.getAgentType());
        				if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
        					data.setBranchName(data.getAgentName());
        				}
        				break;
                    case "BM_G":
        				data.setBranchCode(data.getAgentCode());
        				data.setBranchName(data.getAgentType() + ": " + data.getAgentCode().replace(data.getOrgId(), "") + "-" + data.getAgentName());
        				data.setBranchType(data.getAgentType());
        				if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
        					data.setBranchName(data.getAgentName());
        				}
        				break;
                    default:
                        data.setAgentCode(data.getAgentCode());
                        break;
                }
            }
        }
    }

    @Override
    public <T> String searchAdvance(T searchDto,String agentGroup, String keyword, T searchBd1, T searchBd2) {
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam = "";
        String bd1 = "1 = 1";
        String bd2 = "1 = 1";

        try {
            setConditionSearch((CommonSearchWithPagingDto) searchBd1, 1,agentGroup);
            stringJsonParam = mapper.writeValueAsString(searchBd1);
        } catch (JsonProcessingException e) {
            logger.error("getListOfficeDocument", e);
        }
//        try {
//            stringJsonParam = mapper.writeValueAsString(searchDto);
//        } catch (JsonProcessingException e) {
//            logger.error("getListOfficeDocument", e);
//        }
        CommonSearchWithPagingDto commonLv1 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam, agentGroup);

        if(StringUtils.isNotEmpty(commonLv1.getSearch())) {
            logger.info(commonLv1.getSearch());
            String bd11 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.AGENT_CODE,''))");
            String bd12 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.ORG_ID,''))");
            if(("UM".equalsIgnoreCase(agentGroup) || "BM".equalsIgnoreCase(agentGroup))) {
                bd12 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.AGENT_TYPE,''))");
            }
            bd1 = commonLv1.getSearch() + " OR " + bd11 + " OR " + bd12 ;
        }

        try {
            setConditionSearch((CommonSearchWithPagingDto)searchBd2, 2,agentGroup);
            stringJsonParam = mapper.writeValueAsString(searchBd2);
        } catch (JsonProcessingException e) {
            logger.error("getListOfficeDocument", e);
        }
        CommonSearchWithPagingDto commonLv2 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam, agentGroup);
        if(StringUtils.isNotEmpty(commonLv2.getSearch())) {
            String bd21 = commonLv2.getSearch().replace("UPPER(nvl(L.Agent_Name,''))","UPPER(nvl(L.AGENT_CODE,''))");
            String bd22 = commonLv2.getSearch().replace("UPPER(nvl(L.Agent_Name,''))","UPPER(nvl(L.ORG_ID,''))");
            bd2 = commonLv2.getSearch() + " OR " + bd21 + " OR " +bd22;
            if(StringUtils.isNotEmpty(commonLv1.getSearch())) {
                logger.info(commonLv2.getSearch());
                // lay data cap con thuoc cap cha
                bd2 = bd2 +" AND "+commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.Parent_Agent_Name,''))");
            }
        } else {
            if(StringUtils.isNotEmpty(commonLv1.getSearch())) {
                // lay data cap con thuoc cap cha
                String bd21 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.Parent_Agent_Name,''))");
                String bd22 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.PARENT_AGENT_CODE,''))");
                String bd23 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.ORG_PARENT_ID,''))");
                if(("UM".equalsIgnoreCase(agentGroup) || "BM".equalsIgnoreCase(agentGroup))) {
                    bd23 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.AGENT_TYPE,''))");
                }
                bd2 = bd21 + " OR " + bd22 + " OR " + bd23;
            }
        }
        String searchAll = " ";
        if (StringUtils.isNotEmpty(keyword)) {
            searchAll = "AND (L.TREE_LEVEL = 0  " +
                    "OR (((UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"+keyword+"%') OR UPPER(nvl(L.AGENT_CODE,''))  LIKE UPPER(N'%"+keyword+"%') OR UPPER(nvl(L.ORG_ID,''))  LIKE UPPER(N'%"+keyword+"%') )  and L.TREE_LEVEL = 1)" +
                    "OR (UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"+keyword+"%')  AND UPPER(nvl(L.Parent_Agent_Name,''))  LIKE UPPER(N'%"+keyword+"%')  and L.TREE_LEVEL = 2)" +
                    "OR (UPPER(nvl(L.AGENT_CODE,''))  LIKE UPPER(N'%"+keyword+"%')  AND UPPER(nvl(L.PARENT_AGENT_CODE,''))  LIKE UPPER(N'%"+keyword+"%')  and L.TREE_LEVEL = 2)" +
                    "OR (UPPER(nvl(L.ORG_ID,''))  LIKE UPPER(N'%"+keyword+"%')  AND UPPER(nvl(L.ORG_PARENT_ID,''))  LIKE UPPER(N'%"+keyword+"%')  and L.TREE_LEVEL = 2)" +
                    "))";
        }
        if(StringUtils.isNotEmpty(keyword) && ("UM".equalsIgnoreCase(agentGroup) || "BM".equalsIgnoreCase(agentGroup))) {
            return searchAll.replace("UPPER(nvl(L.ORG_ID,''))", "UPPER(nvl(L.AGENT_TYPE,''))") + "AND (L.TREE_LEVEL = 0  or ((" + bd1 + " and L.TREE_LEVEL = 1) or (" + bd2 + " and L.TREE_LEVEL = 2)) )";
        }else {
            return searchAll + "AND (L.TREE_LEVEL = 0  or ((" + bd1 + " and L.TREE_LEVEL = 1) or (" + bd2 + " and L.TREE_LEVEL = 2)) )";
        }
    }

    public String searchAdvance(ReportResultSearchDto searchDto) {
        searchDto.setAgentCode(UserProfileUtils.getFaceMask());
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam = "";
        searchDto.setFunctionCode("GROUP_OH");
        
        ReportResultSearchDto searchBd1 = objectMapper.convertValue(searchDto, ReportResultSearchDto.class);
        ReportResultSearchDto searchBd2 = objectMapper.convertValue(searchDto, ReportResultSearchDto.class);
        String bd1 = "1 = 1";
        String bd2 = "1 = 1";
        
        try {
            setConditionSearch(searchBd1, 1);
            stringJsonParam = mapper.writeValueAsString(searchBd1);
        } catch (JsonProcessingException e) {
            logger.error("getListOfficeDocument", e);
        }
        CommonSearchWithPagingDto commonLv1 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam, searchDto.getAgentGroup());
        if(StringUtils.isNotEmpty(commonLv1.getSearch())) {
        	logger.info(commonLv1.getSearch());
        	String bd11 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.AGENT_CODE,''))");
            String bd12 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.ORG_NAME,''))");

            bd1 = commonLv1.getSearch() + " OR " + bd11 + " OR " + bd12 ;
        }
        try {
            setConditionSearch(searchBd2, 2);
            stringJsonParam = mapper.writeValueAsString(searchBd2);
        } catch (JsonProcessingException e) {
            logger.error("getListOfficeDocument", e);
        }
        
        CommonSearchWithPagingDto commonLv2 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam, searchDto.getAgentGroup());
        if(StringUtils.isNotEmpty(commonLv2.getSearch())) {

           String bd21 = commonLv2.getSearch().replace("UPPER(nvl(L.Agent_Name,''))","UPPER(nvl(L.AGENT_CODE,''))");
           String bd22 = commonLv2.getSearch().replace("UPPER(nvl(L.Agent_Name,''))","UPPER(nvl(L.AGENT_TYPE,''))");

            bd2 = commonLv2.getSearch() + " OR " + bd21 + " OR " +bd22;

            if(StringUtils.isNotEmpty(commonLv1.getSearch())) {
            	logger.info(commonLv2.getSearch());
                // lay data cap con thuoc cap cha
                bd2 = bd2 +" AND "+commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.Parent_Agent_Name,''))");
            }
        } else {
            if(StringUtils.isNotEmpty(commonLv1.getSearch())) {
                // lay data cap con thuoc cap cha
                String bd21 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.Parent_Agent_Name,''))");
                String bd22 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.PARENT_AGENT_CODE,''))");
                String bd23 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.ORG_PARENT_NAME,''))");
                bd2 = bd21 + " OR " + bd22 + " OR " + bd23;
                //bd2 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.Parent_Agent_Name,''))");
            }
        }
        //////////////////////////////////////////////////////
        String searchAll = " ";
            if (StringUtils.isNotEmpty(searchDto.getKeyword())) {
           // searchAll = " AND (((UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  and L.TREE_LEVEL = 0) or (UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  AND UPPER(nvl(L.Parent_Agent_Name,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  and L.TREE_LEVEL = 1)) )";
            searchAll = "AND ((((UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%') OR UPPER(nvl(L.AGENT_CODE,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%') OR UPPER(nvl(L.ORG_NAME,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%') )  and L.TREE_LEVEL = 0)" +
                    "OR (UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  AND UPPER(nvl(L.Parent_Agent_Name,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  and L.TREE_LEVEL = 1)" +
                    "OR (UPPER(nvl(L.AGENT_CODE,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  AND UPPER(nvl(L.PARENT_AGENT_CODE,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  and L.TREE_LEVEL = 1)" +
                    "OR (UPPER(nvl(L.ORG_NAME,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  AND UPPER(nvl(L.ORG_PARENT_NAME,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  and L.TREE_LEVEL = 1)" +
                    "))";
        }
        String search = searchAll +"AND ((("+ bd1 +" and L.TREE_LEVEL = 0) or ("+bd2+" and L.TREE_LEVEL = 1)) )";
        return search;
    }

	private ReportResultViewGADTabPremiumDto sumByObject(List<ReportResultViewGADTabPremiumDto> datas, int level) {
    	ReportResultViewGADTabPremiumDto dto = new ReportResultViewGADTabPremiumDto();
    	if(CollectionUtils.isEmpty(datas)) {
    		return dto;
    	}
    	if(level == 0) {
    		dto = datas.get(0);
    	} else if(level == 1) {
    		dto.setAgentLevel(datas.get(0).getAgentLevel() - 1);
    		dto.setTreeLevel(1);
    		dto.setChildGroup("GA");
    		dto.setParentAgentCode(datas.get(0).getParentGroup());
    		dto.setOrgId(datas.get(0).getOrgParentId());
    		dto.setOrgName(datas.get(0).getOrgParentName());
    		dto.setOrgParentId(datas.get(0).getOrgParentId());
    		dto.setOrgParentName(datas.get(0).getOrgParentName());
    		dto.setAgentCode(datas.get(0).getParentAgentCode());
    		dto.setAgentName(datas.get(0).getParentAgentName());
    		dto.setAgentType(datas.get(0).getParentAgentType());
    		dto.setParentAgentCode(datas.get(0).getParentAgentCode());
    		dto.setParentAgentName(datas.get(0).getParentAgentName());
    		dto.setParentAgentType(datas.get(0).getParentAgentType());
    	}
    	dto.setPolicyCountReceived(datas.stream().filter(e->e.getPolicyCountReceived()!=null).mapToInt(x->x.getPolicyCountReceived()).sum());
    	dto.setFypReceived(datas.stream().filter(x -> !isNullOrZero(x.getFypReceived())).map(ReportResultViewGADTabPremiumDto :: getFypReceived).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setPolicyCountIssued(datas.stream().filter(e->e.getPolicyCountIssued()!=null).mapToInt(x->x.getPolicyCountIssued()).sum());
    	dto.setFypIssued(datas.stream().filter(x -> !isNullOrZero(x.getFypIssued())).map(ReportResultViewGADTabPremiumDto :: getFypIssued).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setPolicyCount(datas.stream().filter(e->e.getPolicyCount()!=null).mapToInt(x->x.getPolicyCount()).sum());
    	dto.setFyp(datas.stream().filter(x -> !isNullOrZero(x.getFyp())).map(ReportResultViewGADTabPremiumDto :: getFyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setPolicyCountCancel(datas.stream().filter(e->e.getPolicyCountCancel()!=null).mapToInt(x->x.getPolicyCountCancel()).sum());
    	dto.setFypCancel(datas.stream().filter(x -> !isNullOrZero(x.getFypCancel())).map(ReportResultViewGADTabPremiumDto :: getFypCancel).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setRyp(datas.stream().filter(x -> !isNullOrZero(x.getRyp())).map(ReportResultViewGADTabPremiumDto :: getRyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setRfyp(datas.stream().filter(x -> !isNullOrZero(x.getRfyp())).map(ReportResultViewGADTabPremiumDto :: getRfyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setK2App(datas.stream().filter(x -> !isNullOrZero(x.getK2App())).map(ReportResultViewGADTabPremiumDto :: getK2App).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setK2Epp(datas.stream().filter(x -> !isNullOrZero(x.getK2Epp())).map(ReportResultViewGADTabPremiumDto :: getK2Epp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setK2plusApp(datas.stream().filter(x -> !isNullOrZero(x.getK2plusApp())).map(ReportResultViewGADTabPremiumDto :: getK2plusApp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setK2plusEpp(datas.stream().filter(x -> !isNullOrZero(x.getK2plusEpp())).map(ReportResultViewGADTabPremiumDto :: getK2plusEpp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
//    	dto.setK2(datas.stream().map(ReportResultViewGADTabPremiumDto :: getK2).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
//    	dto.setK2Plus(datas.stream().map(ReportResultViewGADTabPremiumDto :: getK2Plus).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setLastPolicyCountIssued(datas.stream().filter(e->e.getLastPolicyCountIssued()!=null).mapToInt(x->x.getLastPolicyCountIssued()).sum());
    	dto.setLastFypIssued(datas.stream().filter(x -> !isNullOrZero(x.getLastFypIssued())).map(ReportResultViewGADTabPremiumDto :: getLastFypIssued).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setLastPolicyCountReceived(datas.stream().filter(e->e.getLastPolicyCountReceived()!=null).mapToInt(x->x.getLastPolicyCountReceived()).sum());
    	dto.setLastFypReceived(datas.stream().filter(x -> !isNullOrZero(x.getLastFypReceived())).map(ReportResultViewGADTabPremiumDto :: getLastFypReceived).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setLastPolicyCount(datas.stream().filter(e->e.getLastPolicyCount()!=null).mapToInt(x->x.getLastPolicyCount()).sum());
    	dto.setLastFyp(datas.stream().filter(x -> !isNullOrZero(x.getLastFyp())).map(ReportResultViewGADTabPremiumDto :: getLastFyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setLastRyp(datas.stream().filter(x -> !isNullOrZero(x.getLastRyp())).map(ReportResultViewGADTabPremiumDto :: getLastRyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setLastRfyp(datas.stream().filter(x -> !isNullOrZero(x.getLastRfyp())).map(ReportResultViewGADTabPremiumDto :: getLastRfyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setCountBmTypeAgentcode(datas.stream().filter(e->e.getCountBmTypeAgentcode()!=null).mapToInt(x->x.getCountBmTypeAgentcode()).sum());
    	dto.setCountUmTypeAgentcode(datas.stream().filter(e->e.getCountUmTypeAgentcode()!=null).mapToInt(x->x.getCountUmTypeAgentcode()).sum());
    	dto.setCountPumTypeAgentcode(datas.stream().filter(e->e.getCountPumTypeAgentcode()!=null).mapToInt(x->x.getCountPumTypeAgentcode()).sum());
    	dto.setCountFcTypeAgentcode(datas.stream().filter(e->e.getCountFcTypeAgentcode()!=null).mapToInt(x->x.getCountFcTypeAgentcode()).sum());
    	dto.setCountSaTypeAgentcode(datas.stream().filter(e->e.getCountSaTypeAgentcode()!=null).mapToInt(x->x.getCountSaTypeAgentcode()).sum());

    	dto.setCountBmAgentcode(datas.stream().filter(e->e.getCountBmAgentcode()!=null).mapToInt(x->x.getCountBmAgentcode()).sum());
    	dto.setCountUmAgentcode(datas.stream().filter(e->e.getCountUmAgentcode()!=null).mapToInt(x->x.getCountUmAgentcode()).sum());
    	dto.setCountPumAgentcode(datas.stream().filter(e->e.getCountPumAgentcode()!=null).mapToInt(x->x.getCountPumAgentcode()).sum());
    	dto.setCountFcAgentcode(datas.stream().filter(e->e.getCountFcAgentcode()!=null).mapToInt(x->x.getCountFcAgentcode()).sum());
    	dto.setCountSaAgentcode(datas.stream().filter(e->e.getCountSaAgentcode()!=null).mapToInt(x->x.getCountSaAgentcode()).sum());
    	
    	dto.setCountNewRecruitment(datas.stream().filter(e->e.getCountNewRecruitment()!=null).mapToInt(x->x.getCountNewRecruitment()).sum());
    	dto.setCountReinstate(datas.stream().filter(e->e.getCountReinstate()!=null).mapToInt(x->x.getCountReinstate()).sum());
    	dto.setCountActive(datas.stream().filter(e->e.getCountActive()!=null).mapToInt(x->x.getCountActive()).sum());
    	dto.setCountNewRecruitmentActive(datas.stream().filter(e->e.getCountNewRecruitmentActive()!=null).mapToInt(x->x.getCountNewRecruitmentActive()).sum());
    	dto.setCountSchemeFc(datas.stream().filter(e->e.getCountSchemeFc()!=null).mapToInt(x->x.getCountSchemeFc()).sum());
    	dto.setCountPfcFc(datas.stream().filter(e->e.getCountPfcFc()!=null).mapToInt(x->x.getCountPfcFc()).sum());
    	dto.setSumFyp(datas.stream().filter(x -> !isNullOrZero(x.getSumFyp())).map(ReportResultViewGADTabPremiumDto :: getSumFyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setSumRyp(datas.stream().filter(x -> !isNullOrZero(x.getSumRyp())).map(ReportResultViewGADTabPremiumDto :: getSumRyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
    	dto.setLastDate(datas.stream().filter(e->e.getLastDate()!=null).mapToInt(x->x.getLastDate()).sum());
    	dto.setLastCountNewRecruitment(datas.stream().filter(e->e.getLastCountNewRecruitment()!=null).mapToInt(x->x.getLastCountNewRecruitment()).sum());
    	dto.setLastCountReinstate(datas.stream().filter(e->e.getLastCountReinstate()!=null).mapToInt(x->x.getLastCountReinstate()).sum());
    	dto.setLastCountActive(datas.stream().filter(e->e.getLastCountActive()!=null).mapToInt(x->x.getLastCountActive()).sum());
    	dto.setLastCountNewRecruitmentActive(datas.stream().filter(e->e.getLastCountNewRecruitmentActive()!=null).mapToInt(x->x.getLastCountNewRecruitmentActive()).sum());
    	return dto;
    }
	 public static boolean isNullOrZero(BigDecimal number) {
	        boolean isBigDecimalValueNullOrZero = false;
	        if (number == null)
	            isBigDecimalValueNullOrZero = true;
	        else if (number != null && number.compareTo(BigDecimal.ZERO) == 0)
	            isBigDecimalValueNullOrZero = true;

	        return isBigDecimalValueNullOrZero;
	    }
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity doExportExcelHeaderWithColFormatRestUpService(XSSFWorkbook xssfWorkbook, Integer sheetIndex, Locale locale,
                                                                        List<ReportResultViewGADTabPremiumDto> listData, Class<ReportResultViewGADTabPremiumDto> objDto, List<ItemColsExcelDto> cols, String datePattern, String cellReference,
                                                                        Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
                                                                        Map<String, Object> mapColDefaultValue, XSSFColor colorToTal, boolean isAllBorder,
                                                                        String templateName, boolean exportFile, String path) throws Exception {
        // create sheet of file excel
        XSSFSheet sxssfSheet = null;
        if (sheetIndex == null) {
            sxssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            sxssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
        }

        if (sxssfSheet == null) {
            throw new Exception("Cannot find Sheet!!!");
        }
        
        CellReference landMark = new CellReference(cellReference);
        int startRow = landMark.getRow();

        // cellStyleDto
        CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Times New Roman", isAllBorder, datePattern);

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        Field[] fields = populateFields(objDto);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }

        if (listData != null) {
            if (listData.size() > 0) {
                int dataSize = listData.size();
                for (int i = 0; i < listData.size(); i++) {
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDto, dataSize, false);
                    }
                    startRow += 1;
                }
            }
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
        sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
        
        if (exportFile) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());
            xssfWorkbook.write(out);
            //update to service
            String pathFile = Paths.get(path,CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
            		templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL).toString();
            
            File file = new File(pathFile);
            try (OutputStream os = new FileOutputStream(file)) {
                xssfWorkbook.write(os);
            }
            
            String pathOut = (Paths.get(CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN
            		, templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL).toString()).replace("\\", "/");
            
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            HttpHeaders headers = new HttpHeaders();
            headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_"
                    + currentDate + CommonConstant.TYPE_EXCEL + "\""+";path="+pathOut);
            
            headers.add("Access-Control-Expose-Headers", CommonConstant.CONTENT_DISPOSITION);
            
             return ResponseEntity
                        .ok()
                        .eTag(pathOut)
                        .headers(headers)
                        .contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
                        .body(new InputStreamResource(in));
        }
        return null;
    }
	private Field[] populateFields(Class<?> cls) {
		Field[] fieldsSuper = cls.getSuperclass().getDeclaredFields();
		Field[] fieldsChild = cls.getDeclaredFields();
		int superLength = fieldsSuper.length;
		int childLength = fieldsChild.length;
		Field[] fields = new Field[superLength + childLength];
		System.arraycopy(fieldsSuper, 0, fields, 0, superLength);
		System.arraycopy(fieldsChild, 0, fields, superLength, childLength);
		return fields;
	}
	public void fillDataForCell(XSSFSheet sxssfSheet, List<ReportResultViewGADTabPremiumDto> listData, Class<ReportResultViewGADTabPremiumDto> objDto, List<ItemColsExcelDto> cols,
            Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
            Map<String, Object> mapColDefaultValue, Map<String, Field> mapFields, int rowIndex, int dataIndex,
            CellStyleDto cellStyleDto, int dataSize, boolean fillColor)
            throws IllegalArgumentException, IllegalAccessException {

        XSSFRow row = sxssfSheet.createRow(rowIndex);

        ReportResultViewGADTabPremiumDto excelDto = listData.get(dataIndex);
        if (excelDto != null) {
            // set value to map
            Field[] headerFields = populateFields(objDto);
            Map<String, Object> mapValueFields = new HashMap<String, Object>();
            for (Field field : headerFields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
            }
            // begin fill to cell
            for (ItemColsExcelDto col : cols) {

                // data type of field
                XSSFCell cell = row.createCell(col.getColIndex());
                // col.getColName());
                Field field = mapFields.get(col.getColName().toUpperCase());
                String typeFields = field.getType().getSimpleName().toUpperCase();
                DataType dataType = DataType.valueOf(typeFields);

                String formatType = null;
                if (null != mapColFormat && mapColFormat.size() != 0) {
                    formatType = mapColFormat.get(col.getColName());
                }
                
                // col value
                Object colValue = mapValueFields.get(col.getColName().toUpperCase());

                if (mapColDefaultValue != null && mapColDefaultValue.containsKey(col.getColName())) {
                    if (null == mapColDefaultValue.get(col.getColName())
                            || EMP_STRING.equals(mapColDefaultValue.get(col.getColName()))) {
                        cell.setCellValue(EMP_STRING);
                    } else {
                        String val = String.valueOf(mapColDefaultValue.get(col.getColName()));
                        cell.setCellValue(val);
                    }
                }
                
                switch (dataType) {
                case LONG:
                    if (colValue != null) {
                        Long valueOfLong = Long.parseLong(colValue.toString());
                        cell.setCellValue(valueOfLong);
                        cell.setCellType(CellType.NUMERIC);
                    }
                    
                    cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                case INTEGER:
                	if (colValue != null) {
                        BigDecimal valueBigdecimal = new BigDecimal( (Integer) colValue);
//                        if((valueBigdecimal.intValue() == 0) || StringUtils.isBlank(colValue.toString())) {
//                            colValue = new BigDecimal(0).intValue();
//                            cell.setCellValue(Integer.parseInt(colValue.toString()));
//                            cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal3());
//                            break;
//                        }
                        if (valueBigdecimal != null) {
                            cell.setCellValue(valueBigdecimal.doubleValue());
                            if (cellStyleDto.getIsFormatFinance() == 0) { // Normal
                                // format
                                // number
                                if (valueBigdecimal.doubleValue() % 1 > 0) {
                                    // format "#,##0.00"
                                    cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal1());
                                } else {
                                    // format "#,##0"
                                    cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal2());
                                }
                            } else { // Format number with Finance
                                     // money
                                cell.setCellStyle(cellStyleDto.getCellStyleFinanceFormat());
                            }

                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());

                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }
                    break;
                case INT:
                    if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                        if (colValue != null && !fillColor) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);
                        }
                        
                        cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    } else {
                        if (colValue != null) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);
                        }
                        
                        cell.setCellStyle(cellStyleDto.getCellStyleRight());
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }

                    break;
                case DOUBLE:
                    if (colValue != null) {
                        Double valueOfDouble = Double.parseDouble(colValue.toString());

                        if (valueOfDouble % 1 > 0) {
                            // format "0.00"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1WithPercent());
                            } else if (null != formatType
                                    && formatType.equalsIgnoreCase(CommonConstant.DOUBLE_SHOW_ALL)) {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble3());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1());
                            }
                            
                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        } else {
                            // format "0"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2WithPercent());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2());
                            }
                            
                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                        
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }

                    break;
                case BIGDECIMAL:
                    if (colValue != null) {
                        BigDecimal valueBigdecimal = (BigDecimal) colValue;
                        //update 0 -> -
//                        if((valueBigdecimal.intValue() == 0) || StringUtils.isBlank(colValue.toString())) {
//                            colValue = new BigDecimal(0).intValue();
//                            cell.setCellValue(Integer.parseInt(colValue.toString()));
//                            cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal3());
//                            break;
//                        }
                        if (valueBigdecimal != null) {
                            cell.setCellValue(valueBigdecimal.doubleValue());
                            if (cellStyleDto.getIsFormatFinance() == 0) { // Normal
                                // format
                                // number
                                if (valueBigdecimal.doubleValue() % 1 > 0) {
                                    // format "#,##0.00"
                                    cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal1());
                                } else {
                                    // format "#,##0"
                                    cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal2());
                                }
                            } else { // Format number with Finance
                                     // money
                                cell.setCellStyle(cellStyleDto.getCellStyleFinanceFormat());
                            }
                            
                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                        
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }

                    break;
                case DATE:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());

                    // short oldFormat = cellStyleDto.getCellStyleDateCenter().getDataFormat();
                    
                    // 2020 11 25 LocLT format cell date
                    try {
                    	if (formatType != null) {
                    		
//                        	short format = cellStyleDto.getSxssfWorkbook().getCreationHelper().createDataFormat().getFormat(formatType);
//                        	cellStyleDto.getCellStyleDateCenter().setDataFormat(format);
                        	
                        	SXSSFWorkbook wb = cellStyleDto.getSxssfWorkbook();
                        	CellStyle cellStyle = wb.createCellStyle();
                        	cellStyle.cloneStyleFrom(cellStyleDto.getCellStyleDateCenter());
                        	CreationHelper createHelper = wb.getCreationHelper();
                        	
                        	short fmt = createHelper.createDataFormat().getFormat(formatType);
                        	cellStyle.setDataFormat(fmt);
                        	
                        	cell.setCellStyle(cellStyle);
                        }
					} catch (Exception e) {
						// TODO: handle exception
					}
                    // 2020 11 25 LocLT format cell date

                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    
                    // cellStyleDto.getCellStyleDateCenter().setDataFormat(oldFormat);
                    
                    break;
                case TIMESTAMP:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                case STRING:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                        cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                        if (col.getColName().concat("%") != null) {
                            cell.setCellStyle(cellStyleDto.getCellStyleRight());
                        }
                        if (col.getColName().equals("MESSAGEERROR")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleMessageError());
                        }
                        
                        if (col.getColName().equals("MESSAGEWARNING")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleMessageWarning());
                        }
                        
                        if (col.getColName().contains("NOTE") || col.getColName().contains("DESCRIPTION")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleDescription());
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                    }
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    
                    break;
                case BOOLEAN:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                default:
                    break;
                }
            } // END FOR 2
        }
    }
}
