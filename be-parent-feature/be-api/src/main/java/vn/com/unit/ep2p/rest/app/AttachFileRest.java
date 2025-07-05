/*******************************************************************************
 * Class        ：AttachFileRest
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAttachFileDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.AttachFileUploadReq;
import vn.com.unit.ep2p.dto.res.AttachFileDowloadRes;
import vn.com.unit.ep2p.dto.res.AttachFileUploadRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.AttachFileService;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * AttachFileRest
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
@Api(tags = { AppApiConstant.API_APP_ATTACH_FILE_DESCR })
public class AttachFileRest extends AbstractRest {

    @Autowired
    private AttachFileService attachFileService;
    @Autowired
    private AccountService accountService;

    @GetMapping(AppApiConstant.API_APP_ATTACH_FILE)
    @ApiOperation("Api provides a list attach file on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 4025102, message = "Get attach file list error"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden") })
    @ApiImplicitParams({ @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "attachType", value = "attachType", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "referenceId", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "referenceKey", value = "referenceKey", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "fileName", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listAttachFile(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,
            @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {

            ObjectDataRes<JcaAttachFileDto> resObj = new ObjectDataRes<>(0, new ArrayList<>());

            String referenceId = requestParams.getFirst("referenceId");
            String referenceKey = requestParams.getFirst("referenceKey");
            
            if (CommonStringUtil.isNoneBlank(referenceId) || CommonStringUtil.isNotBlank(referenceKey)) {
                resObj = attachFileService.getAttachFileList(requestParams, pageable);
            }
            
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PostMapping(AppApiConstant.API_APP_ATTACH_FILE)
    @ApiOperation("Api provides upload attach file")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 4025101, message = "Upload attach file error"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse uploadAttachFile(
            @ApiParam(name = "body", value = "attachFileUploadReq information") @RequestBody AttachFileUploadReq attachFileUploadReq) {
        long start = System.currentTimeMillis();
        try {
            AttachFileUploadRes resObj = attachFileService.uploadAttachFile(attachFileUploadReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @DeleteMapping(AppApiConstant.API_APP_ATTACH_FILE)
    @ApiOperation("Api provides delete attach file")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 4025103, message = "delete attach file error"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse deleteAttachFile(
            @ApiParam(name = "attachFileId", value = "Delete AttachFile on system by id", example = "1") @RequestParam Long attachFileId) {
        long start = System.currentTimeMillis();
        try {
            attachFileService.deleteAttachFile(attachFileId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_ATTACH_FILE + "/{attachFileId}")
    @ApiOperation("Api provides download attach file")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 4025104, message = "Dowload attach file error"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse downloadAttachFile(
            @ApiParam(name = "attachFileId", value = "Delete AttachFile on system by id", example = "1") @PathVariable Long attachFileId) {
        long start = System.currentTimeMillis();
        try {
            AttachFileDowloadRes resObj = attachFileService.dowloadAttachFile(attachFileId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_APP_ATTACH_FILE)
    @ApiOperation("Api provides update referenceId attach file")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 4025105, message = "Update referenceId attach file error"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse updateReferenceId(
            @ApiParam(name = "referenceKey", value = "referenceKey of attach file", example = "1") @RequestParam String referenceKey,
            @ApiParam(name = "referenceId", value = "referenceId of attach file", example = "1") @RequestParam Long referenceId) {
        long start = System.currentTimeMillis();
        try {
            attachFileService.updateReferenceId(referenceKey, referenceId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    @PostMapping(AppApiConstant.API_APP_UPLOAD_AVATAR)
	public DtsApiResponse uploadImage(@RequestBody MultipartFile file, HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	Long userId = UserProfileUtils.getAccountId();

            List<String> names = new ArrayList<>();
            CollectionUtils.addAll(names, file.getOriginalFilename().split("/"));
            String name = file.getOriginalFilename();
            if(CollectionUtils.isNotEmpty(names)) {
                name = names.get(names.size() - 1);
            }
            String contentType = getContentType(name);
            if(contentType.contains("image") || contentType.equals("application/pdf"))
                accountService.updateAvatar(userId, file, locale.getLanguage(), locale);
            else
                return new DtsApiResponse(400, DtsConstant.ERROR, Strings.EMPTY, start, "Invalid image File! Content Type :- " + contentType);
            return this.successHandler.handlerSuccess(file.getName(), start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

    public static String getContentType(String fileNameFull) {
        String contentType = "application/octet-stream";

        String extensionFile = getFileExtension(fileNameFull);

        if (StringUtils.isNotEmpty(extensionFile)) {
            if (extensionFile.equals("pdf"))
                contentType = "application/pdf";
            else if (extensionFile.equals("txt"))
                contentType = "text/plain";
            else if (extensionFile.equals("exe"))
                contentType = "application/octet-stream";
            else if (extensionFile.equals("zip"))
                contentType = "application/zip";
            else if (extensionFile.equals("doc"))
                contentType = "application/msword";
            else if (extensionFile.equals("xls"))
                contentType = "application/vnd.ms-excel";
            else if (extensionFile.equals("xlsx"))
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            else if (extensionFile.equals("ppt"))
                contentType = "application/vnd.ms-powerpoint";
            else if (extensionFile.equals("gif"))
                contentType = "image/gif";
            else if (extensionFile.equals("png"))
                contentType = "image/png";
            else if (extensionFile.equals("jpeg"))
                contentType = "image/jpeg";
            else if (extensionFile.equals("jpg"))
                contentType = "image/jpeg";
            else if (extensionFile.equals("mp3"))
                contentType = "audio/mpeg";
            else if (extensionFile.equals("wav"))
                contentType = "audio/x-wav";
            else if (extensionFile.equals("mpeg"))
                contentType = "video/mpeg";
            else if (extensionFile.equals("mpg"))
                contentType = "video/mpeg";
            else if (extensionFile.equals("mpe"))
                contentType = "video/mpeg";
            else if (extensionFile.equals("mov"))
                contentType = "video/quicktime";
            else if (extensionFile.equals("avi"))
                contentType = "video/x-msvideo";
            else if (extensionFile.equals("flv"))
                contentType = "video/flv";
        }
        return contentType;
    }

    public static String getFileExtension(String fileNameFull) {
        String result = null;

        if (StringUtils.isNotEmpty(fileNameFull)) {
            int beginIndex = fileNameFull.lastIndexOf('.');

            if (beginIndex != -1) {
                beginIndex = beginIndex + 1;
                int endIndex = fileNameFull.length();

                result = fileNameFull.substring(beginIndex, endIndex).toLowerCase();
            }
        }

        return result;
    }

    @PostMapping("/upload-file-to-server")
    public DtsApiResponse uploadFileToServer(@RequestBody MultipartFile file, HttpServletRequest request)
    {
		long start = System.currentTimeMillis();
    	try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	Long userId = UserProfileUtils.getAccountId();
			return this.successHandler.handlerSuccess(attachFileService.uploadFileToServer(userId, file, locale), start, null, userId.toString());
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
    }
}
