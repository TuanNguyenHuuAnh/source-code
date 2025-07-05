/*******************************************************************************
 * Class        ：FileStorage
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：HungHT
 * Change log   ：2020/07/21：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.storage.core;

import vn.com.unit.storage.dto.FileDeleteParam;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;

/**
 * FileStorage.
 *
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface FileStorageInstance {

    /**
     * Upload file to repository.
     *
     * @param param
     *            byte of file, and folder information
     * @param fileRepository
     *            repository information
     * @return file information, as name and path or id
     * @throws Exception
     *             type {@link Exception}
     * @author tantm
     */
    FileUploadResultDto upload(FileUploadParamDto param, JcaRepositoryDto fileRepository) throws Exception;

    /**
     * Download file from repository.
     *
     * @param param
     *            id or path, and connection information
     * @param fileRepository
     *            repository information
     * @return download success flag, byte of file
     * @throws Exception
     *             type {@link Exception}
     * @author tantm
     */
    FileDownloadResult download(FileDownloadParam param, JcaRepositoryDto fileRepository) throws Exception;

    /**
     * Delete file in repository.
     *
     * @param param
     *            id or path
     * @param repo
     *            repository information
     * @return {@code true} if deleted success
     * @author tantm
     */
    boolean delete(FileDeleteParam param, JcaRepositoryDto repo);
}
