/*******************************************************************************
 * Class        ：WebdavFileStorage
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：tantm
 * Change log   ：2020/07/21：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.storage.webdav.core.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import vn.com.unit.storage.constant.StorageConstant;
import vn.com.unit.storage.core.FileStorageInstance;
import vn.com.unit.storage.dto.FileDeleteParam;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.webdav.constant.StorageWebdavConstant;
import vn.com.unit.storage.webdav.core.WebDav;

/**
 * WebdavFileStorage
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Component(value = StorageConstant.BEAN_WEB_DAV_FILE_STORAGE)
@Scope(value = "singleton")
public class WebdavFileStorage implements FileStorageInstance {

    @Override
    public FileUploadResultDto upload(FileUploadParamDto param, JcaRepositoryDto fileRepository) throws Exception {

        FileUploadResultDto result = new FileUploadResultDto();

        try {
            WebDav webDav = new WebDav(fileRepository.getPhysicalPath(), fileRepository.getUsername(), fileRepository.getPassword());
            // create URL to upload
            String urlUpload = StorageWebdavConstant.SLASH.concat(param.getSubFilePathWithRule());
            param.setUrlUpload(urlUpload);
            // create path to update
            Path path = Paths.get(urlUpload, param.getFullFileName());
            param.setPath(path);

            String pathFile = path.toString().replaceAll(StorageWebdavConstant.FOUR_BACK_SLASH, StorageWebdavConstant.SLASH);
            webDav.doUploadFile(pathFile, new ByteArrayInputStream(param.getFileByteArray()), false);
            result.setSuccess(true);
        } catch (IOException e) {
            result.setSuccess(false);
        }

        return result;
    }

    @Override
    public FileDownloadResult download(FileDownloadParam param, JcaRepositoryDto fileRepository) throws Exception {

        FileDownloadResult result = new FileDownloadResult();

        WebDav webDav = new WebDav(fileRepository.getPhysicalPath(), fileRepository.getUsername(), fileRepository.getPassword());
        String filePathWebDav = param.getFilePath().replaceAll(StorageWebdavConstant.FOUR_BACK_SLASH, StorageWebdavConstant.SLASH);
        byte[] arr = webDav.doDownloadFile(filePathWebDav);
        result.setFileByteArray(arr);
        return result;
    }

    @Override
    public boolean delete(FileDeleteParam param, JcaRepositoryDto repo) {
        WebDav webDav;
        try {
            webDav = new WebDav(repo.getPhysicalPath(), repo.getUsername(), repo.getPassword());
            webDav.doDeleteFile(param.getFilePath());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
