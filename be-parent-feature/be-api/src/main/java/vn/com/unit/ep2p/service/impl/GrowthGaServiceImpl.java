package vn.com.unit.ep2p.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
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
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.ga.dto.GrowthGaDto;
import vn.com.unit.cms.core.module.ga.dto.param.GrowthParamGa;
import vn.com.unit.cms.core.module.ga.dto.search.GrowthSearchGa;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.enumdef.GrowthGaExportEnum;
import vn.com.unit.ep2p.service.GrowthGaService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

import javax.servlet.ServletContext;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class GrowthGaServiceImpl implements GrowthGaService {

    @Autowired
    ParseJsonToParamSearchService parseJsonToParamSearchService;

    @Autowired
    @Qualifier("sqlManageDb2Service")
    private SqlManagerDb2Service sqlManagerDb2Service;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ServletContext servletContext;

    private static final Logger logger = LoggerFactory.getLogger(GrowthGaServiceImpl.class);


    private static final String STORE_GROWTH_GA = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_MANPOWER_PRIMEUM";
    private static final String STORE_GROWTH_GA_BONUS = "";


    @Override
    public CmsCommonPagination<GrowthGaDto> getListGrowthGa(GrowthSearchGa searchDto) {
        GrowthParamGa growthParamGa = new GrowthParamGa();

        searchDto.setFunctionCode("GROWTH_GA");
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam = "";
        try {
            stringJsonParam = mapper.writeValueAsString(searchDto);
        } catch (JsonProcessingException e) {
            logger.error("Exception ", e);
        }
        CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());

        growthParamGa.agentCode = searchDto.getAgentCode();
        growthParamGa.orgCode = searchDto.getOrgCode();
        growthParamGa.agentGroup = searchDto.getAgentGroup();
        if(StringUtils.endsWithIgnoreCase(searchDto.getType(),"MTD")){
            growthParamGa.dateKey = searchDto.getYear()+searchDto.getMonth();
        }else if(StringUtils.endsWithIgnoreCase(searchDto.getType(),"QTD")){
            growthParamGa.dateKey = searchDto.getMonth()+searchDto.getYear();
        }
        growthParamGa.dataType = searchDto.getType();
        growthParamGa.page = common.getPage();
        growthParamGa.pageSize = common.getPageSize();
        growthParamGa.search = common.getSearch();
        sqlManagerDb2Service.call(STORE_GROWTH_GA, growthParamGa);
        List<GrowthGaDto> lstData = growthParamGa.lstData;

        CmsCommonPagination<GrowthGaDto> rs = new CmsCommonPagination<>();
        rs.setTotalData(growthParamGa.totalRows);
        rs.setData(lstData);
        return rs;
    }


    @Override
	public CmsCommonPagination<GrowthGaDto> getListGrowthGaBonus(GrowthSearchGa searchDto) {
        GrowthParamGa growthParamGa = new GrowthParamGa();

        searchDto.setFunctionCode("GROWTH_GA_BONUS_" + searchDto.getType());
       
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam = "";
        try {
            stringJsonParam = mapper.writeValueAsString(searchDto);
        } catch (JsonProcessingException e) {
            logger.error("Exception ", e);
        }
        CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());

        growthParamGa.agentCode = searchDto.getAgentCode();
        growthParamGa.orgCode = searchDto.getOrgCode();
        growthParamGa.agentGroup = searchDto.getAgentGroup();
        if(StringUtils.endsWithIgnoreCase(searchDto.getType(),"MTD")){
            growthParamGa.dateKey = searchDto.getMonth()+searchDto.getYear();
        }else if(StringUtils.endsWithIgnoreCase(searchDto.getType(),"QTD")){
            growthParamGa.dateKey = searchDto.getMonth()+searchDto.getYear();
        }
        growthParamGa.dataType = searchDto.getType();
        growthParamGa.page = common.getPage();
        growthParamGa.pageSize = common.getPageSize();
        growthParamGa.search = common.getSearch();
        sqlManagerDb2Service.call(STORE_GROWTH_GA_BONUS, growthParamGa);
        List<GrowthGaDto> lstData = growthParamGa.lstData;
        CmsCommonPagination<GrowthGaDto> rs = new CmsCommonPagination<>();
        rs.setTotalData(growthParamGa.totalRows);
        rs.setData(lstData);
        return rs;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResponseEntity exporttListGrowthGa(GrowthSearchGa searchDto,Locale locale) {
        ResponseEntity res = null;
        try {
            searchDto.setPage(0);
            searchDto.setSize(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<GrowthGaDto> resObj = getListGrowthGa(searchDto);
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            String templateName =null ;
            datePattern = "dd/MM/yyyy";
            if (StringUtils.endsWithIgnoreCase(searchDto.getType(), "1")) {
             templateName = "GrowthGaMonths.xlsx";
            }
            if (StringUtils.endsWithIgnoreCase(searchDto.getType(), "2")) {
             templateName = "GrowthGaQuarter.xlsx";
            }

            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A6";
            List<GrowthGaDto> lstdata = resObj.getData();

            List<ItemColsExcelDto> cols = new ArrayList<>();

            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(GrowthGaExportEnum.class, cols);

            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;

                //insert title
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                XSSFRow row = null;
                XSSFRow row2 = null;

                int Index1 = 0;
                int Index2 = 1;

                row = xssfSheet.getRow(1);

                XSSFCell cellIndex1 = row.getCell(Index1);
                if (cellIndex1 == null)
                    cellIndex1 = row.createCell(Index1);
                if (StringUtils.endsWithIgnoreCase(searchDto.getType(), "2")) {
                    cellIndex1.setCellValue("Báo cáo tăng tưởng Ga tháng" + searchDto.getMonth() + "/" + searchDto.getYear());

                    XSSFCell cellIndex2 = row.getCell(Index2);
                    if (cellIndex2 == null)
                        cellIndex2 = row.createCell(Index2);
                    cellIndex2.setCellValue("Cập nhật đến ngày " + new Date());


                }
                //END
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
                        GrowthGaDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##ExportList##", e);
				throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            logger.error("exportListData: ", e);
        }
        return res;
    }

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    @Override
    public ResponseEntity exporttListGrowthGaBonus(GrowthSearchGa searchDto, Locale locale) {
        ResponseEntity res = null;
        try {
            searchDto.setPage(0);
            searchDto.setSize(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<GrowthGaDto> resObj = getListGrowthGaBonus(searchDto);

            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            
            datePattern = "dd/MM/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
            
            String  templateName = "GrowthGaBonus.xlsx";
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "B9";
            List<GrowthGaDto> lstdata = resObj.getData();
            lstdata.get(0);
            GrowthGaDto data =  lstdata.get(0);

            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(GrowthGaExportEnum.class, cols);
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;
                //insert title
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                
                int Index1 = 1;
                XSSFRow row = xssfSheet.getRow(1);
                
                XSSFCell cellIndex1 = row.getCell(Index1);
                    cellIndex1 = row.createCell(Index1);
                    cellIndex1.setCellValue("THƯỞNG TĂNG TRƯỞNG QUÝ " + searchDto.getMonth() + "/" + searchDto.getYear());

                XSSFRow row2  = xssfSheet.getRow(3);
                XSSFCell cellIndex2 = row2.getCell(2);
                	cellIndex2 = row2.createCell(2);
                    cellIndex2.setCellValue(data.getGaName());

                XSSFRow row3  = xssfSheet.getRow(4);
                XSSFCell cellIndex3 = row3.getCell(2);
                	cellIndex3 = row3.createCell(2);
                    cellIndex3.setCellValue(data.getGadName());

                XSSFRow row4  = xssfSheet.getRow(5);
                XSSFCell cellIndex4 = row4.getCell(2);
                	cellIndex4 = row4.createCell(2);
                    cellIndex4.setCellValue(formatter.format(new Date()));

                XSSFRow row5  = xssfSheet.getRow(13);
                XSSFCell cellIndex5 = row5.getCell(2);
                	cellIndex5 = row5.createCell(2);
                    cellIndex5.setCellValue(data.getRatio15());

                XSSFRow row6  = xssfSheet.getRow(13);
                XSSFCell cellIndex6 = row6.getCell(3);
                cellIndex6 = row6.createCell(3);
                cellIndex6.setCellValue(data.getRatio30());
                        
                XSSFRow row7  = xssfSheet.getRow(13);
                XSSFCell cellIndex7 = row7.getCell(4);
                cellIndex7 = row7.createCell(4);
                cellIndex7.setCellValue(data.getRatio45());
                            
                XSSFRow row8  = xssfSheet.getRow(13);
                XSSFCell cellIndex8 = row8.getCell(5);
                cellIndex8 = row8.createCell(5);
                cellIndex8.setCellValue(data.getRatio60());
                    
                //END
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
                        GrowthGaDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##ExportList##", e);
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            logger.error("exportListData: ", e);
        }
        return res;
    }



	


}
