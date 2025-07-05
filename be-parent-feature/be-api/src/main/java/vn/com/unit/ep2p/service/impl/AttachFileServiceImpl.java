/*******************************************************************************
 * Class        ：AttachFileServiceImpl
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaAttachFileDto;
import vn.com.unit.core.dto.JcaAttachFileSearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAttachFile;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAttachFileService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.DirectoryConstant;
import vn.com.unit.core.dto.FileResultDto;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.AttachFileReq;
import vn.com.unit.ep2p.dto.req.AttachFileUploadReq;
import vn.com.unit.ep2p.dto.res.AttachFileDowloadRes;
import vn.com.unit.ep2p.dto.res.AttachFileRes;
import vn.com.unit.ep2p.dto.res.AttachFileUploadRes;
import vn.com.unit.ep2p.service.AttachFileService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.storage.service.JcaRepositoryService;

/**
 * AttachFileServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AttachFileServiceImpl extends AbstractCommonService implements AttachFileService {

    @Autowired
    private JcaAttachFileService jcaAttachFileService;

    @Autowired
    @Qualifier("appSystemConfigServiceImpl")
    private JcaSystemConfigService jcaSystemConfigService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JRepositoryService jRepositoryService;
    /** The company service. */
    @Autowired
    private CompanyService companyService;

    private static final String REFERENCE_KEY = "PPL_ATTACH_FILE_";
    private static final String ATTACH_FILE_FOLDER = "attach_file/";
    private static final String ATTACH_FILE_RENAME = "ATTACH_FILE_";
    private static final int TYPE_UPLOAD_RULE = 2;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.AttachFileService#uploadAttachFile(vn.com.unit.mbal.api.req.dto.AttachFileUploadReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttachFileUploadRes uploadAttachFile(AttachFileUploadReq attachFileUploadReq) throws DetailException {

        AttachFileUploadRes uploadResult = new AttachFileUploadRes();
        Long companyId = attachFileUploadReq.getCompanyId();
        Long referenceId = attachFileUploadReq.getReferenceId();
        String referenceKey = attachFileUploadReq.getReferenceKey();
        String attachType = attachFileUploadReq.getAttachType();
        if (CommonStringUtil.isBlank(referenceKey)) {
            referenceKey = REFERENCE_KEY
                    .concat(CommonDateUtil.formatDateToString(commonService.getSystemDate(), CommonDateUtil.YYYYMMDDHHMMSS));
        }
        List<AttachFileRes> resultList = new ArrayList<>();
        List<AttachFileReq> attachFileList = attachFileUploadReq.getAttachFileList();
        try {
            if (CommonCollectionUtil.isNotEmpty(attachFileList)) {
                for (AttachFileReq attachFileReq : attachFileList) {
                    // upload
                    JcaAttachFileDto attachFileDto = objectMapper.convertValue(attachFileReq, JcaAttachFileDto.class);
                    FileUploadResultDto uploadResultDto = this.uploadFile(attachFileReq, companyId);
                    if (uploadResultDto.isSuccess()) {
                        attachFileDto.setRepositoryId(uploadResultDto.getRepositoryId());
                        attachFileDto.setFilePath(uploadResultDto.getFilePath());
                        attachFileDto.setCompanyId(companyId);
                        attachFileDto.setReferenceId(referenceId);
                        attachFileDto.setReferenceKey(referenceKey);
                        attachFileDto.setAttachType(attachType);
                        jcaAttachFileService.saveJcaAttachFileDto(attachFileDto);
                        AttachFileRes attachFileRes = objectMapper.convertValue(uploadResultDto, AttachFileRes.class);
                        attachFileRes.setAttachFileId(attachFileDto.getAttachFileId());
                        resultList.add(attachFileRes);
                    } else {
                        throw new DetailException(AppApiExceptionCodeConstant.E4025101_APPAPI_ATTACH_FILE_UPLOAD_ERROR);
                    }
                }
            }
            uploadResult.setStatus(true);
            uploadResult.setCompanyId(companyId);
            uploadResult.setReferenceId(referenceId);
            uploadResult.setReferenceKey(referenceKey);
            uploadResult.setAttachType(attachType);
            uploadResult.setResultList(resultList);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025101_APPAPI_ATTACH_FILE_UPLOAD_ERROR);
        }
        return uploadResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.AttachFileService#getAttachFileList(org.springframework.util.MultiValueMap,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<JcaAttachFileDto> getAttachFileList(MultiValueMap<String, String> commonSearch, Pageable pageable)
            throws DetailException {
        ObjectDataRes<JcaAttachFileDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaAttachFile.class, AttachFileService.TABLE_ALIAS_ATTACH_FILE);
            /** init param search repository */
            JcaAttachFileSearchDto searchDto = this.buildJcaAttachFileSearchDto(commonSearch);

            int totalData = jcaAttachFileService.countBySearchCondition(searchDto);
            List<JcaAttachFileDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = jcaAttachFileService.getJcaAttachFileDtoListByCondition(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025102_APPAPI_ATTACH_FILE_LIST_ERROR);
        }
        return resObj;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.AttachFileService#deleteAttachFile(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAttachFile(Long attachFileId) throws DetailException {
        try {
            jcaAttachFileService.deleteJcaAttachFileById(attachFileId);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025103_APPAPI_ATTACH_FILE_DELETE_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.AttachFileService#dowloadAttachFile(java.lang.Long)
     */
    @Override
    public AttachFileDowloadRes dowloadAttachFile(Long attachFileId) throws DetailException {
        AttachFileDowloadRes attachFileDowloadRes = new AttachFileDowloadRes();
        try {
            JcaAttachFileDto attachFileDto = jcaAttachFileService.getJcaAttachFileDtoById(attachFileId);
            FileDownloadResult downloadResult = this.downloadFile(attachFileDto.getFilePath(), attachFileDto.getRepositoryId());
            byte[] fileByte = downloadResult.getFileByteArray();
            attachFileDowloadRes.setFileByte(fileByte);
            attachFileDowloadRes.setFileName(attachFileDto.getFileName());
            attachFileDowloadRes.setFileType(attachFileDto.getFileType());
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025104_APPAPI_ATTACH_FILE_DOWNLOAD_ERROR);
        }
        return attachFileDowloadRes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.AttachFileService#updateReferenceId(java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReferenceId(String referenceKey, Long referenceId) throws DetailException {
        try {
            jcaAttachFileService.updateReferenceId(referenceKey, referenceId);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025105_APPAPI_ATTACH_FILE_UPDATE_REFERENCE_ID_ERROR);
        }

    }

    private JcaAttachFileSearchDto buildJcaAttachFileSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaAttachFileSearchDto searchDto = new JcaAttachFileSearchDto();

        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Long referenceId = null != commonSearch.getFirst("referenceId") ? Long.valueOf(commonSearch.getFirst("referenceId")) : null;
        String referenceKey = null != commonSearch.getFirst("referenceKey") ? commonSearch.getFirst("referenceKey") : AppApiConstant.EMPTY;
        String fileName = null != commonSearch.getFirst("fileName") ? commonSearch.getFirst("fileName") : AppApiConstant.EMPTY;
        String attachType = null != commonSearch.getFirst("attachType") ? commonSearch.getFirst("attachType") : AppApiConstant.EMPTY;

        searchDto.setCompanyId(companyId);
        searchDto.setReferenceId(referenceId);
        if (null == referenceId) {
            searchDto.setReferenceKey(referenceKey);
        }
        searchDto.setFileName(fileName);
        searchDto.setAttachType(attachType);

        return searchDto;

    }

    private FileUploadResultDto uploadFile(AttachFileReq attachFileReq, Long companyId) throws Exception {

        Long repositoryId = Long.valueOf(jcaSystemConfigService.getValueByKey(SystemConfig.REPO_ATTACH_FILE, companyId));
        String fileName = attachFileReq.getFileName();
        String rename = ATTACH_FILE_RENAME
                .concat(CommonDateUtil.formatDateToString(commonService.getSystemDate(), CommonDateUtil.YYYYMMDDHHMMSS));
        byte[] fileByte = CommonBase64Util.decodeToByte(attachFileReq.getFileBase64());
        String subFilePath = ATTACH_FILE_FOLDER;

        // fileupload
        FileUploadParamDto param = new FileUploadParamDto();
        param.setFileByteArray(fileByte);
        param.setFileName(fileName);
        param.setRename(rename);

        param.setTypeRule(TYPE_UPLOAD_RULE);
        param.setDateRule(null);
        param.setSubFilePath(subFilePath);

        param.setCompanyId(companyId);
        param.setRepositoryId(repositoryId);

        return fileStorageService.upload(param);
    }

    private FileDownloadResult downloadFile(String filePath, Long repositoryId) throws Exception {
        FileDownloadParam downloadParam = new FileDownloadParam();
        downloadParam.setFilePath(filePath);
        downloadParam.setRepositoryId(repositoryId);
        return fileStorageService.download(downloadParam);
    }

	@Override
	public String uploadFileToServer(Long userId, MultipartFile avatarFile, Locale locale) throws AppException {
        if (null != avatarFile) {
        	Long companyId = UserProfileUtils.getCompanyId();
            String systemCode = companyService.getSystemCodeByCompanyId(companyId);
            String subFilePath = DirectoryConstant.EXPORT_EXCEL_FOLDER.concat(systemCode);
        	FileResultDto repoResultDto = jRepositoryService.uploadFileBySettingKey(avatarFile, avatarFile.getOriginalFilename().replace(".xlsx", ""),
                    SystemConfig.REPO_UPLOADED_MAIN, 2, null, subFilePath, companyId, locale);
            if (repoResultDto.isStatus()) {
                return repoResultDto.getFilePath();
//				acc.setRepositoryId(repoResultDto.getRepositoryId());
            } else {
                throw new AppException(repoResultDto.getMessage(), null, null);
            }
        }
        return "";
    }

    // private void deleteFile(String filePath, Long repositoryId) throws Exception {
    // //delete file
    // FileDeleteParam deleteParam = new FileDeleteParam();
    // deleteParam.setRepositoryId(repositoryId);
    // deleteParam.setFilePath(filePath);
    // fileStorageService.delete(deleteParam);
    // }
}
