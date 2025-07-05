/*******************************************************************************
 * Class        ：DocumentMainFileServiceImpl
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：tantm
 * Change log   ：2021/01/20：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.ers.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonFileUtil;
import vn.com.unit.common.utils.CommonMultipartFile;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.common.utils.CommonPasswordUtil;
import vn.com.unit.common.utils.CommonPdfUltil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.config.SystemSettingKey;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.enumdef.DocumentActionFlag;
import vn.com.unit.core.enumdef.DocumentState;
import vn.com.unit.core.req.ReqSaveOzdFile;
import vn.com.unit.core.res.ResSaveOZDFile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaCompanyService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.db.service.SqlManagerService;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.constant.AppSystemSettingKey;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileDto;
import vn.com.unit.ep2p.core.efo.entity.EfoDoc;
import vn.com.unit.ep2p.core.efo.entity.EfoForm;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocMainFile;
import vn.com.unit.ep2p.core.efo.service.EfoDocService;
import vn.com.unit.ep2p.core.efo.service.EfoFormService;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocMainFileService;
import vn.com.unit.ep2p.core.res.dto.DocumentMainFileUploadReq;
import vn.com.unit.ep2p.core.service.DocumentMainFileService;
import vn.com.unit.ep2p.core.service.RestFullApiService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.service.JpmProcessInstActService;

/**
 * DocumentMainFileServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DocumentMainFileServiceImpl implements DocumentMainFileService {

    @Autowired
    private JCommonService commonService;
    
    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerService sqlManagerService;
    
    @Autowired
    @Qualifier("appSystemConfigServiceImpl")
    private JcaSystemConfigService jcaSystemConfigService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JcaCompanyService jcaCompanyService;

    @Autowired
    private EfoOzDocMainFileService efoOzDocMainFileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EfoDocService efoDocService;

    @Autowired
    private EfoFormService efoFormService;
    
    @Autowired
    private RestFullApiService restFullApiService;
    
    @Autowired
    SystemConfig systemConfig;
    
    @Autowired
    JpmProcessInstActService jpmProcessInstActService;

    private static final String MINE_PDF = "application/pdf";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EfoOzDocMainFileDto uploadMainFile(DocumentMainFileUploadReq documentSaveReq) throws Exception {
        EfoOzDocMainFileDto resDto = new EfoOzDocMainFileDto();
//        Date sysDate = commonService.getSystemDate();

        Long mainFileId = documentSaveReq.getMainFileId();
        String fileName = documentSaveReq.getFileName();
        Long formId = documentSaveReq.getFormId();
        Long docId = documentSaveReq.getDocId();
        Long docIdTmp = docId;

        if (docId == null) {
            docIdTmp = sqlManagerService.getNextValBySeqName(CoreConstant.SEQ + AppCoreConstant.TABLE_EFO_DOC);
        }

        /** === convert to ozd === */
        String systemCode = "HDBank";// UserProfileUtils.getUserProfile().getSystemCode();
        Long companyId = 1L; // UserProfileUtils.getCompanyId();
        MultiValueMap<String, Object> requestObj = new LinkedMultiValueMap<>();

        Path tempFile = Files.createTempFile("ppl_upload", ".pdf");

        // String baseUrl = systemConfig.getConfig(SystemConfig.URL_DEFAULT);
        // String docUrl = this.buildUrlDetailDoc(docIdTmp, baseUrl);
        // byte[] byteArray = PdfUltils.addHyperlinkAtFooter(file.getBytes(), "TT Số ".concat(String.valueOf(docIdTmp)), docUrl, new Date(),
        // resourceLoader);
        // if (null == byteArray) {
        // byteArray = file.getBytes();
        // }

        byte[] byteArray = CommonBase64Util.decodeToByte(documentSaveReq.getFileBase64());
        Files.write(tempFile, byteArray);
        FileSystemResource fileSysRes = new FileSystemResource(tempFile.toFile());

        requestObj.add("file", fileSysRes);
        requestObj.add("systemCode", systemCode);

        byte[] ozdBytes = null;
        try {
            String urlRepo = systemConfig.getConfig(AppSystemSettingKey.OZ_REPOSITORY_LOCAL_URL,companyId);
            String url = urlRepo.concat(AppCoreConstant.URL_CONSTANT_REPO_FORM.substring(1).concat(AppCoreConstant.URL_CONSTANT_REPO_CONVERT_OZD));
            
            // Call API to repository server
            ozdBytes = restFullApiService.uploadRestFull(url , requestObj);
        } catch (Exception e) {
            throw new DetailException("Call api upload fail.");
        } finally {
            fileSysRes.getOutputStream().close();
            Files.delete(tempFile);
        }
        /** === ooo === */

        EfoOzDocMainFileDto mainFileDto = new EfoOzDocMainFileDto();

        DocumentActionFlag actionFlag = mainFileId == null ? DocumentActionFlag.CREATE_DATA : DocumentActionFlag.REPLACE_DATA;
        if (null == mainFileId) {
            mainFileDto.setDocId(docIdTmp);
        } else {
            mainFileDto = efoOzDocMainFileService.getEfoOzDocMainFileDtoById(mainFileId);
        }

        CommonObjectUtil.copyPropertiesNonNull(documentSaveReq, mainFileDto);

        String docName = efoOzDocMainFileService.generateDocFileName(docIdTmp, 1L, 0L, false, true);
        String fileStream = Base64.getEncoder().encodeToString(ozdBytes);

        mainFileDto.setMainFileNameView(docName);
        mainFileDto.setFileStream(Arrays.asList(fileStream));

        /** get type size pdf with A4 or default is null */
        String isSizeA4 = CommonPdfUltil.getPageSize(fileName, byteArray);
        if (StringUtils.isNotBlank(isSizeA4) && CommonPdfUltil.SIZE_A4.equals(isSizeA4)) {
            mainFileDto.setPdfTypeSize(isSizeA4);
        } else {
            mainFileDto.setPdfTypeSize(null);
        }

        /** TaiTT add save upload pdf to version first when upload */
        Long pdfMajorVersion = mainFileDto.getPdfMajorVersion();
        Long pdfMinorVersion = mainFileDto.getPdfMinorVersion();
        switch (actionFlag) {
        case CREATE_DATA:
        case UPDATE_DATA:
        case REPLACE_DATA:
            if (null == pdfMajorVersion || null == pdfMinorVersion) {
                pdfMajorVersion = Long.valueOf(CoreConstant.NUMBER_ONE);
                pdfMinorVersion = Long.valueOf(CoreConstant.NUMBER_ZERO);
            } else {
                pdfMinorVersion += 1;
            }
            break;
        default:

            break;
        }
        /** END */

        /** === up file pdf to server === */
        String fileNamePdf = efoOzDocMainFileService.generateDocFileName(docIdTmp, pdfMajorVersion, pdfMinorVersion, false, false);
        MultipartFile pdfFile = new CommonMultipartFile(fileNamePdf, fileNamePdf, MINE_PDF, byteArray);
        Map<String, String> mapResultPdf = this.uploadFilePdf(pdfFile, formId, companyId);
        String pdfId = mapResultPdf.get("pdfId");
        Long repoIdPdf = Long.valueOf(mapResultPdf.get("repoIdPdf"));

        mainFileDto.setPdfFilePath(pdfId);
        mainFileDto.setPdfRepositoryId(repoIdPdf);
        mainFileDto.setPdfMajorVersion(pdfMajorVersion);
        mainFileDto.setPdfMinorVersion(pdfMinorVersion);
//        mainFileDto.setLatestUploadDate(sysDate);

        resDto = this.saveOzDocMainFile(mainFileDto, actionFlag, true, false);

        resDto.setDocIdTmp(docIdTmp);
        resDto.setDocCode(commonService.generateCodeFromId(docIdTmp));

        return resDto;
    }
    
    @Override
    public EfoOzDocMainFileDto buildEfoOzDocMainFileDto(EfoDocDto efoDocDto) {
        List<String> fileStream = efoDocDto.getFileStream();
        Long mainFileId = efoDocDto.getMainFileId();
        Long formId = efoDocDto.getFormId();
        Long docId = efoDocDto.getDocId();
        Long docIdTmp = docId;

        if (docId == null) {
            docIdTmp = sqlManagerService.getNextValBySeqName(CoreConstant.SEQ + AppCoreConstant.TABLE_EFO_DOC);
        }

        EfoOzDocMainFileDto mainFileDto = new EfoOzDocMainFileDto();
        if (null == mainFileId) {
            mainFileDto.setDocIdTmp(docIdTmp);
            mainFileDto.setDocCode(commonService.generateCodeFromId(docIdTmp));
            String docName = efoOzDocMainFileService.generateDocFileName(docIdTmp, 1L, 0L, false, true);
            mainFileDto.setMainFileNameView(docName);
            mainFileDto.setFormId(formId);
        } else {
            mainFileDto = efoOzDocMainFileService.getEfoOzDocMainFileDtoById(mainFileId);
        }
        mainFileDto.setFileStream(fileStream);
        return mainFileDto;
    }

    @Override
    public EfoOzDocMainFileDto saveOzDocMainFile(EfoOzDocMainFileDto mainFileDto, DocumentActionFlag actionFlag, boolean isIncreaseVersion,
            boolean isMajor)
            throws Exception {
        // Build data and upload file ozd
        EfoOzDocMainFile efoOzDocMainFile = this.buildEfoOzDocMainFileAndUploadFileOzd(mainFileDto, actionFlag, isIncreaseVersion, isMajor);

        // And save data to DB
        efoOzDocMainFile.setOzDocId(efoOzDocMainFile.getDocId()); //TODO
        EfoOzDocMainFileDto resDto = efoOzDocMainFileService.saveEfoOzDocMainFile(efoOzDocMainFile, actionFlag, isIncreaseVersion, isMajor);
        return resDto;
    }

    private EfoOzDocMainFile buildEfoOzDocMainFileAndUploadFileOzd(EfoOzDocMainFileDto ozDocMainFileDto, DocumentActionFlag actionFlag,
            boolean isIncreaseVersion, boolean isMajor) throws Exception {

        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Date sysDate = commonService.getSystemDate();
        Long id = ozDocMainFileDto.getId();
        Long docId = ozDocMainFileDto.getDocId();
        EfoOzDocMainFile saveObj = new EfoOzDocMainFile();

        EfoDoc doc = efoDocService.getEfoDocById(docId);
        
        int refType = 1;
        JpmProcessInstActDto instActDto = jpmProcessInstActService.getJpmProcessInstActDtoByReference(docId, refType);
        String docState = (instActDto == null) ? DocumentState.DRAFT.toString() : instActDto.getCommonStatusCode();

        String uploadedFileNamePdf = ozDocMainFileDto.getPdfFilePath();

        switch (actionFlag) {
        case CREATE_DATA:
            if (null != docId && null == id) {
                if (doc != null) {
                    throw new DetailException("Error create main-file with #docId=" + docId);
                }
            }

            saveObj = objectMapper.convertValue(ozDocMainFileDto, EfoOzDocMainFile.class);
            Long major = ozDocMainFileDto.getMajorVersion();
            Long minor = 0L;

            if (null == id) {
                major = 0L;
            } else {
                major += 1;
            }

            saveObj.setMajorVersion(major);
            saveObj.setMinorVersion(minor);
            saveObj.setCreatedId(userId);
            saveObj.setCreatedDate(sysDate);
            saveObj.setUpdatedId(userId);
            saveObj.setUpdatedDate(sysDate);

            String fileName = saveObj.getMainFileNameView();
            fileName = fileName.substring(0, fileName.lastIndexOf(CoreConstant.DOT));

            saveObj.setMainFileName(fileName);
            ozDocMainFileDto.setMainFileName(fileName);
            
            saveObj.setDocId(null == docId ? ozDocMainFileDto.getDocIdTmp() : docId);
            
            break;
        case UPDATE_DATA:
            saveObj = efoOzDocMainFileService.getEfoOzDocMainFileById(id);
            /** set version from db */
            ozDocMainFileDto.setMajorVersion(saveObj.getMajorVersion());
            ozDocMainFileDto.setMinorVersion(saveObj.getMinorVersion());

            saveObj.setOzInputJson(ozDocMainFileDto.getOzInputJson());
            saveObj.setOzValidJson(ozDocMainFileDto.getOzValidJson());
            saveObj.setUpdatedId(userId);
            saveObj.setUpdatedDate(sysDate);
            String fileNameReplace = ozDocMainFileDto.getMainFileNameView();
            if (DocumentState.DRAFT.toString().equals(docState) && !fileNameReplace.equals(saveObj.getMainFileNameView())) {

                fileNameReplace = fileNameReplace.substring(0, fileNameReplace.lastIndexOf(CoreConstant.DOT));

                saveObj.setMainFileName(fileNameReplace);
                ozDocMainFileDto.setMainFileName(fileNameReplace);
            } else {
                ozDocMainFileDto.setMainFileName(saveObj.getMainFileName());
            }
            ozDocMainFileDto.setFormId(saveObj.getFormId());

            // keep file PDF when upload and submit draft
            if (DocumentState.DRAFT.toString().equals(docState)) {
                uploadedFileNamePdf = saveObj.getPdfFilePath();
            }
            break;
        case REVERT_DATA:

            break;
        case REPLACE_DATA:
            saveObj = efoOzDocMainFileService.getEfoOzDocMainFileById(id);
            saveObj.setUpdatedId(userId);
            saveObj.setUpdatedDate(sysDate);
            saveObj.setPdfTypeSize(ozDocMainFileDto.getPdfTypeSize());
//            saveObj.setLatestUploadDate(ozDocMainFileDto.getLatestUploadDate());
            saveObj.setPdfMajorVersion(ozDocMainFileDto.getPdfMajorVersion());
            saveObj.setPdfMinorVersion(ozDocMainFileDto.getPdfMinorVersion());

            fileName = saveObj.getMainFileNameView();
            fileName = fileName.substring(0, fileName.lastIndexOf(CoreConstant.DOT));

            saveObj.setMainFileName(fileName);
            ozDocMainFileDto.setMainFileName(fileName);
            break;
        default:

            break;
        }

        /** change rule save main file version on repository server */
        Long major = saveObj.getMajorVersion();
        Long minor = saveObj.getMinorVersion();

        if (isMajor) {
            major += 1L;
            minor = 0L;
        } else {
            minor += 1L;
        }

        ozDocMainFileDto.setMajorVersion(major);
        ozDocMainFileDto.setMinorVersion(minor);

        if (isIncreaseVersion) {
            saveObj.setMajorVersion(major);
            saveObj.setMinorVersion(minor);
        }
        if(CommonCollectionUtil.isNotEmpty(ozDocMainFileDto.getFileStream())){
            this.uploadFileOzd(ozDocMainFileDto);
        }
        Long ecmRepositoryId = ozDocMainFileDto.getMainFileRepoId();
        String formFileName = ozDocMainFileDto.getMainFilePath();
        String fileNamePdf = ozDocMainFileDto.getPdfFilePath();
        String fileNameView = ozDocMainFileDto.getMainFileNameView();
        String fileName = ozDocMainFileDto.getMainFileName();

        saveObj.setMainFileRepoId(ecmRepositoryId);
        saveObj.setMainFilePath(formFileName);

        // keep PDF uploaded by user
        if (CommonStringUtil.isNotBlank(uploadedFileNamePdf)) {
            fileNamePdf = uploadedFileNamePdf;
        }

        saveObj.setPdfFilePath(fileNamePdf);
        saveObj.setMainFileNameView(fileNameView);
        saveObj.setMainFileName(fileName);

        // set history file name
        EfoForm form = efoFormService.getFormById(saveObj.getFormId());
        String historyFileName = form.getOzAppendFilePath();
        String typeSize = saveObj.getPdfTypeSize();
        if (CommonStringUtil.isNotBlank(typeSize) && CommonStringUtil.isNotBlank(historyFileName)) {
            String fullPath = CommonFileUtil.getFullPath(historyFileName);
            String baseName = CommonFileUtil.getBaseName(historyFileName);
            String ext = CommonFileUtil.getExtension(historyFileName);

            StringBuilder fileNameBuilder = new StringBuilder(fullPath);
            fileNameBuilder.append(baseName);
            fileNameBuilder.append(CoreConstant.UNDERSCORE);
            fileNameBuilder.append(typeSize);
            fileNameBuilder.append(CoreConstant.DOT);
            fileNameBuilder.append(ext);
            historyFileName = fileNameBuilder.toString();
        }
        saveObj.setOzAppendFilePath(historyFileName);

        return saveObj;
    }

    private Map<String, String> uploadFilePdf(MultipartFile file, Long formId, Long companyId) throws Exception {
        String formFileName = null;
        Map<String, String> mapResultUploadPdf = new HashMap<>();
        String systemCode = jcaCompanyService.getSystemCodeByCompanyId(companyId);
        String subFilePath = "files_storage/".concat(systemCode);

        // fileupload
        FileUploadParamDto param = new FileUploadParamDto();
        param.setFileByteArray(file.getBytes());
        param.setFileName(file.getOriginalFilename());
        param.setRename(null);

        param.setTypeRule(2);
        param.setDateRule(null);
        param.setSubFilePath(subFilePath);
        param.setCompanyId(companyId);

        param.setRepositoryId(Long.valueOf(jcaSystemConfigService.getValueByKey(SystemConfig.REPO_DOCUMENT, companyId)));

        FileUploadResultDto fileResultDto = fileStorageService.upload(param);

        formFileName = fileResultDto.getFilePath();
        Long repoIdPdf = fileResultDto.getRepositoryId();
        mapResultUploadPdf.put("pdfId", formFileName);
        mapResultUploadPdf.put("repoIdPdf", repoIdPdf.toString());
        return mapResultUploadPdf;
    }

    private void uploadFileOzd(EfoOzDocMainFileDto ozDocMainFileDto) throws Exception {
        Long companyId = ozDocMainFileDto.getCompanyId();
        if (null == companyId) {
            companyId = UserProfileUtils.getCompanyId();
        }

        boolean useECM = false;
        // Check using ECM to storage
        if (Integer.parseInt(jcaSystemConfigService.getValueByKey(SystemConfig.FLAG_USED_ECM, companyId)) > 0) {
            useECM = true;
        }

        try {
            if (useECM) { // ECM SERVER
                Long ecmRepositoryId = ozDocMainFileDto.getMainFileRepoId();

                if (ecmRepositoryId == null) {
                    String ecmRepositoryDocument = jcaSystemConfigService.getValueByKey(SystemConfig.REPO_DOCUMENT, companyId);

                    if (CommonStringUtil.isEmpty(ecmRepositoryDocument)) {
                        throw new DetailException("ECM_REPOSITORY_DOCUMENT is not value.");
                    }

                    ecmRepositoryId = Long.parseLong(ecmRepositoryDocument);
                }
                String fileStream = ozDocMainFileDto.getFileStream().get(0);
                byte[] ecmContent = Base64.getDecoder().decode(fileStream.substring(fileStream.indexOf(CoreConstant.COMMA) + 1));
                String fileNameOZD = ozDocMainFileDto.getMainFileName().concat(CoreConstant.EXTEND_OZD);

                String systemCode = jcaCompanyService.getSystemCodeByCompanyId(companyId);
                String subFilePath = "files_storage/".concat(systemCode);

                // fileupload
                FileUploadParamDto param = new FileUploadParamDto();
                param.setFileByteArray(ecmContent);
                param.setFileName(fileNameOZD);
                param.setRename(null);

                param.setTypeRule(2);
                param.setDateRule(null);
                param.setSubFilePath(subFilePath);
                param.setCompanyId(companyId);

                param.setRepositoryId(Long.valueOf(jcaSystemConfigService.getValueByKey(SystemConfig.REPO_DOCUMENT, companyId)));

                FileUploadResultDto fileResultDto = fileStorageService.upload(param);

                ozDocMainFileDto.setMainFilePath(fileResultDto.getFilePath());
                ozDocMainFileDto.setMainFileRepoId(fileResultDto.getRepositoryId());

            } else { // LOCAL SERVER
                // call API to save ozd
                String ozUrl = jcaSystemConfigService.getValueByKey(SystemSettingKey.OZ_REPOSITORY_LOCAL_URL, companyId);
                String url = ozUrl.concat(CoreConstant.API_V1_DOC_SAVE_OZD);

                String fileName = ozDocMainFileDto.getMainFileName();
                String fileNameView = ozDocMainFileDto.getMainFileNameView();
                Long docId = ozDocMainFileDto.getDocId();
                if (Objects.nonNull(docId)) {
                    Long major = ozDocMainFileDto.getMajorVersion();
                    Long minor = ozDocMainFileDto.getMinorVersion();
                    fileNameView = efoOzDocMainFileService.generateDocFileName(docId, major, minor, false, true);
                    fileName = FilenameUtils.getBaseName(fileNameView);
                }

                ReqSaveOzdFile reqSaveOzdFile = new ReqSaveOzdFile();
                reqSaveOzdFile.setFileStream(ozDocMainFileDto.getFileStream());
                reqSaveOzdFile.setFileStreamName(ozDocMainFileDto.getFileStreamName());
                reqSaveOzdFile.setFormId(ozDocMainFileDto.getFormId());
                reqSaveOzdFile.setFileName(fileName);
                reqSaveOzdFile.setId(ozDocMainFileDto.getId());

                // reqSaveOzdFile.setFormFileName(ozDocMainFileDto.getFormFileName());
                reqSaveOzdFile.setFormFileName(null);

                String systemCode = jcaCompanyService.getSystemCodeByCompanyId(companyId);
                reqSaveOzdFile.setSystemCode(systemCode);
                reqSaveOzdFile.setCreatedDate(CommonDateUtil.formatDateToString(commonService.getSystemDate(), CoreConstant.YYYYMMDD));

                reqSaveOzdFile.setOzServerUrl(jcaSystemConfigService.getValueByKey(SystemSettingKey.OZ_REPOSITORY_LOCAL_URL, companyId));
                reqSaveOzdFile.setOzUser(jcaSystemConfigService.getValueByKey(SystemSettingKey.OZ_REPOSITORY_USER, companyId));
                String password = jcaSystemConfigService.getValueByKey(SystemSettingKey.OZ_REPOSITORY_PASSWORD, companyId);
                reqSaveOzdFile.setOzPassword(CommonPasswordUtil.decryptString(password));

                ResSaveOZDFile resSaveOzd = restFullApiService.restFull(url, reqSaveOzdFile, ResSaveOZDFile.class);
                if (APIStatus.SUCCESS.toString().equals(resSaveOzd.getStatus())) {
                    String formFileName = resSaveOzd.getResultObj().getFilePath();
                    ozDocMainFileDto.setMainFileNameView(fileNameView);
                    ozDocMainFileDto.setMainFileName(fileName);
                    ozDocMainFileDto.setMainFilePath(formFileName);
                    ozDocMainFileDto.setPdfFilePath(null);
                } else {
                    throw new DetailException(resSaveOzd.getObjErrors().get(0).getErrorCode());
                }
            }

        } catch (Exception e) {
            throw new DetailException(e.getMessage());
        }

    }

    @Override
    public FileDownloadResult getOZDocMainFileById(Long id, Long docMainFileHistoryId, boolean isOZD, Locale lang) throws DetailException {
        FileDownloadResult fileDownloadResult = new FileDownloadResult();

        EfoOzDocMainFileDto mainFile = null;
        if (docMainFileHistoryId != null) {
            mainFile = efoOzDocMainFileService.getEfoOzDocMainFileDtoByVersionId(docMainFileHistoryId);
            if (mainFile == null) {
                throw new DetailException("docMainFileHistoryId " + docMainFileHistoryId + " is not found");
            }
        } else if (id != null) {
            mainFile = efoOzDocMainFileService.getEfoOzDocMainFileDtoById(id);
            if (mainFile == null) {
                throw new DetailException("docMainFileId " + id + " is not found");
            }
        }
        Long companyIdForDoc = mainFile.getCompanyId();
        byte[] contentBytes = null;
        String formFileName = null;
        String mimeFile = null;
        Long repoId = null;
        if (isOZD) {
            formFileName = mainFile.getMainFilePath();
            mimeFile = ".ozd";
            repoId = mainFile.getMainFileRepoId();
        } else {
            formFileName = mainFile.getPdfFilePath();
            mimeFile = ".pdf";
            repoId = mainFile.getPdfRepositoryId();
        }
        
        try {
            // If pdf file is not exist, it call exportPdfByOzd. Else download form filestorage
            if (!isOZD && StringUtils.isEmpty(formFileName)) {
                String ozFileId = mainFile.getMainFilePath();
                contentBytes = exportPdfByOzdAtOZServer(ozFileId, companyIdForDoc);
            }else {   
                contentBytes = this.downloadPDFAtOZServer(formFileName,repoId);   
            }
            String fileName = mainFile.getMainFileNameView();

            if (!fileName.endsWith(mimeFile)) {
                fileName = fileName.substring(0, fileName.lastIndexOf(AppApiConstant.DOT)).concat(mimeFile);
            }
            
            fileDownloadResult.setFileByteArray(contentBytes);
            fileDownloadResult.setFileName(fileName);
        }catch (Exception e) {
            
        }


        return fileDownloadResult;
    }
    
    private byte[] exportPdfByOzdAtOZServer(String formFileName, Long companyId) throws Exception {
        String repositoryURL = systemConfig.getConfig(SystemSettingKey.OZ_REPOSITORY_LOCAL_URL, companyId);
        String url = getRepositoryURLDownloadPDF(repositoryURL);
        //Map<String, String> param = new LinkedHashMap<>()
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("formFileName", formFileName);
        
        //TRIEUVD_28042020_SET OZ PARAM     
        param.add(AppCoreConstant.REPO_PARAM_OZ_SERVER_URL, systemConfig.getConfig(SystemSettingKey.OZ_REPOSITORY_LOCAL_URL, companyId));
        param.add(AppCoreConstant.REPO_PARAM_OZ_USER, systemConfig.getConfig(SystemSettingKey.OZ_REPOSITORY_USER, companyId));
        param.add(AppCoreConstant.REPO_PARAM_OZ_PASSWORD, systemConfig.getConfigDecrypted(SystemSettingKey.OZ_REPOSITORY_PASSWORD, companyId));
        byte[] pdfBytes = null;
        try {
            pdfBytes = restFullApiService.uploadRestFull(url, param); 
        }catch (Exception e) {
         
        }
               
        
        return pdfBytes;
    }
    
    private String getRepositoryURLDownloadPDF(String repositoryURL)throws DetailException {
        String result = repositoryURL;

        if (StringUtils.isEmpty(result)) {
            throw new DetailException("OZ_REPOSITORY_URL is not value.");
        }

        String lastCharacter = result.substring(result.length() - 1);
        if (!AppApiConstant.SLASH.equals(lastCharacter)) {
            result = result.concat(AppApiConstant.SLASH).concat(AppCoreConstant.URL_CONSTANT_REPO_DOC);
        }

        int usedECM = systemConfig.getIntConfig(SystemConfig.FLAG_USED_ECM);
        if (usedECM > 0) {
            result = result.concat(AppCoreConstant.URL_CONSTANT_REPO_DOC_DOWNLOAD_PDF_ECM_INCLUDE_OZR);
        } else {
            result = result.concat(AppCoreConstant.URL_CONSTANT_URL_DOC_DOWNLOAD_PDF);
        }
            
        return result;
    }
    
    private byte[] downloadPDFAtOZServer(String formFileName,Long repoId) throws Exception {
        FileDownloadParam param = new FileDownloadParam();
        param.setFilePath(formFileName);
        param.setRepositoryId(repoId);

        FileDownloadResult downloadResultDto;
        byte[] byteImage = null;
        try {
            downloadResultDto = fileStorageService.download(param);
            byteImage = downloadResultDto.getFileByteArray();
            
        } catch (Exception e) {
            throw new DetailException("download failed");
        }
        return byteImage;
    }
}
