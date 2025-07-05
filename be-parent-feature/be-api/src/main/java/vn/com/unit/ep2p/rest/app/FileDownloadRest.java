/*******************************************************************************
 * Class        ：FileDownloadRest
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：SonND
 * Change log   ：2021/01/28：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.core.security.jwt.TokenProvider;
import vn.com.unit.core.utils.URLUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.req.dto.FileDownloadDocumentReq;
import vn.com.unit.ep2p.core.req.dto.FileDownloadInfoReq;
import vn.com.unit.ep2p.core.req.dto.FileDownloadOZRInfoReq;
import vn.com.unit.ep2p.core.res.dto.DownloadFileInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.FileDownloadService;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.service.JcaRepositoryService;

import vn.com.unit.ep2p.dto.req.FileReq;

/**
 * FileDownloadRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
@Api(tags = { AppApiConstant.API_APP_DOWNLOAD_DESCR })
public class FileDownloadRest extends AbstractRest {

    public static final String USER_ID = "userId";
    public static final String ROLE_FOR_ACCOUNT_ID = "roleForAccountId";

    @Autowired
    private FileDownloadService downloadFileService;
    
    @Autowired
    private JcaRepositoryService jcaRepositoryService;
    
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping(AppApiConstant.API_APP_DOWNLOAD + "/file-local")
    @ApiOperation("Download file local")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 4022404, message = "Error not found repository"),
            @ApiResponse(code = 4024801, message = "Error process download file local"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse downloadFileLocal(
            @ApiParam(name = "body", value = "") @RequestBody FileDownloadInfoReq fileDownloadLocalInfoReq) {
        long start = System.currentTimeMillis();
        try {
            DownloadFileInfoRes resObj = downloadFileService.downloadFileLocal(fileDownloadLocalInfoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PostMapping(AppApiConstant.API_APP_DOWNLOAD + "/file-ozr")
    @ApiOperation("Download file OZR")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 4022404, message = "Error not found repository"),
            @ApiResponse(code = 4024801, message = "Error process download file local"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse downloadFileOZR(
            @ApiParam(name = "body", value = "") @RequestBody FileDownloadOZRInfoReq fileDownloadOZRInfoReq) {
        long start = System.currentTimeMillis();
        try {
            DownloadFileInfoRes resObj = downloadFileService.downloadFileOZR(fileDownloadOZRInfoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PostMapping(AppApiConstant.API_APP_DOWNLOAD + "/file-ozd")
    @ApiOperation("Download file OZR")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 4022404, message = "Error not found repository"),
            @ApiResponse(code = 4024801, message = "Error process download file local"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse downloadFileDocument(
            @ApiParam(name = "body", value = "") @RequestBody FileDownloadDocumentReq fileDownloadOZRInfoReq) {
        long start = System.currentTimeMillis();
        try {
            DownloadFileInfoRes resObj = downloadFileService.downloadFileDocument(fileDownloadOZRInfoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(value = "/downloadFile")
    public void download(@RequestParam(required = false, value = "fileName") String fileName,
            @RequestParam(required = false, value = "repoId", defaultValue = "0") String repoId,
            HttpServletRequest request, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            if (StringUtils.isEmpty(fileName)) {
                return;
            }
            if (fileName.contains(".xlsx")) {
        		return;
        	}
            fileName = URLUtil.cleanPath(fileName);
            if (!repoId.equals("undefined")) {
                JcaRepositoryDto dto = null;
                if ("0".equals(repoId)) {
                    dto = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN", null);
                }
                if (dto != null && dto.getId() != null) {
                    repoId = dto.getId().toString();
                }
                downloadFileService.download(fileName, "0".equals(repoId) ? null : Long.parseLong(repoId), request, response);
            }
        } catch (DetailException e) {
            this.errorHandler.handlerException(e, start);
        }
    }

    @ApiOperation("Export file")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/exportFile")
    public ResponseEntity<InputStreamResource> exportFile(
            HttpServletRequest request,
            HttpServletResponse response, // Bổ sung response để dùng trong service
            @RequestBody FileReq exportFileReq // DTO từ body
    ) throws Exception {

        // Lấy token từ request header
        String token = request.getHeader("Authorization");

        // Kiểm tra token hợp lệ
        if (StringUtils.isEmpty(token) || tokenProvider.getUserIdFromJWT(token.replace("Bearer ", "")) == null) {
            return null;
        }

        // Lấy fileName và repoId từ DTO
        String fileName = exportFileReq.getFileName();
        String repoId = exportFileReq.getRepoId();

        // Xử lý repoId
        if (!"undefined".equals(repoId)) {
            JcaRepositoryDto dto = null;

            if ("0".equals(repoId)) {
                dto = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN", null);
            }

            if (dto != null && dto.getId() != null) {
                Long repoIdLong = dto.getId(); // Đổi tên biến để tránh xung đột
                return downloadFileService.fetchExportedDocument(new FileReq(repoIdLong, fileName), request, response);
            } else {
                return downloadFileService.fetchExportedDocument(new FileReq(0L, fileName), request, response);
            }

        }

        return ResponseEntity.badRequest().build(); // RepoId không hợp lệ
    }

    @PostMapping(value = "/download-file")
    public void downloadFile(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestBody FileReq downloadFileReq) {
        long start = System.currentTimeMillis();
        try {
            // Lấy fileName và repoId từ DTO
            String fileName = downloadFileReq.getFileName();
            String repoId = downloadFileReq.getRepoId();

            // Lấy token từ request header
            String token = request.getHeader("Authorization");

            // Kiểm tra token hợp lệ
            if (StringUtils.isEmpty(token) || tokenProvider.getUserIdFromJWT(token.replace("Bearer ", "")) == null) {
                return;
            }

            if (StringUtils.isEmpty(fileName)) {
                return;
            }

            fileName = URLUtil.cleanPath(fileName);
            if (!repoId.equals("undefined")) {
                JcaRepositoryDto dto = null;
                if ("0".equals(repoId)) {
                    dto = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN", null);
                }
                if (dto != null && dto.getId() != null) {
                    repoId = dto.getId().toString();
                }
                Long repoIdLong = (dto != null && dto.getId() != null) ? dto.getId() : 0L;

                downloadFileService.fetchDownloadedDocument(new FileReq(repoIdLong.equals(0L) ? null : repoIdLong, fileName), request, response);
            }
        } catch (DetailException e) {
            this.errorHandler.handlerException(e, start);
        }
    }

    @ApiOperation("downloadCommitFile")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    @GetMapping("download-commit")
    public ResponseEntity<InputStreamResource> downloadCommitFile(@RequestParam(required = false, value = "fileName") String fileName,
            @RequestParam(required = false, value = "repoId", defaultValue = "0") String repoId,
            HttpServletRequest request, HttpServletResponse response) {
    	try {
    		if (!repoId.equals("undefined")) {
                JcaRepositoryDto dto = null;
                if ("0".equals(repoId)) {
                    dto = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN", null);
                }
                if (dto != null && dto.getId() != null) {
                    repoId = dto.getId().toString();
                }
    		}
			return downloadFileService.downloadFileCommit(fileName, Long.parseLong(repoId));
		} catch (Exception e) {
			return null;
		}
    	
    }
    
    @ApiOperation("Delete file")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @DeleteMapping("/deleteFile")
    public DtsApiResponse deleteFile(@RequestBody FileReq exportFileReq) {
        long start = System.currentTimeMillis();
        DtsApiResponse result = new DtsApiResponse();
        try {
            String fileName = exportFileReq.getFileName();
            String repoId = exportFileReq.getRepoId();
            
            JcaRepositoryDto dto = null;
            if ("0".equals(repoId)) {
                dto = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN", null);
            }

            downloadFileService.deleteFile(fileName, dto.getId());
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
