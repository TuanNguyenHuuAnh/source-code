/*******************************************************************************
 * Class        ：AlfrescoFileStorage
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：tantm
 * Change log   ：2020/07/21：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.core.impl;

import java.io.InputStream;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Cleanup;
import vn.com.unit.common.utils.CommonFileUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.storage.alfresco.constant.StorageAlfrescoConstant;
import vn.com.unit.storage.alfresco.constant.StorageAlfrescoExceptionCodeConstant;
import vn.com.unit.storage.alfresco.service.AlfrescoService;
import vn.com.unit.storage.constant.StorageConstant;
import vn.com.unit.storage.core.FileStorageInstance;
import vn.com.unit.storage.dto.FileDeleteParam;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.utils.FileStorageUtils;

/**
 * AlfrescoFileStorage
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Component(value = StorageConstant.BEAN_ECM_ALFRESCO_FILE_STORAGE)
@Scope(value = "singleton")
public class AlfrescoFileStorage implements FileStorageInstance {

    @Autowired
    AlfrescoService alfrescoService;

    @Override
    public FileUploadResultDto upload(FileUploadParamDto param, JcaRepositoryDto repo) throws Exception {

        FileUploadResultDto result = new FileUploadResultDto();

        try {
            // Remove version in id
            if (null == param.getMimeType()) {
                param.setMimeType(FileStorageUtils.getContentType(param.getFileName()));
            }
            String docId = alfrescoService.createOrUpdateDocumentWithVersion(repo.getHost(), repo.getUsername(), repo.getPassword(),
                    repo.getSite(), repo.getPath(), param.getFileByteArray(), param.getFileName(), param.getMimeType(), param.isMajor());
            result.setDocId(docId);
            result.setFilePath(docId);
            result.setSuccess(true);
        } catch (Exception ex) {
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public FileDownloadResult download(FileDownloadParam param, JcaRepositoryDto repo) throws Exception {
        FileDownloadResult result = new FileDownloadResult();
        // ECM authority
        String ecmHost = repo.getHost();
        String ecmUsername = repo.getUsername();
        String ecmPassword = repo.getPassword();
        String ecmSite = repo.getSite();
        String version = param.getFilePath().split(StorageAlfrescoConstant.SEMI_COLON)[1];
        Document doc = alfrescoService.getDocumentById(ecmHost, ecmUsername, ecmPassword, ecmSite, param.getFilePath(), version);

        if (doc == null) {
            throw new DetailException(StorageAlfrescoExceptionCodeConstant.E205701_FSA_FILE_NOT_FOUND,
                    new Object[] { repo.getId(), param.getFilePath() }, true);
        }

        ContentStream contentStream = doc.getContentStream();
        String fileName = contentStream.getFileName();
        long length = contentStream.getLength();
        @Cleanup
        InputStream stream = contentStream.getStream();
        String mimeType = contentStream.getMimeType();
        result.setFileName(fileName);
        result.setLength(length);
        result.setMimeType(mimeType);
        result.setFileByteArray(CommonFileUtil.toByteArray(stream));
        result.setSuccess(true);

        return result;

    }

    @Override
    public boolean delete(FileDeleteParam param, JcaRepositoryDto repo) {

        // ECM authority
        String ecmHost = repo.getHost();
        String ecmUsername = repo.getUsername();
        String ecmPassword = repo.getPassword();
        String ecmSite = repo.getSite();
        String ozdId = param.getDocId();
        boolean isDeleteAllVersion = param.isDeleteAllVersion();

        return alfrescoService.deleteDocument(ecmHost, ecmUsername, ecmPassword, ecmSite, ozdId, isDeleteAllVersion);

    }
}
