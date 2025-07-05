/*******************************************************************************
 * Class        ：FileStorageServiceImpl
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.service.impl;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonDateFormatUtil;
import vn.com.unit.common.utils.CommonFileUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.storage.constant.StorageConstant;
import vn.com.unit.storage.constant.StorageExceptionCodeConstant;
import vn.com.unit.storage.core.FileProtocolBuilder;
import vn.com.unit.storage.core.FileStorageInstance;
import vn.com.unit.storage.dto.FileDeleteParam;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.storage.service.JcaRepositoryService;

/**
 * 
 * FileStorageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private JcaRepositoryService jcaRepositoryService;

    @Autowired
    private FileProtocolBuilder fileProtocolBuilder;

    @Autowired
    private JCommonService commonService;

    @Override
    public FileUploadResultDto upload(FileUploadParamDto param, JcaRepositoryDto repo) throws Exception {
        FileUploadResultDto result = new FileUploadResultDto();

        if (null != param.getFileByteArray()) {
            // check repository
            if (null == repo) {
                throw new DetailException(StorageExceptionCodeConstant.E202702_FS_CAN_NOT_ACCESS_REPOSITORY, true);
            }
            FileStorageInstance fileStorageInstance = fileProtocolBuilder.getFileStorageInstance(repo.getFileProtocol());

            // get subPath with rule
            this.getSubPathWithRule(param, repo);

            String filePath = null;
            try {
                // Check upload file
                result = fileStorageInstance.upload(param, repo);
                filePath = param.getSubFilePathWithRule().concat(StorageConstant.SLASH).concat(param.getFullFileName())
                        .replaceAll(StorageConstant.DOUBLE_SLASH, StorageConstant.SLASH)
                        .replaceAll(StorageConstant.FOUR_BACK_SLASH, StorageConstant.SLASH);
            } catch (Exception e) {
                if (e instanceof DetailException) {
                    DetailException de = (DetailException) e;
                    throw new DetailException(de.getExceptionErrorCode(), de.getParamater(), de.isTranslate());
                }
                filePath = null;
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(StorageConstant.YYYYMMDD_TIME);
                    // set name file with date time when file name already exists
                    String fullFileName = param.getNameFile() + StorageConstant.UNDERLINED
                            + dateFormat.format(commonService.getSystemDate()) + param.getExtension();
                    param.setFullFileName(fullFileName);

                    // Check upload file
                    result = fileStorageInstance.upload(param, repo);
                    if (!result.isSuccess()) {
                        throw new DetailException(StorageExceptionCodeConstant.E202703_FS_CAN_NOT_UPLOAD_FILE,
                                new String[] { param.getNameFile() }, true);
                    }
                    filePath = param.getSubFilePathWithRule().concat(StorageConstant.SLASH).concat(fullFileName)
                            .replaceAll(StorageConstant.DOUBLE_SLASH, StorageConstant.SLASH)
                            .replaceAll(StorageConstant.FOUR_BACK_SLASH, StorageConstant.SLASH);
                } catch (Exception ex) {
                    throw new DetailException(StorageExceptionCodeConstant.E202703_FS_CAN_NOT_UPLOAD_FILE,
                            new String[] { param.getNameFile() }, true);
                }
            }
            // Set result object
            if (CommonStringUtil.isNotBlank(filePath)) {
                result.setSuccess(true);
                if (CommonStringUtil.isBlank(result.getFilePath())) {
                    result.setFilePath(filePath);
                }
                result.setFileName(param.getFullFileName());
                result.setRepositoryId(repo.getId());
            } else {
                throw new DetailException(StorageExceptionCodeConstant.E202703_FS_CAN_NOT_UPLOAD_FILE, new String[] { param.getNameFile() },
                        true);
            }
        }
        return result;

    }

    @Override
    public FileDownloadResult download(FileDownloadParam param, JcaRepositoryDto repo) throws Exception {
        FileDownloadResult result = new FileDownloadResult();

        // FileRepositoryDto repo = fileStorageConfig.getRepoById(param.getRepositoryId(), null);
        if (repo != null) {
            try {
                // get instance fileStorage
                FileStorageInstance fileStorageInstance = fileProtocolBuilder.getFileStorageInstance(repo.getFileProtocol());
                result = fileStorageInstance.download(param, repo);
                result.setSuccess(true);
            } catch (Exception e) {
                if (e instanceof DetailException) {
                    DetailException de = (DetailException) e;
                    throw new DetailException(de.getExceptionErrorCode(), de.getParamater(), de.isTranslate());
                }
                throw new DetailException(StorageExceptionCodeConstant.E202804_FS_CAN_NOT_DOWNLOAD_FILE, true);
            }
        }

        return result;
    }

    @Override
    public boolean delete(FileDeleteParam param, JcaRepositoryDto repo) {
        FileStorageInstance fileStorageInstance = fileProtocolBuilder.getFileStorageInstance(repo.getFileProtocol());
        return fileStorageInstance.delete(param, repo);

    }

    @Override
    public FileUploadResultDto upload(FileUploadParamDto param) throws Exception {

        Long repositoryId = param.getRepositoryId();
        // id is required
        if (null == repositoryId) {
            throw new DetailException(StorageExceptionCodeConstant.E202702_FS_CAN_NOT_ACCESS_REPOSITORY);
        }
        JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
        // generate subFolderRule
        Date dateRule = param.getDateRule();
        String subFolderRule = repo.getSubFolderRule();
        if (!CommonStringUtil.isEmpty(subFolderRule)) {
            if (dateRule == null) {
                dateRule = commonService.getSystemDate();
            }
            String dateFormat = CommonDateFormatUtil.format(dateRule, subFolderRule);
            repo.setSubFolderRule(dateFormat);
        }
        return this.upload(param, repo);
    }

    @Override
    public FileDownloadResult download(FileDownloadParam param) throws Exception {
        Long repositoryId = param.getRepositoryId();
        // id is required
        if (null == repositoryId) {
            throw new DetailException(StorageExceptionCodeConstant.E202702_FS_CAN_NOT_ACCESS_REPOSITORY);
        }
        JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
        return this.download(param, repo);
    }

    @Override
    public boolean delete(FileDeleteParam param) throws Exception {
        Long repositoryId = param.getRepositoryId();
        // id is required
        if (null == repositoryId) {
            throw new DetailException(StorageExceptionCodeConstant.E202702_FS_CAN_NOT_ACCESS_REPOSITORY);
        }
        JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
        return this.delete(param, repo);
    }

    private void getSubPathWithRule(FileUploadParamDto param, JcaRepositoryDto repo) {
        // Check rule folder upload
        String subFilePathWithRule = null;
        switch (param.getTypeRule()) {
        // 0: No sub folder rule
        case 0:
            subFilePathWithRule = Paths.get(param.getSubFilePath()).toString();
            break;
        // 1: Sub forlder rule before subFilePath
        case 1:
            String subRule1 = repo.getSubFolderRule();
            if (CommonStringUtil.isBlank(subRule1)) {
                subFilePathWithRule = Paths.get(param.getSubFilePath()).toString();
            } else {
                subFilePathWithRule = subRule1.concat(StorageConstant.SLASH).concat(param.getSubFilePath());
            }
            break;
        // 2: Sub forlder rule after subFilePath
        case 2:
            String subRule2 = repo.getSubFolderRule();
            if (CommonStringUtil.isBlank(subRule2)) {
                subFilePathWithRule = Paths.get(param.getSubFilePath()).toString();
            } else {
                subFilePathWithRule = param.getSubFilePath().concat(StorageConstant.SLASH).concat(subRule2);
            }
            break;
        default:
            subFilePathWithRule = Paths.get(param.getSubFilePath()).toString();
            break;
        }

        String extension = StorageConstant.DOT + CommonFileUtil.getExtension(param.getFileName());
        String nameFile = null;
        // Check rename file when upload
        if (CommonStringUtil.isNotBlank(param.getRename())) {
            nameFile = param.getRename();
        } else {
            nameFile = CommonFileUtil.getBaseName(param.getFileName());
        }
        String fullFileName = nameFile + extension;

        param.setExtension(extension);
        param.setNameFile(nameFile);
        param.setFullFileName(fullFileName);
        param.setSubFilePathWithRule(subFilePathWithRule);
    }

}
