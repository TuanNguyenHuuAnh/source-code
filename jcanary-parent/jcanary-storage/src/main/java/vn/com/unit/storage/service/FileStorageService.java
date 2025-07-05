/*******************************************************************************
 * Class        ：FileStorageService
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：HungHT
 * Change log   ：2020/07/27：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.storage.service;

import vn.com.unit.storage.dto.FileDeleteParam;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;

/**
 * FileStorageService.
 *
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface FileStorageService {

    /**
     * Upload file to repository.
     *
     * @param param
     *            byte of file, and repository information
     * @param fileRepository
     *            repository information
     * @return file information, as name and path or id
     * @throws Exception
     *             type {@link Exception}
     * @author tantm
     */
    FileUploadResultDto upload(FileUploadParamDto param, JcaRepositoryDto fileRepository) throws Exception;

    /**
     * Upload file to repository.
     *
     * @param param
     *            byte of file, and repository information
     * @return file information, as name and path or id
     * @throws Exception
     *             type {@link Exception}
     * @author tantm
     */
    FileUploadResultDto upload(FileUploadParamDto param) throws Exception;

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
     * Download file from repository.
     *
     * @param param
     *            id or path, and connection information
     * @return download success flag, byte of file
     * @throws Exception
     *             type {@link Exception}
     * @author tantm
     */
    FileDownloadResult download(FileDownloadParam param) throws Exception;

    /**
     * Delete file in repository.
     *
     * @param param
     *            id or path
     * @param fileRepository
     *            repository information
     * @return {@code true} if deleted success
     * @author tantm
     */
    boolean delete(FileDeleteParam param, JcaRepositoryDto fileRepository);

    /**
     * Delete file in repository.
     *
     * @param param
     *            id or path
     * @return {@code true} if deleted success
     * @author tantm
     * @throws Exception 
     */
    boolean delete(FileDeleteParam param) throws Exception;

}
