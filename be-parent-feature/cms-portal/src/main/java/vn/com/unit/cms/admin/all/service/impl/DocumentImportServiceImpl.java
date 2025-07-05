package vn.com.unit.cms.admin.all.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.admin.all.dto.DocumentParams;
import vn.com.unit.cms.admin.all.entity.DocumentCategory;
import vn.com.unit.cms.admin.all.entity.DocumentImport;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.DocImportRepository;
import vn.com.unit.cms.admin.all.repository.DocumentCategoryRepository;
import vn.com.unit.cms.admin.all.repository.DocumentImportRepository;
import vn.com.unit.cms.admin.all.service.DocumentImportService;
import vn.com.unit.cms.core.module.document.dto.DocumentImportDto;
import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.cms.core.module.document.entity.DocumentDetail;
import vn.com.unit.cms.core.module.document.entity.DocumentLanguage;
import vn.com.unit.cms.core.module.document.repository.DocumentLanguageRepository;
import vn.com.unit.cms.core.module.document.repository.DocumentRepository;
import vn.com.unit.cms.core.module.document.repository.DocumentVersionRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.DirectoryConstant;
import vn.com.unit.ep2p.core.efo.repository.EfoDocRepository;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.service.DocumentAppService;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.imp.excel.annotation.CoreReadOnlyTx;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.repository.JpmProcessInstActRepository;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmButtonDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.repository.JpmBusinessRepository;
import vn.com.unit.workflow.repository.JpmProcessDeployRepository;
import vn.com.unit.workflow.repository.JpmStatusCommonRepository;
import vn.com.unit.workflow.service.JpmButtonDeployService;

@SuppressWarnings("rawtypes")
@Service
@Qualifier(value = "documentImportServiceImpl")
@CoreReadOnlyTx
public class DocumentImportServiceImpl implements DocumentImportService {
    
    @Autowired
    private DocumentImportRepository documentImportRepository;
    
    @Autowired
    private DocumentCategoryRepository documentCategoryRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private DocumentLanguageRepository documentLanguageRepository;
    
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
    private DocImportRepository docImportRepository;
    
	@Autowired
	private DocumentVersionRepository documentVersionRepository;
	
    @Autowired
    private JpmButtonDeployService jpmButtonDeployService;
    
    @Autowired
    private DocumentAppService documentAppService;
    
    @Override
    public int countData(String sessionKey) {
        return documentImportRepository.countData(sessionKey);
    }

    @Override
    public List<DocumentImportDto> getListData(int page, String sessionKey, int sizeOfPage) {
        return documentImportRepository.findListData(page, sizeOfPage, sessionKey);
    }

    @Override
    public List<DocumentImportDto> getListDataExport(String sessionKey) {
        return documentImportRepository.findListDataExport(sessionKey);
    }

    @Override
    public int countError(String sessionKey) {
        return documentImportRepository.countError(sessionKey);
    }

    @Override
    public List<DocumentImportDto> getAllDatas(String sessionKey) {
        return documentImportRepository.findAllDatas(sessionKey);
    }

    @Override
    public Class getImportDto() {
        return DocumentImportDto.class;
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
    public void updateColumn(String sessionKey) {

        List<DocumentImport> listData = docImportRepository.findListDataImport(sessionKey);
        for(DocumentImport item : listData) {
            if(item.getTitle() != null)
            {
                item.setKeywordsSeo(FileUtil.deAccent(item.getTitle()).toLowerCase().replace(" ", "-"));
                item.setKeywords("#" + FileUtil.deAccent(item.getTitle()).toLowerCase().replace(" ",""));
            }
        }
        docImportRepository.save(listData);
    }

    @Override
    public boolean validateBusiness(String sessionKey, ImportExcelSearchDto searchDto, Map<String, Object> mapParams,
            List<DocumentImportDto> listDataValidate) {
        DocumentParams params = new DocumentParams();
        params.sessionKey = sessionKey;
        sqlManager.call("SP_IMPORT_DOCUMENT_VALID", params);
        updateColumn(sessionKey);
        return countError(sessionKey) > 0;
    }
    
    @Override
    public List<String> initHeaderTemplate() {
        List<String> result = new ArrayList<String>();
        result.add("import.doc.id");
        result.add("import.doc.name");
        result.add("import.doc.code");
        result.add("import.doc.title");
        result.add("import.doc.keywordsdesc");
        result.add("import.doc.enable");
        result.add("import.doc.physical.file.name");
        result.add("import.doc.posteddate");
        result.add("import.doc.expirationDate");
        result.add("template.header.message.error");
        return result;
    }
    public String getMaxCode(String tableName, String prefix) {
        SimpleDateFormat format = new SimpleDateFormat("yyMM");
        prefix = prefix + format.format(new Date());
        return documentImportRepository.getMaxCode(tableName, prefix);
    }
	private void moveFileDoc(DocumentImportDto importDto,String documentCode, String cateCode) throws IOException {		
		String tempImageUrl = importDto.getPhysicalFileName();
		
		String fileName = tempImageUrl.substring(tempImageUrl.lastIndexOf("\\") + 1);
		String folderSource = tempImageUrl.substring(0,tempImageUrl.lastIndexOf("\\") + 1);
		
		
		String folderTarget = DirectoryConstant.DOCUMENT_FOLDER.concat(documentCode) + File.separatorChar + cateCode + File.separatorChar;
		
        String pathTarget = Paths.get(CmsUtils.getPathMain(), folderTarget).toString() + File.separatorChar;

        // create directory taget if not exists
        CmsUtils.createDirectoryNotExists(pathTarget);

		if (StringUtils.isNotEmpty(tempImageUrl)) {
			FileUtil.moveFile(fileName, folderSource,pathTarget);
			importDto.setPhysicalFileName(folderTarget.concat(fileName));
			importDto.setFileName(fileName);
		}
		
	}

    @SuppressWarnings("unchecked")
	@Override
    public void saveListImport(List<DocumentImportDto> listDataSave, String sessionKey, Locale locale, String username)
            throws Exception {
        try {
            Date maxDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/9999");
            long userId = UserProfileUtils.getAccountId();
            SimpleDateFormat f = new SimpleDateFormat(systemConfig.getConfig(SystemConfig.DATE_PATTERN));
           //get list data import by session key
            List<DocumentImportDto> list = documentImportRepository.findAllDatas(sessionKey);
            for(DocumentImportDto item : list) {
             // init process
                DocumentActionReq action = new DocumentActionReq();
                
                JpmBusinessDto businessDto = jpmBusinessRepository.getBusinessDtoByCodeAndCompanyId("BUSINESS_CMS",new Long(2));
                action.setBusinessId(businessDto.getBusinessId());
                
                JpmProcessDeployDto processDeploy = jpmProcessDeployRepository.getJpmProcessDeployLasted(new Long(2),businessDto.getBusinessId(),new Date());
                action.setProcessDeployId(processDeploy.getProcessDeployId());
                action.setProcessDeployCode(processDeploy.getProcessCode());
                action.setButtonText("SUBMIT");
                action.setProcessType(3);
                action.setDocInputJson("{skip: 1}");
                JpmButtonDeployDto btnDto = jpmButtonDeployService
                        .getJpmButtonDeployDtoByButtonTextAndProcessDeployId(action.getButtonText(), processDeploy.getProcessDeployId());
                action.setButtonId(btnDto.getButtonDeployId());
            	action = documentAppService.action(action, locale);
                DocumentCategory cat = documentCategoryRepository.findByCode(item.getCode());
                if(item.getDocumentCode() == null) {
                    
                    //save entity m_faq
                    Document doc  = new Document();
                    doc.setCategoryId(cat.getId());
                    if(item.getCode() != null) {
                        doc.setCode(CommonUtil.getNextBannerCode(item.getCode().substring(0,3),getMaxCode("M_DOCUMENT", item.getCode().substring(0,3))));
                    }
                    if(item.getEnabled() !=null && item.getEnabled().equals("x"))
                    {
                        doc.setEnabled(true);
                    }else {
                        doc.setEnabled(false);
                    }
                    doc.setPostedDate(item.getPostedDate());
                    if(item.getExpirationDate() != null) {
                        doc.setExpirationDate(item.getExpirationDate());
                    }
                    else if(item.getExpirationDate() == null) {
                        doc.setExpirationDate(maxDate);
                    }
                    doc.setCreateDate(new Date());
                    doc.setCreateBy(username);    
                    doc.setDocId(ObjectUtils.isNotEmpty(action.getFormId()) ? action.getFormId() : action.getDocId());
                    documentRepository.save(doc);
                    //save language VI
                    DocumentLanguage docLang = new DocumentLanguage();
                    docLang.setDocumentId(doc.getId());
                    docLang.setLanguageCode("VI");
                    docLang.setTitle(item.getTitle());
                    docLang.setKeywordDescription(item.getKeywordsDesc());
                    docLang.setKeyword(item.getKeywords());
                    docLang.setLinkAlias(item.getKeywordsSeo());
                    docLang.setCreateDate(new Date());
                    docLang.setCreateBy(username);
                    documentLanguageRepository.save(docLang);
                    //save language EN
                    DocumentLanguage docLangee = new DocumentLanguage();
                    docLangee.setDocumentId(doc.getId());
                    docLangee.setLanguageCode("EN");
                    docLangee.setTitle(item.getTitle());
                    docLangee.setKeywordDescription(item.getKeywordsDesc());
                    docLangee.setKeyword(item.getKeywords());
                    docLangee.setLinkAlias(item.getKeywordsSeo());
                    docLangee.setCreateDate(new Date());
                    docLangee.setCreateBy(username);
                    documentLanguageRepository.save(docLangee);
                    

                    String pathFile = Paths.get(item.getPhysicalFileName(), "").toString();
                    File file = new File(pathFile);
                    if(file.exists()) {
                    	moveFileDoc(item,doc.getCode(),item.getCode());
                        
                        DocumentDetail docDetail = new DocumentDetail();
                        docDetail.setDocumentId(doc.getId());
                        docDetail.setVersion(1);
                        docDetail.setCurrentVersion(true);
                        if(item.getPhysicalFileName() !=null ) {
                        	docDetail.setPhysicalFileName(item.getPhysicalFileName());
                        	docDetail.setFileName(item.getFileName());
                        }
                        docDetail.setCreateBy(username);
                        docDetail.setCreateDate(new Date());
                        documentVersionRepository.save(docDetail);
                    }
                      
                     
                } else {
                    Document doc  = documentImportRepository.findDocumentByCategoryAndCode(cat.getId(),item.getDocumentCode());
                    if(doc !=null) {
                        JpmProcessInstActDto process = jpmProcessInstActRepository.getJpmProcessInstActDtoByReference(doc.getDocId(),1);
                        JpmStatusCommonDto commonDto = jpmStatusCommonRepository.findStatusCommon("000", "VI");
                        if(process.getCommonStatusId().equals(commonDto.getId()))
                        {

                            if(item.getEnabled() !=null && item.getEnabled().equals("x"))
                            {
                                doc.setEnabled(true);
                            }else {
                                doc.setEnabled(false);
                            }
                            doc.setPostedDate(item.getPostedDate());
                            if(item.getExpirationDate() != null) {
                                doc.setExpirationDate(item.getExpirationDate());
                            }
                            else if(item.getExpirationDate() == null) {
                                doc.setExpirationDate(maxDate);
                            }
                            doc.setUpdateDate(new Date());
                            doc.setUpdateBy(username);   
                            doc.setDocId(ObjectUtils.isNotEmpty(action.getFormId()) ? action.getFormId() : action.getDocId());
                            documentRepository.save(doc);
                            
                            List<DocumentLanguage> lstLang = documentLanguageRepository.findByDocumentId(doc.getId());
                            for( DocumentLanguage docLang : lstLang) {
                                docLang.setTitle(item.getTitle());
                                docLang.setKeywordDescription(item.getKeywordsDesc());
                                docLang.setKeyword(item.getKeywords());
                                docLang.setLinkAlias(item.getKeywordsSeo());
                                docLang.setUpdateDate(new Date());
                                docLang.setUpdateBy(username);
                                documentLanguageRepository.save(docLang);
                            }
                        }
                        
                        String pathFile = Paths.get(item.getPhysicalFileName(), "").toString();
                        File file = new File(pathFile);
                        if(file.exists()) {
                        	Integer currentVersion = documentVersionRepository
    								.findCurrentVersionByDocumentId(doc.getId());
    						documentVersionRepository.updateAllToOldVersionByDocumentId(doc.getId());
    						
    	                    moveFileDoc(item,doc.getCode(),item.getCode());
    	                    
                            DocumentDetail docDetail = new DocumentDetail();
                            docDetail.setDocumentId(doc.getId());
                            docDetail.setVersion(currentVersion != null ? currentVersion + 1 : 1);
                            docDetail.setCurrentVersion(true);
                            if(item.getPhysicalFileName() !=null ) {
                            	docDetail.setPhysicalFileName(item.getPhysicalFileName());
                            	docDetail.setFileName(item.getFileName());
                            }
                            docDetail.setCreateBy(username);
                            docDetail.setCreateDate(new Date());
                            documentVersionRepository.save(docDetail);
                        }
                        
                        
                        
                    }else {
                        
                        Document docCode  = documentImportRepository.findDocumentByCode(item.getDocumentCode());
                        
                        JpmProcessInstActDto process = jpmProcessInstActRepository.getJpmProcessInstActDtoByReference(docCode.getDocId(),1);
                        JpmStatusCommonDto commonDto = jpmStatusCommonRepository.findStatusCommon("000", "VI");
                        if(process.getCommonStatusId().equals(commonDto.getId()))
                        {
                            docCode.setCategoryId(cat.getId());
                            if(item.getEnabled() !=null && item.getEnabled().equals("x"))
                            {
                                docCode.setEnabled(true);
                            }else {
                                docCode.setEnabled(false);
                            }
                            docCode.setPostedDate(item.getPostedDate());
                            if(item.getExpirationDate() != null) {
                                docCode.setExpirationDate(item.getExpirationDate());
                            }
                            else if(item.getExpirationDate() == null) {
                                docCode.setExpirationDate(maxDate);
                            }
                            docCode.setUpdateDate(new Date());
                            docCode.setUpdateBy(username);
                            docCode.setDocId(ObjectUtils.isNotEmpty(action.getFormId()) ? action.getFormId() : action.getDocId());
                            documentRepository.save(docCode);
                            
                            List<DocumentLanguage> lstLang = documentLanguageRepository.findByDocumentId(docCode.getId());
                            for( DocumentLanguage docLang : lstLang) {
                                docLang.setTitle(item.getTitle());
                                docLang.setKeywordDescription(item.getKeywordsDesc());
                                docLang.setKeyword(item.getKeywords());
                                docLang.setLinkAlias(item.getKeywordsSeo());
                                docLang.setUpdateDate(new Date());
                                docLang.setUpdateBy(username);
                                documentLanguageRepository.save(docLang);
                            }
                        }
                        
                        String pathFile = Paths.get(item.getPhysicalFileName(), "").toString();
                        File file = new File(pathFile);
                        if(file.exists()) {
                        	Integer currentVersion = documentVersionRepository
    								.findCurrentVersionByDocumentId(docCode.getId());
    						documentVersionRepository.updateAllToOldVersionByDocumentId(docCode.getId());
    						
    						
    	                    moveFileDoc(item,docCode.getCode(),item.getCode());
    	                    
                            DocumentDetail docDetail = new DocumentDetail();
                            docDetail.setDocumentId(docCode.getId());
                            docDetail.setVersion(currentVersion != null ? currentVersion + 1 : 1);
                            docDetail.setCurrentVersion(true);
                            if(item.getPhysicalFileName() !=null ) {
                            	docDetail.setPhysicalFileName(item.getPhysicalFileName());
                            	docDetail.setFileName(item.getFileName());
                            }
                            docDetail.setCreateBy(username);
                            docDetail.setCreateDate(new Date());
                            documentVersionRepository.save(docDetail);
                        }
                           
                    }
                }
            }
        }catch(Exception e){
            logger.error("####saveListImport####", e);
            throw new Exception(e);
        }
    }
    
}
