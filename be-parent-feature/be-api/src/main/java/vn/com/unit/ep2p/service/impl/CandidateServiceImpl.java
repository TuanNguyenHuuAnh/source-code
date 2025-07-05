package vn.com.unit.ep2p.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.formula.functions.Now;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import io.micrometer.core.instrument.search.Search;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.agent.dto.CertificatePagingParam;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetailParam;
import vn.com.unit.cms.core.module.candidate.dto.AddCandidateProfileDto;
import vn.com.unit.cms.core.module.candidate.dto.AddCandidateProfileDtoParam;
import vn.com.unit.cms.core.module.candidate.dto.AddCandidateProfileSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.CandidateInfoDto;
import vn.com.unit.cms.core.module.candidate.dto.CandidateInfoParam;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleCandidateParam;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleCandidateSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleHomepage;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleHomepageParam;
import vn.com.unit.cms.core.module.candidate.dto.MonthYear;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateDetailDto;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateDetailParam;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateParam;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.ScheduleCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.ScheduleCandidateParam;
import vn.com.unit.cms.core.module.candidate.dto.ScheduleCandidateSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.TemporaryCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.TemporaryCandidateParam;
import vn.com.unit.cms.core.module.candidate.dto.TemporaryCandidateSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentVersionDto;
import vn.com.unit.cms.core.module.document.repository.DocumentVersionRepository;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.enumdef.ExamScheduleCandidateEnum;
import vn.com.unit.ep2p.enumdef.ScheduleCandidateEnum;
import vn.com.unit.ep2p.enumdef.TemporaryCandidateEnum;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.CandidateService;
import vn.com.unit.ep2p.service.JcaZipcodeService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.ep2p.utils.RestUtil;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    DocumentVersionRepository documentVersionRepository;

    @Autowired
    ParseJsonToParamSearchService parseJsonToParamSearchService;

    @Autowired
    ApiAgentDetailServiceImpl apiAgentDetailServiceImpl;
    
    @Autowired
    @Qualifier("sqlManageDb2Service")
    private SqlManagerDb2Service sqlManagerDb2Service;

    private static final String DS_SP_GET_STUDIES_AND_EXAM_RESULTS = "RPT_ODS.DS_SP_GET_STUDIES_AND_EXAM_RESULTS";
    private static final String DS_SP_GET_LIST_DLBH_CERTIFICATION_EXAMS = "RPT_ODS.DS_SP_GET_LIST_DLBH_CERTIFICATION_EXAMS";
    private static final String DS_SP_GET_CANDIDATE_PROFILE = "RPT_ODS.DS_SP_GET_CANDIDATE_PROFILE";
    private static final String DS_SP_GET_LIST_REQUIREMENTS_CANDIDATE_PROFILES = "RPT_ODS.DS_SP_GET_LIST_REQUIREMENTS_CANDIDATE_PROFILES";
    private static final String DS_SP_GET_LIST_CLASSES_FOR_CERT = "RPT_ODS.DS_SP_GET_LIST_CLASSES_FOR_CERT";
    private static final String SP_SEARCH_ACCOUNT_REGISTER = "SP_SEARCH_ACCOUNT_REGISTER";

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManagerService;

    private Logger logger = LoggerFactory.getLogger(getClass());


    // HOMEPAGE
    //LỊCH HOC VÀ THI
    @Override
    public ExamScheduleHomepage getScheduleHomepage(String agentCode) {
        ExamScheduleHomepageParam param = new ExamScheduleHomepageParam();
        param.idNo = agentCode;
        param.page = 0;
        param.pageSize = 1;
        sqlManagerDb2Service.call(DS_SP_GET_STUDIES_AND_EXAM_RESULTS, param);
        List<ExamScheduleHomepage> list = new ArrayList<>();
        if (CommonCollectionUtil.isNotEmpty(param.data)) {
            list = param.data;
        }
        return list.get(0);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<DocumentVersionDto> getListRegulation(String languageCode) {
        List<DocumentVersionDto> list = documentVersionRepository.getListRegulation(languageCode);
        if (CommonCollectionUtil.isNotEmpty(list)) {
            for (DocumentVersionDto doc : list) {
                if (doc.getVersion() != 0) {
                    doc.setFullPhysicalFileName(RestUtil.replaceImageUrl(doc.getPhysicalFileName(), null));
                }
            }
        }
        return list;
    }

    //LAY THONG TIN UNG VIEN
    @Override
    public CandidateInfoDto getCandidateInfo(String userName) {
        CandidateInfoParam param = new CandidateInfoParam();
        param.userName = userName;
        sqlManagerDb2Service.call(DS_SP_GET_CANDIDATE_PROFILE, param);
        CandidateInfoDto entity = new CandidateInfoDto();
        if (CommonCollectionUtil.isNotEmpty(param.data)) {
            entity = param.data.get(0);
        }
        return entity;
    }

    // QUAN LY TUYEN DUNG
    // LICH THI
    @Override
    public ObjectDataRes<ExamScheduleCandidateDto> getListExamScheduleByCondition(
            ExamScheduleCandidateSearchDto dto) {
        ExamScheduleCandidateParam param = new ExamScheduleCandidateParam();
        dto.setFunctionCode("SCHEDULE_EXAM");
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam = "";
        try {
            stringJsonParam = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            logger.error("Exception ", e);
        }
        String search = "";
        if( ObjectUtils.isEmpty(dto.getExamDate())) {
        	search = " AND '"+dto.getNgayThi() +"' = VARCHAR_FORMAT( C.NGAY_THI,'YYYYMM')";
        }
        CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "SCHEDULE_EXAM");
        if(StringUtils.isEmpty(common.getSort())) {
        	common.setSort(" ORDER BY c.MA_KY_THI asc");
        }
        param.page = common.getPage();
        param.pageSize = common.getPageSize();
        param.sort = common.getSort();
        param.search = search +" "+common.getSearch();
        sqlManagerDb2Service.call(DS_SP_GET_LIST_DLBH_CERTIFICATION_EXAMS, param);
        List<ExamScheduleCandidateDto> data = new ArrayList<ExamScheduleCandidateDto>();
        int total = 0;
        if (CommonCollectionUtil.isNotEmpty(param.data)) {
            data = param.data;
            if (param.total != null) {
                total = param.total;
            }

        }
        ObjectDataRes<ExamScheduleCandidateDto> resObj = new ObjectDataRes<>(total, data);
        return resObj;
    }

    // QUAN LY TUYEN DUNG
    // LICH HOC
    @Override
    public ObjectDataRes<ScheduleCandidateDto> getListScheduleByCondition(ScheduleCandidateSearchDto dto) {
        ScheduleCandidateParam param = new ScheduleCandidateParam();
        dto.setFunctionCode("SCHEDULE_STUDIES");
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam = "";
        try {
            stringJsonParam = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            logger.error("Exception ", e);
        }
        String search = "";
        if( ObjectUtils.isEmpty(dto.getOrganizationDate())) {
        	search = " AND '"+dto.getNgayToChuc() +"' = VARCHAR_FORMAT( A.NGAY_TO_CHUC,'YYYYMM')";
        }
        CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "SCHEDULE_STUDIES");
        param.page = common.getPage();
        param.pageSize = common.getPageSize();
        param.sort = common.getSort();
        param.search = search +" "+common.getSearch();
        sqlManagerDb2Service.call(DS_SP_GET_LIST_CLASSES_FOR_CERT, param);
        List<ScheduleCandidateDto> data = new ArrayList<ScheduleCandidateDto>();

        int total = 0;
        if (CommonCollectionUtil.isNotEmpty(param.data)) {
            data = param.data;
            total = param.total;
        }
        ObjectDataRes<ScheduleCandidateDto> resObj = new ObjectDataRes<>(total, data);
        return resObj;
    }

    @Override
    public List<MonthYear> getListMonth() {
        List<MonthYear> listMonth = new ArrayList<MonthYear>();
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                listMonth.add(new MonthYear(i, "Tháng 0" + i));
            } else {
                listMonth.add(new MonthYear(i, "Tháng " + i));
            }
        }
        return listMonth;
    }

    @Override
    public List<MonthYear> getListYear() {
        LocalDate localDate = LocalDate.now();
        int currentYear = localDate.getYear();
        int nextYear = currentYear + 1;
        List<MonthYear> listYear = new ArrayList<MonthYear>();
        listYear.add(new MonthYear(1, String.valueOf(currentYear)));
        listYear.add(new MonthYear(2, String.valueOf(nextYear)));
        return listYear;
    }

    // EXPORT LỊCH THI
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ResponseEntity exportExamSchedule(Locale locale,  ExamScheduleCandidateSearchDto searchDto ) {
        ResponseEntity res = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            searchDto.setSize(0);
            ObjectDataRes<ExamScheduleCandidateDto> resObj = getListExamScheduleByCondition(searchDto);
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "Thong_tin_lich_thi.xlsx";
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A6";

            List<ExamScheduleCandidateDto> lstdata = resObj.getDatas();
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(ExamScheduleCandidateEnum.class, cols);
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);

                //fill date
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                XSSFRow row = null;
                Date dateNow = new Date();
                row = xssfSheet.getRow(1);
  
                XSSFCell cellDate = row.createCell(0);
        		CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();

                Font fontTitle = xssfWorkbook.createFont();
        		fontTitle.setFontName("Arial");
        		fontTitle.setFontHeightInPoints((short)10);
        		titleStyleDate.setFont(fontTitle); 
        		cellDate.setCellStyle(titleStyleDate);
                cellDate.setCellValue("Kết quả tính đến " + DateUtils.formatDateToString(dateNow, "dd/MM/yyyy"));
                //end

                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
                        ExamScheduleCandidateDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
            	logger.error("Exception", e);
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
        }
        return res;
    }

    // EXPORT LỊCH HOC
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ResponseEntity exportSchedule(Locale locale,ScheduleCandidateSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            searchDto.setSize(0);
            ObjectDataRes<ScheduleCandidateDto> resObj = getListScheduleByCondition(searchDto);
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String template = "Thong_tin_khoa_hoc.xlsx";
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
            String templateName = "Thong_tin_lich_hoc.xlsx";

            String startRow = "A6";

            List<ScheduleCandidateDto> lstdata = resObj.getDatas();
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(ScheduleCandidateEnum.class, cols);
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);

                //fill date
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                XSSFRow row = row = xssfSheet.getRow(1);
                XSSFCell cellDate = row.getCell(0);
                cellDate.setCellValue("Kết quả tính đến " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
                //end
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
                        ScheduleCandidateDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##ExportList##", e.getMessage());
            }
        } catch (Exception e) {
        }
        return res;
    }

    @Override
    public ObjectDataRes<ProfileCandidateDto> getListProfileCandidate(ProfileCandidateSearchDto searchDto) {
        ProfileCandidateParam param = new ProfileCandidateParam();
        param.page = searchDto.getPage();
        param.pageSize = searchDto.getPageSize();
        param.agentType = searchDto.getAgentType();
        param.userName = searchDto.getUserName();
        param.statusCandidateProfile = searchDto.getStatusCandidateProfile();
        param.statusCandidateExam = searchDto.getStatusCandidateExam();
        param.BDTH = searchDto.getBdth();
        param.BDAH = searchDto.getBdah();
        param.BDRH = searchDto.getBdrh();
        param.BDOH = searchDto.getBdoh();
        param.office = searchDto.getOffice();
        param.headOfDepartment = searchDto.getHeadOfDepartment();
        param.manager = searchDto.getManager();
        param.submitted = searchDto.getSubmitted();
        param.approved = searchDto.getApproved();
        param.reject = searchDto.getReject();
        param.requestAddRecords = searchDto.getRequestAddRecords();
        param.notYetExam = searchDto.getNotYetExam();
        param.passExam = searchDto.getPassExam();
        param.failedExam = searchDto.getFailedExam();
        param.retest = searchDto.getRetest();
        param.recruitment = searchDto.getRecruitment();
        param.contacted = searchDto.getContacted();
        param.TVTC = searchDto.getTvtc();
        param.idCard = searchDto.getIdCard();
        param.fullnameCandidate = searchDto.getFullnameCandidate();
        param.positionApply = searchDto.getPositionApply();
        param.birthdayCandidate = searchDto.getBirthdayCandidate();
        param.phoneNum = searchDto.getPhoneNum();
        param.email = searchDto.getEmail();
        param.dateExam = searchDto.getDateExam();
        param.scoreExam = searchDto.getScoreExam();
        // chua co store
        sqlManagerDb2Service.call(DS_SP_GET_LIST_DLBH_CERTIFICATION_EXAMS, param);
        List<ProfileCandidateDto> data = new ArrayList<ProfileCandidateDto>();
        int total = 0;
        if (CommonCollectionUtil.isNotEmpty(param.data)) {
            data = param.data;
            if (param.TotalRows != null) {
                total = param.TotalRows;
            }
        }
        ObjectDataRes<ProfileCandidateDto> resObj = new ObjectDataRes<>(total, data);
        return resObj;
    }

    @Override
    public ProfileCandidateDetailDto getProfileCandidateDetail(String idCardCandidate) {
        ProfileCandidateDetailParam param = new ProfileCandidateDetailParam();
        param.idCardCandidate = idCardCandidate;
        sqlManagerDb2Service.call(DS_SP_GET_CANDIDATE_PROFILE, param);
        ProfileCandidateDetailDto entity = new ProfileCandidateDetailDto();
        if (CommonCollectionUtil.isNotEmpty(param.data)) {
            entity = param.data.get(0);
        } else {
            return null;
        }
        return entity;
    }

    //ds bo sung ho so ung vien
    @Override
    public ObjectDataRes<AddCandidateProfileDto> getListProfileCandidateDetail(AddCandidateProfileSearchDto searchDto) {
        AddCandidateProfileDtoParam param = new AddCandidateProfileDtoParam();
        param.page = searchDto.getPage();
        param.pageSize = searchDto.getPageSize();
        param.agentType = searchDto.getAgentType();
        param.userName = searchDto.getUserName();
        param.regionSelect = searchDto.getRegionSelect();
        param.office = searchDto.getOffice();
        param.headOfDepartment = searchDto.getHeadOfDepartment();
        param.requestNum = searchDto.getRequestNum();
        param.manager = searchDto.getManager();
        param.TVTC = searchDto.getTVTC();
        param.idNo = searchDto.getIdNo();
        param.candidateName = searchDto.getCandidateName();
        param.positionApply = searchDto.getPositionApply();
        param.birthday = searchDto.getBirthday();
        param.phoneNum = searchDto.getPhoneNum();
        param.email = searchDto.getEmail();
        param.request = searchDto.getRequest();
        param.addInfo = searchDto.getAddInfo();
        sqlManagerDb2Service.call(DS_SP_GET_LIST_REQUIREMENTS_CANDIDATE_PROFILES, param);
        List<AddCandidateProfileDto> data = new ArrayList<AddCandidateProfileDto>();
        int total = 0;
        if (CommonCollectionUtil.isNotEmpty(param.data)) {
            data = param.data;
            if (param.data != null) {
                total = param.TotalRows;
            }
        }
        ObjectDataRes<AddCandidateProfileDto> resObj = new ObjectDataRes<>(total, data);
        return resObj;
    }

    //ds ung vien vang lai
    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ObjectDataRes<TemporaryCandidateDto> getListTemporaryCandidateParam(TemporaryCandidateSearchDto searchDto) {
        ObjectMapper mapper = new ObjectMapper();
    	searchDto.setFunctionCode("ACCOUNT_REGISTER");
        List<String> listOrgId = apiAgentDetailServiceImpl.getOffceCode(searchDto.getAgentCode());
        String orgId = listOrgId.stream().collect(Collectors.joining(";"));
    	searchDto.setOrgId(orgId);
    	
        TemporaryCandidateParam param = new TemporaryCandidateParam();
        
    	String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        ObjectDataRes<TemporaryCandidateDto> resObj = new ObjectDataRes<TemporaryCandidateDto>();
        try {
            param.stringJsonParam = stringJsonParam;
            sqlManagerService.call(SP_SEARCH_ACCOUNT_REGISTER, param);
            if (CommonCollectionUtil.isNotEmpty(param.data)) {
                resObj.setTotalData(param.totalData);
                resObj.setDatas(param.data);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return resObj;
    }
    
 

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ResponseEntity exportListTemporaryCandidate(TemporaryCandidateSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(99999);
            TemporaryCandidateParam param = new TemporaryCandidateParam();
            ObjectMapper mapper = new ObjectMapper();
            searchDto.setFunctionCode("ACCOUNT_REGISTER");

            List<String> listOrgId = apiAgentDetailServiceImpl.getOffceCode(searchDto.getAgentCode());
            String orgId = listOrgId.stream().collect(Collectors.joining(";"));
            searchDto.setOrgId(orgId);
            String stringJsonParam ="";
            try {
                stringJsonParam = mapper.writeValueAsString(searchDto);
            } catch (JsonProcessingException e) {
                logger.error("Exception ", e);
            }
            List<TemporaryCandidateDto> lstdata = new ArrayList<>();
            try {
                param.stringJsonParam = stringJsonParam;
                sqlManagerService.call(SP_SEARCH_ACCOUNT_REGISTER, param);
                if (CommonCollectionUtil.isNotEmpty(param.data)) {
                    lstdata = param.data;
                    for(TemporaryCandidateDto ls:lstdata){
                        if(ls.isContact() == true){
                            ls.setContactString("Đã liên hệ");
                        }else{
                            ls.setContactString("Chưa liên hệ");
                        }

                    }
                }
            } catch (Exception e) {
                System.out.print(e);
            }
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/mm/yyyy hh:mm:ss";
            String templateName = "Ung_vien_vang_lai.xlsx";
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A5";

            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(TemporaryCandidateEnum.class, cols);
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0,null, lstdata,
                        TemporaryCandidateDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);


            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

}