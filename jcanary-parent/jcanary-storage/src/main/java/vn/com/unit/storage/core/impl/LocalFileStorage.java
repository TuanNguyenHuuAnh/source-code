/*******************************************************************************
 * Class        ：LocalFileStorage
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：HungHT
 * Change log   ：2020/07/21：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.storage.core.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import vn.com.unit.common.utils.CommonFileUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.storage.constant.StorageConstant;
import vn.com.unit.storage.constant.StorageExceptionCodeConstant;
import vn.com.unit.storage.core.FileStorageInstance;
import vn.com.unit.storage.dto.FileDeleteParam;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.utils.FileStorageUtils;

/**
 * LocalFileStorage
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Component(value = StorageConstant.BEAN_LOCAL_FILE_STORAGE)
@Scope(value = "singleton")
public class LocalFileStorage implements FileStorageInstance {

    @Override
    public FileUploadResultDto upload(FileUploadParamDto param, JcaRepositoryDto fileRepository) throws Exception {

        FileUploadResultDto result = new FileUploadResultDto();
        // create URL to upload
        String urlUpload = Paths.get(fileRepository.getPhysicalPath(), param.getSubFilePathWithRule()).toString();
        param.setUrlUpload(urlUpload);

        // Check create directory if not exist
        if (!this.createDirectoryNotExists(param.getUrlUpload())) {
            throw new DetailException(StorageExceptionCodeConstant.E202701_FS_CAN_NOT_CREATE_FOLDER, new String[] { param.getUrlUpload() },
                    true);
        }

        // create path to update
        Path path = Paths.get(urlUpload, param.getFullFileName());
        param.setPath(path);
        // upload file
        boolean isSuccess = this.writeFile(path, param.getFileByteArray());
        result.setSuccess(isSuccess);

        return result;

    }

    @Override
    public FileDownloadResult download(FileDownloadParam param, JcaRepositoryDto fileRepository) throws Exception {

        FileDownloadResult result = new FileDownloadResult();
        result.setSuccess(true);

        Path path = Paths.get(fileRepository.getPhysicalPath(), param.getFilePath());
        // String url = StorageConstant.EMPTY;
        String domain = StorageConstant.EMPTY;

        // path main
        String pathMain = path.toString();
        File fileTemp = new File(Paths.get(domain, pathMain).toAbsolutePath().toString());

        // TODO tantm read config temp folder
        // if (!fileTempCheck.exists() && param.getFilePath().contains(StorageConstant.AT_FILE)) {
        // // path temp
        // url = fileStorageService.getPathByRepository(param.getCompanyId(), param.getFilePath(), FileStorageConfig.REPO_UPLOADED_TEMP);
        // } else {
        // url = pathMain;
        // }
        // File fileTemp = new File(CommonFileUtil.separatorsToSystem(Paths.get(domain, url).toString()));

        result.setFileName(fileTemp.getName());
        result.setLength(fileTemp.length());
        result.setFileByteArray(CommonFileUtil.readFileToByteArray(fileTemp));
        return result;
    }

    @Override
    public boolean delete(FileDeleteParam param, JcaRepositoryDto repo) {
        Path path = Paths.get(repo.getPhysicalPath(), param.getFilePath());
        String domain = StorageConstant.EMPTY;

        // path main
        String pathMain = path.toString();

        File fileTemp = new File(Paths.get(domain, pathMain).toAbsolutePath().toString());

        if (fileTemp.exists()) {
            fileTemp.delete();
            return true;
        }
        return false;

    }

    private boolean createDirectoryNotExists(String path) {
        boolean result = true;
        try {
            FileStorageUtils.createDirectoryNotExists(path);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private boolean writeFile(Path path, byte[] bytes) {
        boolean result = true;
        try {
            Files.write(path, bytes, StandardOpenOption.CREATE_NEW);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
