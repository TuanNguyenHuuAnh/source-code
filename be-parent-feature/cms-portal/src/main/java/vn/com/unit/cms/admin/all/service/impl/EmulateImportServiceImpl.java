package vn.com.unit.cms.admin.all.service.impl;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.provider.ConnectionProvider;
import net.sf.jasperreports.engine.export.oasis.CellStyle;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import vn.com.unit.cms.admin.all.db2.service.Db2Service;
import vn.com.unit.cms.admin.all.dto.EmulateResultParams;
import vn.com.unit.cms.admin.all.entity.EmulateResultImport;
import vn.com.unit.cms.admin.all.entity.FaqsCategoryImport;
import vn.com.unit.cms.admin.all.enumdef.exports.EmulateExportEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.EmulateImportRepository;
import vn.com.unit.cms.admin.all.repository.EmulateImportStRepository;
import vn.com.unit.cms.admin.all.repository.EmulateLanguageRepository;
import vn.com.unit.cms.admin.all.service.EmulateImportService;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.module.emulate.dto.ContestDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateImportSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportDto;
import vn.com.unit.cms.core.module.emulate.entity.ContestApplicableDetail;
import vn.com.unit.cms.core.module.emulate.entity.ContestSummary;
import vn.com.unit.cms.core.module.emulate.entity.ContestSummaryImport;
import vn.com.unit.cms.core.module.emulate.entity.EmulateLanguage;
import vn.com.unit.cms.core.module.emulate.repository.ContestApplicableDetailRepository;
import vn.com.unit.cms.core.module.emulate.repository.ContestRepository;
import vn.com.unit.cms.core.module.emulate.repository.ContestSummaryRepository;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentNotifyParamDto;
import vn.com.unit.ep2p.admin.dto.Db2ContestSummaryParamDto;
import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.imp.excel.annotation.CoreReadOnlyTx;
import vn.com.unit.imp.excel.annotation.CoreTx;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Service
@Qualifier(value = "emunateImportServiceImpl")
@CoreReadOnlyTx
public class EmulateImportServiceImpl implements EmulateImportService {

    @Autowired
    private EmulateImportRepository emulateImportRepository;

    @Autowired
    private EmulateImportStRepository emulateImportStRepository;

    /**
     * MessageSource
     */
    @Autowired
    private MessageSource msg;

    /**
     * SystemConfigure
     */
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ConnectionProvider connectionProvider;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestSummaryRepository contestSummaryRepository;

    @Autowired
    private ContestApplicableDetailRepository contestApplicableDetailRepository;

    @Autowired
    private CmsCommonService cmsCommonService;

    @Autowired
    private EmulateLanguageRepository emulateLanguageRepository;

    @Autowired
    private Db2Service db2Service;

    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManager;

    private final int BATCHSIZE = 10000;


    @Override
    public int countData(String sessionKey) {
        return emulateImportRepository.countData(sessionKey);
    }

    @Override
    public List<EmulateImportDto> getListData(int page, String sessionKey, int sizeOfPage) {
        return emulateImportRepository.findListData(page, sizeOfPage, sessionKey);

    }

    @Override
    public List<EmulateImportDto> getListDataExport(String sessionKey) {
        return emulateImportRepository.findListDataExport(sessionKey);
    }

    @Override
    public int countError(String sessionKey) {
        return emulateImportRepository.countError(sessionKey);
    }

    @Override
    public List<EmulateImportDto> getAllDatas(String sessionKey) {
        return emulateImportRepository.findAllDatas(sessionKey);

    }

    @Override
    public Class getImportDto() {
        return EmulateImportDto.class;

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
    public SqlManager getSqlManager() {
        return sqlManager;
    }

    private Date checkMaxDate(Date date) throws ParseException {
        if(!ObjectUtils.isNotEmpty(date)) {
                Date maxDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/9999");
                return maxDate;
            }
        return date;
    }
    
    @Override
    public boolean validateBusiness(String sessionKey, ImportExcelSearchDto searchDto, Map<String, Object> mapParams,
                                    List<EmulateImportDto> listDataValidate) {
        EmulateResultParams params = new EmulateResultParams();
        params.sessionKey = sessionKey;
        sqlManager.call("SP_IMPORT_CONTEST_SUMMARY_VALID", params);
        updateColumn(sessionKey);
        return countError(sessionKey) > 0;
    }

    public void updateColumn(String sessionKey) {

        List<ContestSummaryImport> listData = emulateImportStRepository.findListDataImport(sessionKey);
        for (ContestSummaryImport item : listData) {
            if (item.getKeywordsSeo() == null) {
                item.setKeywordsSeo(FileUtil.deAccent(item.getContestName()).toLowerCase().replace(" ", "-"));
            } else {
                item.setKeywordsSeo(FileUtil.deAccent(item.getKeywordsSeo()).toLowerCase().replace(" ", "-"));
            }
            if (item.getKeywords() == null) {
                item.setKeywords("#" + FileUtil.deAccent(item.getContestName()).toLowerCase().replace(" ", ""));
            } else {
                item.setKeywords("#" + FileUtil.deAccent(item.getKeywords()).toLowerCase().replace(" ", ""));

            }
        }
        emulateImportStRepository.save(listData);
    }

    @Override
    public PageWrapper<EmulateImportDto> doSearchDetail(int page, EmulateImportSearchDto searchDto, int pageSize,
                                                        Locale locale) throws Exception {
        PageWrapper<EmulateImportDto> pageWrapper = new PageWrapper<>();
        int sizeOfPage = systemConfig.settingPageSizeList(pageSize, pageWrapper, page);

        int count = emulateImportRepository.countDataBySearchDto(searchDto);

        List<EmulateImportDto> result = new ArrayList<>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            result = emulateImportRepository.findListDataBySearchDto(startIndex, sizeOfPage, searchDto);
            parseData(result, false, locale);
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    @Override
    public void exportExcel(EmulateImportSearchDto searchDto, HttpServletRequest req, HttpServletResponse res,
                            Locale locale) throws Exception {
        // TODO
        String templateName = "";

        List<EmulateImportDto> lstData = getListDataExportBySearchDto(searchDto);

        if (CollectionUtils.isNotEmpty(lstData)) {
            parseData(lstData, true, locale);
        }

        doExport(templateName, searchDto, lstData, EmulateExportEnum.class, res, locale);
    }

    private List<EmulateImportDto> getListDataExportBySearchDto(EmulateImportSearchDto searchDto) {
        return emulateImportRepository.findListDataExportBySearchDto(searchDto);

    }

    @SuppressWarnings("unchecked")
    private void doExport(String templateName, EmulateImportSearchDto searchDto, List<EmulateImportDto> lstData,
                          Class enumClass, HttpServletResponse res, Locale locale) throws Exception {

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
        emulateImportRepository.updateDataBySessionKey(sessionKey, UserProfileUtils.getUserNameLogin());
    }

    @Override
    public List<String> initHeaderTemplate() {
        List<String> result = new ArrayList<String>();

        result.add("import.emulate.id");
        result.add("import.emulate.contest.no");
        result.add("import.emulate.memo");
        result.add("import.emulate.contest.name");
        result.add("import.emulate.key.seo");
        result.add("import.emulate.key");
        result.add("import.emulate.key.desc");
        result.add("import.emulate.description");
        result.add("import.emulate.contestType");
        result.add("import.emulate.contest.phy.img");
        result.add("import.emulate.contest.phy.file");
        result.add("import.emulate.hot");
        result.add("import.emulate.challenge");
        result.add("import.emulate.action");
        result.add("import.emulate.ods");
        result.add("import.emulate.start.date");
        result.add("import.emulate.end.date");
        result.add("import.emulate.effctive.date");
        result.add("import.emulate.expired.date");
        result.add("import.emulate.object");
        result.add("import.emulate.agent.name");
        result.add("import.emulate.area");
        result.add("import.emulate.territory");
        result.add("import.emulate.region");
        result.add("import.emulate.office");
        result.add("import.emulate.position");
        result.add("import.emulate.reporting");
        result.add("import.emulate.note");
        result.add("template.header.message.error");
        return result;
    }

    @Override
    public void saveListImport(List<EmulateImportDto> listDataSave, String sessionKey, Locale locale, String username)
            throws Exception {
        try {
            long userId = UserProfileUtils.getAccountId();
            List<EmulateImportDto> listImport = emulateImportRepository.findAllDatas(sessionKey);
            for (EmulateImportDto item : listImport) {
                ContestSummary entity = new ContestSummary();
                entity.setMemoNo(item.getMemoNo());
                if (StringUtils.isBlank(item.getCode())) {
                    entity.setCode(CommonUtil.getNextBannerCode(CmsPrefixCodeConstant.PREFIX_CODE_EMULATE, cmsCommonService.getMaxCodeYYMM("M_CONTEST_SUMMARY", CmsPrefixCodeConstant.PREFIX_CODE_EMULATE)));
                } else {
                    ContestSummary getCode = contestSummaryRepository.findByContestCode(item.getCode());
                    ContestSummary getId = contestSummaryRepository.findOne(item.getId());
                    if (getCode == null && getId == null) {
                        entity.setCode(item.getCode());
                    } else {
                        entity.setId(getCode.getId());
                        entity.setCode(getCode.getCode());
                        entity.setUpdatedDate(new Date());
                        entity.setUpdatedBy(username);
                    }
                }
                entity.setContestName(item.getContestName());
                entity.setKeywordsSeo(item.getKeywordsSeo());
                entity.setKeywords(item.getKeywords());
                entity.setKeywordsDesc(item.getKeywordsDesc());
                entity.setDescription(item.getDescription());
                entity.setContestType(item.getContestType());
                entity.setContestPhysicalImt(item.getContestPhysicalImt());
                entity.setContestPhysicalFile(item.getContestPhysicalFile());
                if (item.getIsHot() != null && item.getIsHot().equals("x")) {
                    entity.setHot(true);
                } else {
                    entity.setHot(false);
                }
                if (item.getIsChallenge() != null && item.getIsChallenge().equals("x")) {
                    entity.setChallenge(true);
                } else {
                    entity.setChallenge(false);
                }
                if (item.getEnabled() != null && item.getEnabled().equals("x")) {
                    entity.setEnabled(true);
                } else {
                    entity.setEnabled(false);
                }         
                entity.setStartDate(item.getStartDate());
                entity.setEffectiveDate(item.getEffectiveDate());
                entity.setExpiredDate(checkMaxDate(item.getExpiredDate()));
                entity.setEndDate(checkMaxDate(item.getEndDate()));
                entity.setApplicableObject(item.getApplicableObject());  
                
                if (item.getApplicableObject().equals("1")) {
                    entity.setApplicableObject("All");
                }else if (item.getApplicableObject().equals("2")) {
                    entity.setApplicableObject("IMP");
                }else if (item.getApplicableObject().equals("3")) {
                    entity.setApplicableObject("SEL");
                }
                
                if (item.getIsOds() != null && item.getIsOds().equals("x")) {
                    entity.setOds(true);
                    entity.setSaveDetail(true);                  
                } else {
                    entity.setOds(false);                    
                    if (item.getApplicableObject().equals("1")) {
                        entity.setSaveDetail(true);
                    }
                    if (item.getApplicableObject().equals("2")) {
                        entity.setSaveDetail(false);
                        entity.setAgentCode(item.getAgentName());

                    }
                    if (item.getApplicableObject().equals("3")) {
                        entity.setSaveDetail(true);
                        entity.setArea(item.getArea());
                        entity.setTerritory(item.getTerritory());
                        entity.setRegion(item.getRegion());
                        entity.setOffice(item.getOffice());
                        entity.setPosition(item.getPosition());
                        entity.setReportingtoCode(item.getReportingtoName());
                    }                
                }                             	
                entity.setCreatedDate(new Date());
                entity.setCreatedBy(username);
                entity.setNote(item.getNote());
                contestSummaryRepository.save(entity);
                ConnectDb2(entity.getId(),item,username);


            }
        } catch (Exception e) {
            logger.error("####saveListImport####", e);
            throw new Exception(e);
        }
    }

    public void ConnectDb2(Long contestId, EmulateImportDto item , String username) throws SQLException {
        Db2ContestSummaryParamDto db2 = new Db2ContestSummaryParamDto();
            if (StringUtils.equalsIgnoreCase(item.getApplicableObject(), "2")) {
                item.setTerritory(null);
                item.setArea(null);
                item.setRegion(null);
                item.setOffice(null);
                item.setPosition(null);
                if(StringUtils.isNotBlank(item.getAgentName())) {
                    callStoreDb2(db2, item);
                    List<Db2SummaryDto> lstData = db2.lstAgent;
                    saveApplicableDetail(lstData, contestId, username);
                }           
            }

    }

    public void saveApplicableDetail(List<Db2SummaryDto> lstData, Long contestId, String username) throws SQLException {
        Connection connection = connectionProvider.getConnection();
        //CHANGE OJECT DELETE DATA CŨ
        connection.setAutoCommit(false);
        String deleteOject = " DELETE FROM M_CONTEST_APPLICABLE_DETAIL WHERE CONTEST_ID = ?";

        PreparedStatement psttt = connection.prepareStatement(deleteOject);
        int startRowDeleteOject = 0;
        for (Db2SummaryDto data : lstData) {
            psttt.setLong(1, contestId);
            psttt.addBatch();
            startRowDeleteOject++;
            if (startRowDeleteOject % BATCHSIZE == 0) {
                psttt.executeBatch();
                psttt.clearBatch();
                startRowDeleteOject = 0;
            }
            psttt.executeBatch();
            psttt.clearBatch();
            connection.commit();
            //END
        }

        //INSERT
        String query = "INSERT INTO M_CONTEST_APPLICABLE_DETAIL (CONTEST_ID, TERRITORRY, AREA, REGION, OFFICE, POSITION, AGENT_NAME, AGENT_CODE, REPORTINGTO_CODE, REPORTINGTO_NAME, CREATE_BY, CREATE_DATE) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pst = connection.prepareStatement(query);
        int startRow = 0;
        for (Db2SummaryDto data : lstData) {

            pst.setLong(1, contestId);
            pst.setString(2, data.getNdCode());
            pst.setString(3, data.getNCode());
            pst.setString(4, data.getRCode());
            pst.setString(5, data.getOCode());
            pst.setString(6, data.getAgentType());
            pst.setString(7, data.getAgentName());
            pst.setString(8, data.getAgentCode());
            pst.setString(9, data.getReportingToCode());
            pst.setString(10, data.getReportingToName());
            pst.setString(11, username);
            Date curDate = new Date();
            pst.setDate(12, new java.sql.Date(curDate.getTime()));
            pst.addBatch();
            startRow++;
            if (startRow % BATCHSIZE == 0) {
                pst.executeBatch();
                logger.error("PST = "+startRow);

                pst.clearBatch();
                startRow = 0;
            }

        } 

        pst.executeBatch();
        pst.clearBatch();
        connection.commit();
        //END
    }

    public void callStoreDb2(Db2ContestSummaryParamDto db2, EmulateImportDto entityDetail) {
        db2.territory = StringUtils.isBlank(entityDetail.getTerritory()) ? null : (";" + entityDetail.getTerritory().replace(",", ";") + ";");
        db2.region = StringUtils.isBlank(entityDetail.getRegion()) ? null : (";" + entityDetail.getRegion().replace(",", ";") + ";");
        db2.office = StringUtils.isBlank(entityDetail.getOffice()) ? null : (";" + entityDetail.getOffice().replace(",", ";") + ";");
        db2.area = StringUtils.isBlank(entityDetail.getArea()) ? null : (";" + entityDetail.getArea().replace(",", ";") + ";");
        db2.leader = entityDetail.getReportingtoName();
        db2.agentType = StringUtils.isBlank(entityDetail.getPosition()) ? null : (";" + entityDetail.getPosition().replace(",", ";") + ";");
        db2.agentCode = StringUtils.isBlank(entityDetail.getAgentName()) ? null : (";" + entityDetail.getAgentName().replace(",", ";") + ";");


        db2Service.callStoreAgentDetailDb2("RPT_ODS.DS_SP_GET_ODS_AGENT_DETAIL", db2);
    }
    @Override
	@CoreTx
	public void updateData(String memoNo, String username,ContestSummary getSummary) {
		getSummary.setUpdatedDate(new Date());
		getSummary.setUpdatedBy(username);
		contestSummaryRepository.save(getSummary);
	}


}
