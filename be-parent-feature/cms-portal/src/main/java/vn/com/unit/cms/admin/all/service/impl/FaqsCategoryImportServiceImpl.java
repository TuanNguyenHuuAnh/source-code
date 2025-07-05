package vn.com.unit.cms.admin.all.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.admin.all.dto.FasqCategoryParams;
import vn.com.unit.cms.admin.all.entity.FaqsCategory;
import vn.com.unit.cms.admin.all.entity.FaqsCategoryImport;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.FaqsCategoryImportRepository;
import vn.com.unit.cms.admin.all.repository.FaqsCategoryRepository;
import vn.com.unit.cms.admin.all.repository.FaqsImportRepository;
import vn.com.unit.cms.admin.all.repository.FaqsLanguageRepository;
import vn.com.unit.cms.admin.all.service.FaqsCategoryImportService;
import vn.com.unit.cms.core.module.faqs.dto.FaqsCategoryImportDto;
import vn.com.unit.cms.core.module.faqs.entity.Faqs;
import vn.com.unit.cms.core.module.faqs.entity.FaqsLanguage;
import vn.com.unit.cms.core.module.faqs.repository.FaqsRepository;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.dts.utils.DtsStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.efo.entity.EfoDoc;
import vn.com.unit.ep2p.core.efo.repository.EfoDocRepository;
import vn.com.unit.ep2p.core.efo.service.EfoDocService;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.imp.excel.annotation.CoreReadOnlyTx;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.entity.JpmProcessInstAct;
import vn.com.unit.workflow.activiti.repository.JpmProcessInstActRepository;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.entity.JpmStatusCommon;
import vn.com.unit.workflow.repository.JpmBusinessRepository;
import vn.com.unit.workflow.repository.JpmProcessDeployRepository;
import vn.com.unit.workflow.repository.JpmStatusCommonRepository;

@SuppressWarnings("rawtypes")
@Service
@Qualifier(value = "faqsCategoryImportServiceImpl")
@CoreReadOnlyTx
public class FaqsCategoryImportServiceImpl implements FaqsCategoryImportService{
    
    @Autowired
    private FaqsCategoryImportRepository faqsCategoryImportRepository;
    
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
    
    @Autowired
    private CmsCommonService cmsCommonService;
    
    @Autowired
    private FaqsRepository faqsRepository;
    
    @Autowired
    private FaqsLanguageRepository faqsLanguageRepository;
    @Autowired
    private FaqsCategoryRepository faqsCategoryRepository;
    
    @Autowired
    private EfoDocRepository efoDocRepository;
    
    @Autowired
    private JpmProcessInstActRepository jpmProcessInstActRepository;
    
    @Autowired
    private JpmBusinessRepository jpmBusinessRepository;
    
    @Autowired
    private JpmProcessDeployRepository jpmProcessDeployRepository;
    
    @Autowired
    private JpmStatusCommonRepository jpmStatusCommonRepository;
    
    @Autowired
    private FaqsImportRepository faqsImportRepository;

    @Override
    public int countData(String sessionKey) { 
        return faqsCategoryImportRepository.countData(sessionKey);
    }

    @Override
    public List<FaqsCategoryImportDto> getListData(int page, String sessionKey, int sizeOfPage) {
        return faqsCategoryImportRepository.findListData(page, sizeOfPage, sessionKey);
    }
    

    @Override
    public List<FaqsCategoryImportDto> getListDataExport(String sessionKey) {
        return faqsCategoryImportRepository.findListDataExport(sessionKey);
    }

    @Override
    public int countError(String sessionKey) {
        return faqsCategoryImportRepository.countError(sessionKey);
    }

    @Override
    public List<FaqsCategoryImportDto> getAllDatas(String sessionKey) {
        return faqsCategoryImportRepository.findAllDatas(sessionKey);
    }

    @Override
    public Class getImportDto() {
        return FaqsCategoryImportDto.class;
    }

    @Override
    public Class getEntity() {
        // TODO Auto-generated method stub
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
    
    public void updateColumn(String sessionKey) {

        List<FaqsCategoryImport> listData = faqsImportRepository.findListDataImport(sessionKey);
        for(FaqsCategoryImport item : listData) {
            if(item.getTitle() != null)
            {
                item.setKeywordsSeo(FileUtil.deAccent(item.getTitle()).toLowerCase().replace(" ", "-"));
                item.setKeywords("#" + FileUtil.deAccent(item.getTitle()).toLowerCase().replace(" ",""));
            }
        }
        faqsImportRepository.save(listData);
    }
    @Override
    public boolean validateBusiness(String sessionKey, ImportExcelSearchDto searchDto, Map<String, Object> mapParams,
            List<FaqsCategoryImportDto> listDataValidate) {
        
      FasqCategoryParams params = new FasqCategoryParams();
      params.sessionKey = sessionKey;
      sqlManager.call("SP_IMPORT_FAQS_VALID", params);
      updateColumn(sessionKey);
      return countError(sessionKey) > 0;
    }

    @Override
    public void saveListImport(List<FaqsCategoryImportDto> listDataSave, String sessionKey, Locale locale,
            String username) throws Exception {
        try {
            long userId = UserProfileUtils.getAccountId();
            SimpleDateFormat f = new SimpleDateFormat(systemConfig.getConfig(SystemConfig.DATE_PATTERN));
           //get list data import by session key
            List<FaqsCategoryImportDto> list = faqsCategoryImportRepository.findAllDatas(sessionKey);
            for(FaqsCategoryImportDto item : list) {
                FaqsCategory cat = faqsCategoryRepository.findByCode(item.getCode());
                if(item.getFaqsCode() == null) {
                    
                    EfoDoc efoDoc = new EfoDoc();
                    efoDoc.setId(sqlManager.getNextValBySeqName(CoreConstant.SEQ + AppCoreConstant.TABLE_EFO_DOC));
                    efoDoc.setSubmittedPositionId(userId);
                    efoDoc.setOwnerId(userId);
                    efoDoc.setOwnerPositionId(userId);
                    efoDoc.setSystemCode("DSUCCESS");
                    efoDoc.setAppCode("DSUCCESS-WEBAPP");
                    efoDoc.setCompanyId(userId);
                    efoDoc.setCreatedDate(new Date());
                    efoDoc.setSubmittedDate(new Date());
                    efoDoc.setCreatedId(userId);
                    efoDocRepository.save(efoDoc);
                    
                    long idDoc = efoDoc.getId();
                    JpmProcessInstAct process = new JpmProcessInstAct();
                    process.setReferenceId(idDoc);
                    
                    JpmBusinessDto business = jpmBusinessRepository.getBusinessDtoByCodeAndCompanyId("BUSINESS_CMS",new Long(2));
                    process.setBusinessId(business.getBusinessId());
                    
                    JpmProcessDeployDto deploy = jpmProcessDeployRepository.getJpmProcessDeployLasted(new Long(2),business.getBusinessId(),new Date());
                    process.setProcessDeployId(deploy.getProcessDeployId().toString());
                    
                    process.setReferenceType(1);
                    process.setCreatedDate(new Date());
                    process.setCreatedId(userId);
                    
                    JpmStatusCommonDto commonDto = jpmStatusCommonRepository.findStatusCommon("994", "VI");
                    process.setCommonStatusId(commonDto.getId());
                    
                    jpmProcessInstActRepository.save(process);
                    
                    //save entity m_faq
                    Faqs faqs  = new Faqs();
                    faqs.setCategoryId(cat.getId());
                    faqs.setCode(CommonUtil.getNextBannerCode("FAQ",getMaxCode("M_FAQS", "FAQ")));
                    if(item.getEnabled() !=null && item.getEnabled().equals("x"))
                    {
                        faqs.setEnabled(true);
                    }else {
                        faqs.setEnabled(false);
                    }
                    faqs.setPostedDate(item.getPostedDate());
                    //if(item.getExpirationDate() != null) {
                        faqs.setExpirationDate(checkMaxDate(item.getExpirationDate()));
                    //}
                    faqs.setCreateDate(new Date());
                    faqs.setCreateBy(username);    
                    faqs.setDocId(idDoc);
                    faqsRepository.save(faqs);
                    //save language VI
                    FaqsLanguage faqsLang = new FaqsLanguage();
                    faqsLang.setFaqsId(faqs.getId());
                    faqsLang.setLanguageCode("VI");
                    faqsLang.setTitle(item.getTitle());
                    faqsLang.setContent(item.getContent());
                    faqsLang.setKeywordDescription(item.getKeywordsDesc());
                    faqsLang.setKeyword(item.getKeywords());
                    faqsLang.setLinkAlias(item.getKeywordsSeo());
                    faqsLang.setCreateDate(new Date());
                    faqsLang.setCreateBy(username);
                    faqsLanguageRepository.save(faqsLang);
                    //save language EN
                    FaqsLanguage faqsLangee = new FaqsLanguage();
                    faqsLangee.setFaqsId(faqs.getId());
                    faqsLangee.setLanguageCode("EN");
                    faqsLangee.setTitle(item.getTitle());
                    faqsLangee.setContent(item.getContent());
                    faqsLangee.setKeywordDescription(item.getKeywordsDesc());
                    faqsLangee.setKeyword(item.getKeywords());
                    faqsLangee.setLinkAlias(item.getKeywordsSeo());
                    faqsLangee.setCreateDate(new Date());
                    faqsLangee.setCreateBy(username);
                    faqsLanguageRepository.save(faqsLangee);
                    
                
                    
                     
                } else {
                    Faqs faqs  = faqsCategoryImportRepository.findFaqsByCategoryAndCode(cat.getId(),item.getFaqsCode());
                    if(faqs !=null) {
                        JpmProcessInstActDto process = jpmProcessInstActRepository.getJpmProcessInstActDtoByReference(faqs.getDocId(),1);
                        JpmStatusCommonDto commonDto = jpmStatusCommonRepository.findStatusCommon("000", "VI");
                        if(process.getCommonStatusId().equals(commonDto.getId()))
                        {

                            if(item.getEnabled() !=null && item.getEnabled().equals("x"))
                            {
                                faqs.setEnabled(true);
                            }else {
                                faqs.setEnabled(false);
                            }
                            faqs.setPostedDate(item.getPostedDate());
                            //if(item.getExpirationDate() != null) {
                                faqs.setExpirationDate(checkMaxDate(item.getExpirationDate()));
                            //}
                            faqs.setUpdateDate(new Date());
                            faqs.setUpdateBy(username);                    
                            faqsRepository.save(faqs);
                            
                            List<FaqsLanguage> lstLang = faqsLanguageRepository.findByFaqsId(faqs.getId());
                            for( FaqsLanguage faqsLang : lstLang) {
                                faqsLang.setTitle(item.getTitle());
                                faqsLang.setContent(item.getContent());
                                faqsLang.setKeywordDescription(item.getKeywordsDesc());
                                faqsLang.setKeyword(item.getKeywords());
                                faqsLang.setLinkAlias(item.getKeywordsSeo());
                                faqsLang.setUpdateDate(new Date());
                                faqsLang.setUpdateBy(username);
                                faqsLanguageRepository.save(faqsLang);
                            }
                            
                            JpmProcessInstAct proAct = jpmProcessInstActRepository.findOne(process.getId());
                            JpmStatusCommonDto common = jpmStatusCommonRepository.findStatusCommon("994", "VI");
                            proAct.setCommonStatusId(common.getId());
                            proAct.setUpdatedDate(new Date());
                            proAct.setUpdatedId(userId);
                            jpmProcessInstActRepository.save(proAct);
                        }
                        
                    }else {
                        
                        Faqs faqsCode  = faqsCategoryImportRepository.findFaqsByCode(item.getFaqsCode());
                        
                        JpmProcessInstActDto process = jpmProcessInstActRepository.getJpmProcessInstActDtoByReference(faqsCode.getDocId(),1);
                        JpmStatusCommonDto commonDto = jpmStatusCommonRepository.findStatusCommon("000", "VI");
                        if(process.getCommonStatusId().equals(commonDto.getId()))
                        {
                            faqsCode.setCategoryId(cat.getId());
                            if(item.getEnabled() !=null && item.getEnabled().equals("x"))
                            {
                                faqsCode.setEnabled(true);
                            }else {
                                faqsCode.setEnabled(false);
                            }
                            faqsCode.setPostedDate(item.getPostedDate());
                            //if(item.getExpirationDate() != null) {
                                faqsCode.setExpirationDate(checkMaxDate(item.getExpirationDate()));
                            //}
                            faqsCode.setUpdateDate(new Date());
                            faqsCode.setUpdateBy(username);                    
                            faqsRepository.save(faqsCode);
                            
                            List<FaqsLanguage> lstLang = faqsLanguageRepository.findByFaqsId(faqsCode.getId());
                            for( FaqsLanguage faqsLang : lstLang) {
                                faqsLang.setTitle(item.getTitle());
                                faqsLang.setContent(item.getContent());
                                faqsLang.setKeywordDescription(item.getKeywordsDesc());
                                faqsLang.setKeyword(item.getKeywords());
                                faqsLang.setLinkAlias(item.getKeywordsSeo());
                                faqsLang.setUpdateDate(new Date());
                                faqsLang.setUpdateBy(username);
                                faqsLanguageRepository.save(faqsLang);
                            }
                            
                            JpmProcessInstAct proAct = jpmProcessInstActRepository.findOne(process.getId());
                            JpmStatusCommonDto common = jpmStatusCommonRepository.findStatusCommon("994", "VI");
                            proAct.setCommonStatusId(common.getId());
                            proAct.setUpdatedDate(new Date());
                            proAct.setUpdatedId(userId);
                            jpmProcessInstActRepository.save(proAct);
                        }
                        
                    }
                    
                    
                }
            }
        }catch(Exception e){
            logger.error("####saveListImport####", e);
            throw new Exception(e);
        }
        
    }

    @Override
    public List<String> initHeaderTemplate() {
        List<String> result = new ArrayList<String>();
        result.add("import.faqs.id");
        result.add("import.faqs.name");
        result.add("import.faqs.code");
        result.add("import.faqs.title");
        result.add("import.faqs.keywordsdesc");
        result.add("import.faqs.content");
        result.add("import.faqs.enable");
        result.add("import.faqs.posteddate");
        result.add("import.faqs.expirationDate");
        result.add("template.header.message.error");
        return result;
    }
    public String getMaxCode(String tableName, String prefix) {
        SimpleDateFormat format = new SimpleDateFormat("YYMM");
        prefix = prefix + format.format(new Date());
        return faqsCategoryImportRepository.getMaxCode(tableName, prefix);
    }
}
