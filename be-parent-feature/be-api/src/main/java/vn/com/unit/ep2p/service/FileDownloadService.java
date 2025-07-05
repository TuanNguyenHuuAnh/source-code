/*******************************************************************************
 * Class        ：DownloadFileService
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：SonND
 * Change log   ：2021/01/28：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.FileDownloadDocumentReq;
import vn.com.unit.ep2p.core.req.dto.FileDownloadInfoReq;
import vn.com.unit.ep2p.core.req.dto.FileDownloadOZRInfoReq;
import vn.com.unit.ep2p.core.res.dto.DownloadFileInfoRes;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import vn.com.unit.ep2p.dto.req.FileReq;

/**
 * DownloadFileService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
/**
 * @author sonnd
 *
 */
public interface FileDownloadService {

    DownloadFileInfoRes downloadFileLocal(FileDownloadInfoReq fileDownloadLocalInfoReq) throws DetailException;

    DownloadFileInfoRes downloadFileOZR(FileDownloadOZRInfoReq fileDownloadOZRInfoReq) throws DetailException;

    /**
     * downloadFileDocument
     * @param fileDownloadInfoDocument
     * @return
     * @throws DetailException
     * @author taitt
     */
    DownloadFileInfoRes downloadFileDocument(FileDownloadDocumentReq fileDownloadInfoDocument) throws DetailException;
    
    void download(String fileName, Long repoId, HttpServletRequest request, HttpServletResponse response) throws DetailException;
    void fetchDownloadedDocument(FileReq downloadFileReq, HttpServletRequest request, HttpServletResponse response) throws DetailException;

	ResponseEntity<InputStreamResource> downloadFileCommit(String fileName
    		, Long repositoryId) throws Exception;

    ResponseEntity<InputStreamResource> fetchExportedDocument(FileReq exportFileReq,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception;

    ResponseEntity<InputStreamResource> exportFile(String fileName, long parseLong, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception;
    void deleteFile(String fileName, Long repoId) throws Exception;
}
