package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.emulate.dto.CheckAgentChildParam;
import vn.com.unit.cms.core.module.emulate.dto.EmulatePersonalParam;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulationChallengeSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.resp.EmulateResp;
import vn.com.unit.cms.core.module.emulate.repository.EmulateRepository;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.enumdef.ChellengeResultPersonalEnum;
import vn.com.unit.ep2p.enumdef.EmulateChallengeGroupEnum;
import vn.com.unit.ep2p.enumdef.EmulateChallengePersonalEnum;
import vn.com.unit.ep2p.enumdef.EmulateResultPersonalEnum;
import vn.com.unit.ep2p.service.ApiEmulateService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.ep2p.utils.RestUtil;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiEmulateServiceImpl implements ApiEmulateService {
    @Autowired
    private EmulateRepository emulateRepository;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    @Qualifier("sqlManageDb2Service")
    private SqlManagerDb2Service sqlManagerDb2Service;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String EMULATE_AND_CHALLENGE_PERSONAL = "RPT_ODS.DS_SP_GET_LIST_EMULATION_OTHER_SYSTEM";
    private static final String EMULATE_AND_CHALLENGE_RESULT_PERSONAL = "RPT_ODS.DS_SP_GET_EMULATION_CHALLENGES_OTHER_SYSTEM_RESULT";
    private static final String EMULATE_AND_CHALLENGE_RESULT_SUMMARY = "RPT_ODS.DS_SP_GET_EMULATION_OTHER_SYSTEM_RESULT_SUMMARY";
    private static final String DS_SP_GET_ODS_AGENT_DETAIL = "RPT_ODS.DS_SP_GET_ODS_AGENT_DETAIL_INFORCE";

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<EmulateResp> getListEmulateInMonth(EmulateSearchDto searchDto, Pageable pageable, Integer modeView) {
        return emulateRepository.getListEmulateInMonth(searchDto, pageable, modeView).getContent();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public int countListEmulateInMonth(EmulateSearchDto searchDto, Integer modeView) {
        return emulateRepository.countListEmulateInMonth(searchDto);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public EmulateResp getHotEmulateInMonth(EmulateSearchDto searchDto) {
        return emulateRepository.getHotEmulateInMonth(searchDto);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<EmulateSearchDto> findByContestType() {
        return emulateRepository.findByContestType();
    }
    @Autowired
    ParseJsonToParamSearchService parseJsonToParamSearchService;
    
    @Override
    public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengePersonal(
            EmulationChallengeSearchDto searchDto) {
        ObjectDataRes<EmulateResultSearchResultDto> resObj = new ObjectDataRes<EmulateResultSearchResultDto>();
        EmulatePersonalParam param = new EmulatePersonalParam();
        List<EmulateResultSearchResultDto> datas = new ArrayList<>();
        try {
            searchDto.setFunctionCode("EMULATION_PROGRAM_PERSONAL");
            commom(searchDto, param);
            sqlManagerDb2Service.call(EMULATE_AND_CHALLENGE_PERSONAL, param);
            datas=param.data;
            for (EmulateResultSearchResultDto dto : datas) {
                if(CommonStringUtil.isNotEmpty(dto.getContestPhysicalFile())) {
                    dto.setContestPhysicalFile(RestUtil.replaceImageUrl(dto.getContestPhysicalFile().replace("'\'", "//"), null));
                }
            }
            resObj = new ObjectDataRes<>(param.TotalRows, datas);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return resObj;
    }

    @Override
    public ObjectDataRes<EmulateResultSearchResultDto> getListEmulateAndChallengeResultPersonal(
            EmulationChallengeSearchDto searchDto) {
        EmulatePersonalParam param = new EmulatePersonalParam();
        ObjectDataRes<EmulateResultSearchResultDto> resObj = new ObjectDataRes<EmulateResultSearchResultDto>();
        List<EmulateResultSearchResultDto> datas = new ArrayList<>();
        try {
            if(searchDto.getIsChallenge()==1) {
                searchDto.setFunctionCode("CHALLENGE_PROGRAM_PERSONAL_RESULT");
            }
            else {
                searchDto.setFunctionCode("EMULATION_PROGRAM_PERSONAL_RESULT");
            }
            commom(searchDto, param);
            sqlManagerDb2Service.call(EMULATE_AND_CHALLENGE_RESULT_PERSONAL, param);
            datas=param.data;
            for (EmulateResultSearchResultDto dto : datas) {
                if(CommonStringUtil.isNotEmpty(dto.getContestPhysicalFile())) {
                    dto.setContestPhysicalFile(RestUtil.replaceImageUrl(dto.getContestPhysicalFile().replace("'\'", "//"), null));
                }
            }
            resObj = new ObjectDataRes<>(param.TotalRows, datas);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return resObj;
    }
    
    //export per list
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResponseEntity exportEmulateAndChellengePersonal(EmulationChallengeSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            List<EmulateResultSearchResultDto> lstdata = getEmulateAndChallengePersonal(searchDto).getDatas();
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "Emulate_Challenge_Personal.xlsx";
            String titleName="";
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            List<ItemColsExcelDto> cols = new ArrayList<>();
            if(searchDto.getIsChallenge()!=null && searchDto.getIsChallenge()==1) {
                templateName="Thu_thach_thuc.xlsx";
                titleName="THƯ THÁCH THỨC ";
                }
            else {
                templateName="Chuong_trinh_thi_dua.xlsx";
                titleName="DANH SÁCH CHƯƠNG TRÌNH THI ĐUA ";
            }
            if(searchDto.getType() !=null) {
                if(searchDto.getType()==1) {
                    templateName += "_dai_han_ca_nhan.xlsx";
                    titleName+="DÀI HẠN CÁ NHÂN";
                }
                else {
                    templateName += "_ngan_han_ca_nhan.xlsx";
                    titleName+="NGẮN HẠN CÁ NHÂN";
                }
            }
            ImportExcelUtil.setListColumnExcel(EmulateChallengePersonalEnum.class, cols);
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                List<String> datas = new ArrayList<String>();
                datas.add("Ngày cập nhật: "+DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
                setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, null, titleName, "A3", datas);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                        EmulateResultSearchResultDto.class, cols, datePattern, "A6", null, null,
                        null, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##doExport##", e);
            }
        } catch (Exception e) {
            logger.error("##exportEmulateAndChellengePersonal##", e);
        }
        return res;
    }
    
    //export per result
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResponseEntity exportEmulateAndChellengePersonalResult(EmulationChallengeSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            List<EmulateResultSearchResultDto> lstdata = getListEmulateAndChallengeResultPersonal(searchDto).getDatas();
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "";
            String titleName="";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            if(searchDto.getIsChallenge()!=null && searchDto.getIsChallenge()==1) {
                templateName="Ket_qua_thu_thach_thuc.xlsx";
                titleName="KẾT QUẢ THƯ THÁCH THỨC ";
                ImportExcelUtil.setListColumnExcel(ChellengeResultPersonalEnum.class, cols);
                }
            else {
                templateName="Ket_qua_thi_dua.xlsx";
                titleName="KẾT QUẢ THI ĐUA ";
                ImportExcelUtil.setListColumnExcel(EmulateResultPersonalEnum.class, cols);
            }
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            if(searchDto.getType()!=null && searchDto.getType()==1) {
                titleName+="DÀI HẠN CÁ NHÂN";
                templateName += "_dai_han_ca_nhan.xlsx";
            }
            else {
                titleName+="NGẮN HẠN CÁ NHÂN";
                templateName += "_ngan_han_ca_nhan.xlsx";
            }
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;
                List<String> datas = new ArrayList<String>();
                datas.add("Ngày cập nhật: "+DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
                setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, null, titleName, "A3", datas);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                        EmulateResultSearchResultDto.class, cols, datePattern, "A6", null, mapColStyle,
                        null, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##doExport##", e);
            }
        } catch (Exception e) {
            logger.error("##exportLis##", e);
        }
        return res;
    }

    // II. nhom phong
    @Override
    public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengeGroup(
            EmulationChallengeSearchDto searchDto) {
        EmulatePersonalParam param = new EmulatePersonalParam();
        ObjectDataRes<EmulateResultSearchResultDto> resObj = new ObjectDataRes<EmulateResultSearchResultDto>();
        List<EmulateResultSearchResultDto> datas = new ArrayList<>();
        try {
            if(searchDto.getObjectType()==1) {
                searchDto.setFunctionCode("LETTER_CHALLENGE_OFFICE_GA");
            }
            else {
                searchDto.setFunctionCode("EMULATION_PROGRAM_GROUP");
            }
            commom(searchDto, param);
            sqlManagerDb2Service.call(EMULATE_AND_CHALLENGE_PERSONAL, param);
            datas=param.data;
            for (EmulateResultSearchResultDto dto : datas) {
                if(CommonStringUtil.isNotEmpty(dto.getContestPhysicalFile())) {
                    dto.setContestPhysicalFile(RestUtil.replaceImageUrl(dto.getContestPhysicalFile().replace("'\'", "//"), null));
                }
            }
            resObj = new ObjectDataRes<>(param.TotalRows, datas);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return resObj;
    }

    @Override
    public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengeResultGroup(
            EmulationChallengeSearchDto searchDto) {
        EmulatePersonalParam param = new EmulatePersonalParam();
        ObjectDataRes<EmulateResultSearchResultDto> resObj = new ObjectDataRes<EmulateResultSearchResultDto>();
        List<EmulateResultSearchResultDto> datas = new ArrayList<>();
        try {
            searchDto.setFunctionCode("EMULATION_PROGRAM_GROUP_RESULT");
            commom(searchDto, param);
            sqlManagerDb2Service.call(EMULATE_AND_CHALLENGE_RESULT_PERSONAL, param);
            datas=param.data;
            for (EmulateResultSearchResultDto dto : datas) {
                if(CommonStringUtil.isNotEmpty(dto.getContestPhysicalFile())) {
                    dto.setContestPhysicalFile(RestUtil.replaceImageUrl(dto.getContestPhysicalFile().replace("'\'", "//"), null));
                }
            }
            resObj = new ObjectDataRes<>(param.TotalRows, datas);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return resObj;
    }
    
    @Override
    public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengeResultGroupSumary(
            EmulationChallengeSearchDto searchDto) {
        EmulatePersonalParam param = new EmulatePersonalParam();
        ObjectDataRes<EmulateResultSearchResultDto> resObj = new ObjectDataRes<EmulateResultSearchResultDto>();
        List<EmulateResultSearchResultDto> datas = new ArrayList<>();
        try {
            searchDto.setFunctionCode("EMULATION_PROGRAM_GROUP_RESULT");
            commom(searchDto, param);
            sqlManagerDb2Service.call(EMULATE_AND_CHALLENGE_RESULT_SUMMARY, param);
            datas=param.data;
            for (EmulateResultSearchResultDto dto : datas) {
                if(CommonStringUtil.isNotEmpty(dto.getContestPhysicalFile())) {
                    dto.setContestPhysicalFile(RestUtil.replaceImageUrl(dto.getContestPhysicalFile().replace("'\'", "//"), null));
                }
            }
            resObj = new ObjectDataRes<>(param.TotalRows, datas);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return resObj;
    }

    //export list group
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResponseEntity exportEmulateAndChellengeGroup(EmulationChallengeSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            List<EmulateResultSearchResultDto> lstdata = getEmulateAndChallengeGroup(searchDto).getDatas();
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName;
            if(searchDto.getObjectType()!=null && searchDto.getIsChallenge()==1) {
            	if(StringUtils.equalsIgnoreCase(searchDto.getCheckPage(), "GA")) {
                    templateName = "Thong_tin_thach_thuc.xlsx";
            	}else {
                    templateName = "Chuong_trinh_thi_dua.xlsx";
            	}
            }else {
            templateName = "Chuong_trinh_thi_dua.xlsx";
            }
            String titleName="";
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            List<ItemColsExcelDto> cols = new ArrayList<>();
            //GA
            if(searchDto.getObjectType()!=null && searchDto.getIsChallenge()==1 && StringUtils.equalsIgnoreCase(searchDto.getCheckPage(), "GA")) {
                templateName="Thong_tin_thach_thuc_GA.xlsx";
                titleName="THƯ THÁCH THỨC GA ";
            }
            //AGENT
            else {
                if(searchDto.getIsChallenge()!=null) {
                    if(searchDto.getIsChallenge()==1) {
                        templateName="Thu_thach_thuc.xlsx";
                        titleName="THƯ THÁCH THỨC ";
                        
                        }
                    else {
                        templateName="Chuong_trinh_thi_dua.xlsx";
                        titleName="DANH SÁCH CHƯƠNG TRÌNH THI ĐUA ";
                    }
                }
            }
            if(searchDto.getType()!=null) {
                if(searchDto.getType()==1) {
                    templateName += "_dai_han_cap_phong_ban.xlsx";
                    titleName+="DÀI HẠN CẤP PHÒNG BAN";
                }
                else {
                    templateName += "_ngan_han_cap_phong_ban.xlsx";
                    titleName+="NGẮN HẠN CẤP PHÒNG BAN";
                }
            }
            
            ImportExcelUtil.setListColumnExcel(EmulateChallengePersonalEnum.class, cols);
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;
                List<String> datas = new ArrayList<String>();
                datas.add("Ngày cập nhật: "+DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, null, titleName, "A3", datas);
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                        EmulateResultSearchResultDto.class, cols, datePattern, "A6", mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##doExport##", e);
            }
        } catch (Exception e) {
            logger.error("##exportLis##", e);
        }
        return res;
    }

    //export result group
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResponseEntity exportEmulateAndChellengeGroupResult(EmulationChallengeSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            List<EmulateResultSearchResultDto> lstdata = getEmulateAndChallengeResultGroup(searchDto).getDatas();
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName;
            if(searchDto.getObjectType()!=null && searchDto.getObjectType()==1) {
            	if(StringUtils.equalsIgnoreCase(searchDto.getCheckPage(), "GA")) {
                    templateName = "Ket_qua_thach_thuc.xlsx";
            	}else {
                    templateName = "Ket_qua_thi_dua_group.xlsx";
            	}
            }else {
             templateName = "Ket_qua_thi_dua_group.xlsx";
            }
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            List<ItemColsExcelDto> cols = new ArrayList<>();
            ImportExcelUtil.setListColumnExcel(EmulateChallengeGroupEnum.class, cols);
            String titleName="";
            //GA
            if(searchDto.getObjectType()!=null && searchDto.getObjectType()==1) {
                if(searchDto.getIsChallenge() != null && searchDto.getIsChallenge()==1) {
                    templateName="Ket_qua_chuong_trinh_GA";
                    titleName="KẾT QUẢ THƯ THÁCH THỨC GA ";
                 }
            }
            //AGENT
            else {
                if(searchDto.getIsChallenge() != null) {
                    if(searchDto.getIsChallenge()==1) {
                        templateName="Ket_qua_thu_thach_thuc";
                        titleName="KẾT QUẢ THƯ THÁCH THỨC ";
                    }
                    else {
                        templateName="Ket_qua_thi_dua";
                        titleName="KẾT QUẢ THI ĐUA ";
                    }
                }
            }
            
            if(searchDto.getType() != null) {
                if(searchDto.getType()==1) {
                    titleName+="DÀI HẠN CẤP PHÒNG BAN";
                    templateName += "_dai_han_cap_phong_ban.xlsx";
                }
                else {
                    titleName+="NGẮN HẠN CẤP PHÒNG BAN";
                    templateName += "_ngan_han_cap_phong_ban.xlsx";
                }
            }
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;
                List<String> datas = new ArrayList<String>();
                datas.add("Ngày cập nhật: "+DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
                setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, null, titleName, "A3", datas);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                        EmulateResultSearchResultDto.class, cols, datePattern, "A6", mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##doExport##", e);
            }
        } catch (Exception e) {
            logger.error("##exportLis##", e);
        }
        return res;
    }
  
    @SuppressWarnings("unused")
    @Override
    public void setDataHeaderToXSSFWorkbookSheet(
            XSSFWorkbook xssfWorkbook
            , int sheetNumber
            , String[] titleHeader
            , String titleName
            , String startRow
            , List<String> datas
            ){
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);    
      
        CellReference landMark = new CellReference(startRow);
        int dataSize = datas.size();
        
        CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
        Font font = xssfWorkbook.createFont();
        //font.setBold(true);
        font.setColor(IndexedColors.BLUE.index);
        font.setFontName("Times New Roman");
        headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
        //headerCellStyle.setWrapText(true);
        headerCellStyle.setFont(font);
        
        if(datas != null && dataSize > 0) {
            int mark = landMark.getRow();
            int j=0;
            for (int i = mark; i < mark + dataSize; i++) {	
                XSSFRow row = xssfSheet.getRow(i);
                XSSFCell cell = null;
                if(row==null) {
                    row=xssfSheet.createRow(i);
                    cell = row.getCell(0);
                    if(cell==null) {
                        cell=row.createCell(0);
                    }
                }
                else {
                    if(cell==null) {
                        cell=row.createCell(0);
                    }
                    else {
                        cell = row.getCell(0);
                    }
                }
                cell.setCellValue(datas.get(j));
                cell.setCellStyle(headerCellStyle);
                j++;
            }
        }
        
        CellStyle headerTitle = xssfSheet.getWorkbook().createCellStyle();
        Font fontTitle  = xssfWorkbook.createFont();
        fontTitle.setBold(true);
        fontTitle.setColor(IndexedColors.BLUE.index);
        fontTitle.setFontName("Times New Roman");
        fontTitle.setFontHeightInPoints((short) 20);

        headerTitle.setFont(fontTitle);
        
        XSSFRow row2 = xssfSheet.createRow(1);
        XSSFCell cellTitle = row2.createCell(0);
        cellTitle = row2.createCell(0);
        cellTitle = row2.createCell(0);
        cellTitle.setCellValue(titleName);
        cellTitle.setCellStyle(headerTitle);

		XSSFRow row1 = xssfSheet.getRow(2);
		XSSFCell cellDate = row1.getCell(0);
		cellDate = row1.createCell(0);
		cellDate.setCellValue("Ngày cập nhật: "+DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
		cellDate.setCellStyle(headerCellStyle);

		
        //CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
        //headerCellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.index);
        
//        if(titleHeader!=null && titleHeader.length>0) {
//            //CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
//            headerCellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.index);
//            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            
//            Font font = xssfWorkbook.createFont();
//            font.setBold(true);
//            font.setColor(IndexedColors.WHITE.index);
//            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
//            headerCellStyle.setWrapText(true);
//            headerCellStyle.setFont(font);
//            
//            headerCellStyle.setBorderBottom(BorderStyle.THIN);
//            headerCellStyle.setBorderTop(BorderStyle.THIN);
//            headerCellStyle.setBorderRight(BorderStyle.THIN);
//            headerCellStyle.setBorderLeft(BorderStyle.THIN);
//            
//            XSSFRow row4 = xssfSheet.getRow(4);
//            for (int i = 0; i < titleHeader.length; i++) {
//                XSSFCell cell4 = row4.getCell(i);
//                if(cell4==null) {
//                    cell4=row4.createCell(i);
//                }
//                cell4.setCellValue(titleHeader[i]);
//                cell4.setCellStyle(headerCellStyle);
//            }
//        }
    }
    
    private void commom(EmulationChallengeSearchDto searchDto, EmulatePersonalParam param) {
        if (StringUtils.isEmpty(searchDto.getAgentCode())) {
            searchDto.setAgentCode(UserProfileUtils.getFaceMask());
        }
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam ="";
        try {
            stringJsonParam = mapper.writeValueAsString(searchDto);
        } catch (JsonProcessingException e) {
            logger.error("Exception ", e);
        }
        CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());
        
        String objectTypeSearch = "";
        String contestTypeSearch = "";
        String typeSearch = "";//not yet mapping
        String memoNo="";
        String territoryCode="";
        String regionCode="";
        String areaCode="";
        String officeCode="";
        String agentChildSearch="";
        //agent or ga
        if (searchDto.getObjectType() != null) {
            if (searchDto.getObjectType() == 0) {
                objectTypeSearch = "AND (LOAI_DOI_TUONG = N'" + "AGENT" + "')";
            } else if (searchDto.getObjectType() == 1) {
                objectTypeSearch = "AND (LOAI_DOI_TUONG = N'" + "GA_AGENT" + "')";
            }
        }
        //emulate or challenge
        if (CommonStringUtil.isNotEmpty(searchDto.getIsChallenge()+"")) {
            if (searchDto.getIsChallenge() == 1) {
                contestTypeSearch = "AND (LOAI_CHUONG_TRINH like N'%" + "1" + "')";//THACH THUC
            } else {
                contestTypeSearch = "AND (LOAI_CHUONG_TRINH like N'%" + "0" + "')";//THI DUA
            }
        }
        // long or short
        if (CommonStringUtil.isNotEmpty(searchDto.getType()+"")) {
            if (searchDto.getType() == 1) {
                typeSearch = "AND (UPPER(TYPE) like N'%DÀI HẠN')";
            } else {
                typeSearch = "AND (UPPER(TYPE) like N'%NGẮN HẠN')";
            }
        }
        if (CommonStringUtil.isNotEmpty(searchDto.getAgentChildSearch())) {
            agentChildSearch = "AND (LV3_AGENTCODE like N'%" + searchDto.getAgentChildSearch() +"')";
        }
        //search by meme
        if (CommonStringUtil.isNotEmpty(searchDto.getMemoNoGroup())) {
                memoNo = "AND (SO_MEMO like '" + searchDto.getMemoNoGroup() + "')";
        }
        //search by 
        if(CommonStringUtil.isNotEmpty(searchDto.getTerritoryCode())) {
            territoryCode="AND (TD_CODE like '" + searchDto.getTerritoryCode() + "')";
        }
        if(CommonStringUtil.isNotEmpty(searchDto.getRegionCode())) {
            regionCode="AND (R_CODE like '" + searchDto.getRegionCode() + "')";
        }
        if(CommonStringUtil.isNotEmpty(searchDto.getAreaCode())) {
            areaCode="AND (N_CODE like '" + searchDto.getAreaCode() + "')";
        }
        if(CommonStringUtil.isNotEmpty(searchDto.getOfficeCode())) {
            officeCode="AND (O_CODE like '" + searchDto.getOfficeCode() + "')";
        }
        param.agentCode=searchDto.getAgentCode();
        param.agentGroup=searchDto.getAgentGroup();
        param.page=searchDto.getPage();
        param.size=searchDto.getPageSize();
        param.sort=common.getSort();
        String searhContent=objectTypeSearch + contestTypeSearch + typeSearch+ memoNo;
        param.territory = territoryCode;
        param.area = areaCode;
        param.region = regionCode;
        param.office = officeCode;
        param.agentCodeChild = agentChildSearch;
        param.searchContent = searhContent + common.getSearch();
    }

    @Override
    public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengeResultGa(
            EmulationChallengeSearchDto searchDto) {
        EmulatePersonalParam param = new EmulatePersonalParam();
        ObjectDataRes<EmulateResultSearchResultDto> resObj = new ObjectDataRes<EmulateResultSearchResultDto>();
        List<EmulateResultSearchResultDto> datas = new ArrayList<>();
        try {
            searchDto.setFunctionCode("LETTER_CHALLENGE_OFFICE_GA_RESULT");
            commom(searchDto, param);
            sqlManagerDb2Service.call(EMULATE_AND_CHALLENGE_RESULT_PERSONAL, param);
            datas=param.data;
            for (EmulateResultSearchResultDto dto : datas) {
                if(CommonStringUtil.isNotEmpty(dto.getContestPhysicalFile())) {
                    dto.setContestPhysicalFile(RestUtil.replaceImageUrl(dto.getContestPhysicalFile().replace("'\'", "//"), null));
                }
            }
            resObj = new ObjectDataRes<>(param.TotalRows, datas);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return resObj;
    }
    //check agent child
    @Override
    public boolean checkAgentChild(String territory, String region, String area, String office, String agentParent, String agentChild) {
        try {
            CheckAgentChildParam param = new CheckAgentChildParam();
            param.territory=CommonStringUtil.isNotEmpty(territory) ? ";"+territory+";":null;
            param.region=CommonStringUtil.isNotEmpty(region) ? ";"+region+";":null;
            param.area=CommonStringUtil.isNotEmpty(area) ? ";"+area+";":null;
            param.office=CommonStringUtil.isNotEmpty(office) ? ";"+office+";":null;
            param.agentParent=CommonStringUtil.isNotEmpty(agentParent) ? ";"+agentParent+";":null;
            param.agentChild=CommonStringUtil.isNotEmpty(agentChild) ? ";"+agentChild+";":null;
            sqlManagerDb2Service.call(DS_SP_GET_ODS_AGENT_DETAIL, param);
            if(CommonCollectionUtil.isNotEmpty(param.data)) {
                return true;
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return false;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResponseEntity exportEmulateAndChallengeResultGa(EmulationChallengeSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            List<EmulateResultSearchResultDto> lstdata = getEmulateAndChallengeResultGa(searchDto).getDatas();
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "Ket_qua_thach_thuc.xlsx";
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            List<ItemColsExcelDto> cols = new ArrayList<>();
            ImportExcelUtil.setListColumnExcel(EmulateChallengeGroupEnum.class, cols);
            String titleName="";
            //GA
            if(searchDto.getObjectType()!=null && searchDto.getObjectType()==1) {
                if(searchDto.getIsChallenge() != null && searchDto.getIsChallenge()==1) {
                    templateName="Ket_qua_chuong_trinh_GA.xlsx";
                    titleName="Kết quả chương Trình GA ";
                 }
            }
            if(searchDto.getType() != null) {
                if(searchDto.getType()==1) {
                    titleName+="dài hạn";
                    templateName += "_dai_han.xlsx";
                }
                else {
                    titleName+="ngắn hạn";
                    templateName += "_ngan_han.xlsx";
                }
            }
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;
                List<String> datas = new ArrayList<String>();
                datas.add("Ngày cập nhật: "+DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
                setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, null, titleName, "A3", datas);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                        EmulateResultSearchResultDto.class, cols, datePattern, "A6", mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##doExport##", e);
            }
        } catch (Exception e) {
            logger.error("##exportLis##", e);
        }
        return res;
    }
    
}
