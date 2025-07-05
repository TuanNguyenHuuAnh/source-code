/*******************************************************************************
 * Class        ：RoleForTeamServiceImpl
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：SonND
 * Change log   ：2020/12/22：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.constant.CommonConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.constant.AppCoreExceptionCodeConstant;
import vn.com.unit.ep2p.core.constant.AppSystemSettingKey;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.service.EfoFormService;
import vn.com.unit.ep2p.core.req.dto.FileDownloadDocumentReq;
import vn.com.unit.ep2p.core.req.dto.FileDownloadInfoReq;
import vn.com.unit.ep2p.core.req.dto.FileDownloadOZRInfoReq;
import vn.com.unit.ep2p.core.res.dto.DownloadFileInfoRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.DocumentMainFileService;
import vn.com.unit.ep2p.core.service.RestFullApiService;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.ep2p.service.FileDownloadService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.storage.service.JcaRepositoryService;

import vn.com.unit.ep2p.dto.req.FileReq;

/**
 * RoleForTeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FileDownloadServiceImpl extends AbstractCommonService implements FileDownloadService {

 // PARAM SERVICE
    private static final String FILE_PATH = "filePath";

	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private JcaRepositoryService jcaRepositoryService;

	@Autowired
	private RestFullApiService restFullApiService;
	
	@Autowired
	private DocumentMainFileService documentMainFileService;
	
	@Autowired
	private EfoFormService efoFormService;
	
    @Autowired
    SystemConfig systemConfig;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
    public DownloadFileInfoRes downloadFileLocal(FileDownloadInfoReq fileDownloadLocalInfoReq) throws DetailException {
	    DownloadFileInfoRes downloadFileInfoRes = new DownloadFileInfoRes();
	    try {
	    	
	    	if ( null == fileDownloadLocalInfoReq.getFilePath() && null == fileDownloadLocalInfoReq.getRepositoryId() ) {
	    		return null;
	    	}
	    	
	        FileDownloadParam param = new FileDownloadParam();
	        param.setFilePath(fileDownloadLocalInfoReq.getFilePath());
	        if(null != fileDownloadLocalInfoReq.getRepositoryId()) {
	            setRepositoryId(fileDownloadLocalInfoReq.getRepositoryId(), param);
	        }
	        param.setRepositoryId(fileDownloadLocalInfoReq.getRepositoryId());
	        FileDownloadResult fileResult = fileStorageService.download(param);
	        this.convertData(fileResult, downloadFileInfoRes);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024801_APPAPI_DOWNLOAD_FILE_LOCAL_ERROR);
        }
	    return downloadFileInfoRes;
    }
	
	private void setRepositoryId(Long repositoryId, FileDownloadParam fileDownloadParam) throws DetailException {
	    JcaRepositoryDto jcaRepositoryDto  =  jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
	    if(null != jcaRepositoryDto) {
	        fileDownloadParam.setRepositoryId(repositoryId);
	    }else {
	        throw new DetailException(AppApiExceptionCodeConstant.E4022404_APPAPI_REPOSITORY_NOT_FOUND, true);
	    }
	}

    private void convertData(FileDownloadResult fileResult, DownloadFileInfoRes downloadFileInfoRes) {
        downloadFileInfoRes.setFileName(fileResult.getFileName());
        downloadFileInfoRes.setFilePath(fileResult.getFilePath());
        downloadFileInfoRes.setLength(fileResult.getLength());
        downloadFileInfoRes.setMimeType(fileResult.getMimeType());
        if(null != fileResult.getFileByteArray()) {
            this.setBase64(fileResult.getFileByteArray(), downloadFileInfoRes);
        }
    }
    
    private String convertByteToStringBase64(byte[] fileByteArray) {
        return CommonBase64Util.encode(fileByteArray);
    }
	
	private void setBase64(byte[] fileByteArray, DownloadFileInfoRes downloadFileInfoRes) {
	    downloadFileInfoRes.setBase64(this.convertByteToStringBase64(fileByteArray));
	}

    @Override
    public DownloadFileInfoRes downloadFileOZR(FileDownloadOZRInfoReq fileDownloadOZRInfoReq) throws DetailException {
        DownloadFileInfoRes downloadFileInfoRes = new DownloadFileInfoRes();
        Long formId = fileDownloadOZRInfoReq.getFormId();
        EfoFormDto efoFormDto =  efoFormService.getEfoFormDtoById(formId);
        String filePath  = efoFormDto.getOzFilePath(); 
        if(null != filePath) {
            byte[] fileByteArray = this.getFileOZRByOZRepository( filePath); 
            downloadFileInfoRes.setBase64(convertByteToStringBase64(fileByteArray));
            downloadFileInfoRes.setFilePath( filePath);
        }
        return downloadFileInfoRes;
    }
    
    public byte[] getFileOZRByOZRepository(String filePath) throws DetailException {
        byte[] response = null;
        Long companyId = UserProfileUtils.getUserPrincipal().getCompanyId();
        try {
            String urlRepo = systemConfig.getConfig(AppSystemSettingKey.OZ_REPOSITORY_LOCAL_URL,companyId);
            String url = urlRepo.concat(AppCoreConstant.URL_CONSTANT_REPO_FORM.substring(1).concat(AppCoreConstant.URL_CONSTANT_REPO_DOWNLOAD_TEAMPLATE));
            
            // Init param request
            MultiValueMap<String, Object> requestObj = new LinkedMultiValueMap<>();
            requestObj.add(FILE_PATH, filePath);
            response = restFullApiService.uploadRestFull(url, requestObj);        
            
        } catch (Exception e) {
            throw new DetailException(AppCoreExceptionCodeConstant.E401801_CORE_REGISTER_FORM_EXISTS);
        }
        return response;
    }
    
    @Override
    public DownloadFileInfoRes downloadFileDocument(FileDownloadDocumentReq fileDownloadInfoDocument) throws DetailException {
        DownloadFileInfoRes downloadFileInfoRes = new DownloadFileInfoRes();
        
        Long mainfileId = fileDownloadInfoDocument.getMainFileId();
        Long docMainFileHistoryId = fileDownloadInfoDocument.getDocMainFileHistoryId();
        
        try {
            FileDownloadResult fileDownloadResult = documentMainFileService.getOZDocMainFileById(mainfileId, docMainFileHistoryId, true, UserProfileUtils.getLocaleRequest());

            this.convertData(fileDownloadResult, downloadFileInfoRes);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024801_APPAPI_DOWNLOAD_FILE_LOCAL_ERROR);
        }
        return downloadFileInfoRes;
    }

    @Override
    public void download(String fileName, Long repositoryId, HttpServletRequest request, HttpServletResponse response) throws DetailException {
        if (StringUtils.isEmpty(fileName)) {
            return;
        }
        try {
            // path main
        	String pathFile = "";
            JcaRepositoryDto repo = new JcaRepositoryDto();
            if(ObjectUtils.isNotEmpty(repositoryId)) {
                repo = jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
                pathFile = repo.getPhysicalPath() + "/" + fileName;
            } else {

                pathFile = fileName;
            }
            File fileTemp = new File(pathFile);
            if(!StringUtils.containsIgnoreCase(fileTemp.getCanonicalPath(), Paths.get(repo.getPhysicalPath()).toString())){
                throw new IOException("invalid path");
            }
            if (fileTemp.exists()) {
                FileInputStream fileInputStream = new FileInputStream(fileTemp);
                byte[] arr = new byte[1024];
                int numRead = -1;
                response.addHeader("Content-Length", Long.toString(fileTemp.length()));
                if (StringUtils.isNotEmpty(request.getParameter("isDownload"))
                        && request.getParameter("isDownload").equalsIgnoreCase("download")) {
                    response.setContentType("application/octet-stream");
                } else {
                    response.setContentType(getContentType(fileName));
                }
                
                response.addHeader("Content-Disposition",
                        "inline; filename=\"" + FileUtil.deAccent(fileTemp.getName()) + "\"");
                
                while ((numRead = fileInputStream.read(arr)) != -1) {
                    response.getOutputStream().write(arr, 0, numRead);
                }
                fileInputStream.close();
            } else {
                return;
            }
        } catch (IOException e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024801_APPAPI_DOWNLOAD_FILE_LOCAL_ERROR);
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

	@Override
	public ResponseEntity<InputStreamResource> downloadFileCommit(String fileName, Long repositoryId) throws Exception {
		JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
        String pathFile = repo.getPhysicalPath() + "/" + fileName;
        File fileTemp = new File(pathFile);
            FileInputStream fileInputStream = new FileInputStream(fileTemp);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition"
            		, "attachment; filename=\""+ fileName);
            
            headers.add("Access-Control-Expose-Headers", "Content-Disposition");
    		return ResponseEntity
                    .ok()
                    .headers(headers)
//                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .body(new InputStreamResource(fileInputStream));
//            response.addHeader("Content-Length", Long.toString(fileTemp.length()));
//            if (StringUtils.isNotEmpty(request.getParameter("isDownload"))
//                    && request.getParameter("isDownload").equalsIgnoreCase("download")) {
//                response.setContentType("application/octet-stream");
//            } else {
//                response.setContentType(getContentType(fileName));
//            }
//            
//            response.addHeader("Content-Disposition",
//                    "inline; filename=\"" + FileUtil.deAccent(fileTemp.getName()) + "\"");
//            
//            while ((numRead = fileInputStream.read(arr)) != -1) {
//                response.getOutputStream().write(arr, 0, numRead);
//            }
//            fileInputStream.close();
	}

    @Override
    public ResponseEntity<InputStreamResource> exportFile(String fileName, long repositoryId, HttpServletRequest request,
                                                          HttpServletResponse response) throws Exception {
        String pathFile = "";
        if(ObjectUtils.isNotEmpty(repositoryId)) {
            JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
            pathFile = repo.getPhysicalPath() + "/" + fileName;
        } else {

            pathFile = fileName;
        }
        File fileTemp = new File(pathFile);
        if (fileTemp.exists()) {
            List<String> names = new ArrayList<>();
            CollectionUtils.addAll(names, fileName.split("/"));
            String name = fileName;
            if(CollectionUtils.isNotEmpty(names)) {
                name = names.get(names.size() - 1);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add(CommonConstant.CONTENT_DISPOSITION,
                    CommonConstant.ATTCHMENT_FILENAME + name + "\"");
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType(getContentType(name)))
                    .contentLength(fileTemp.length()).body(new InputStreamResource(new FileInputStream(fileTemp)));
        }
        return null;
    }

    @Override
    public ResponseEntity<InputStreamResource> fetchExportedDocument(FileReq exportFileReq,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) throws Exception {
        if (exportFileReq == null || StringUtils.isEmpty(exportFileReq.getFileName())) {
            return ResponseEntity.badRequest().build();
        }

        String fileName = exportFileReq.getFileName();
        long repositoryId = ObjectUtils.isNotEmpty(exportFileReq.getRepoId()) ?
                Long.parseLong(exportFileReq.getRepoId()) : 0;

        String pathFile = "";
        if (repositoryId > 0) {
            JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
            if (repo != null) {
                pathFile = repo.getPhysicalPath() + "/" + fileName;
            }
        } else {
            pathFile = fileName;
        }

        File fileTemp = new File(pathFile);
        if (!fileTemp.exists()) {
            return ResponseEntity.notFound().build();
        }

        List<String> names = new ArrayList<>();
        CollectionUtils.addAll(names, fileName.split("/"));
        String name = CollectionUtils.isNotEmpty(names) ? names.get(names.size() - 1) : fileName;

        HttpHeaders headers = new HttpHeaders();
        headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + name + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(getContentType(name)))
                .contentLength(fileTemp.length())
                .body(new InputStreamResource(new FileInputStream(fileTemp)));
    }

    @Override
    public void fetchDownloadedDocument(FileReq downloadFileReq, HttpServletRequest request, HttpServletResponse response) throws DetailException {
        String fileName = downloadFileReq.getFileName();
        String repoIdStr = downloadFileReq.getRepoId();
        long repositoryId = (StringUtils.isNotEmpty(repoIdStr) && StringUtils.isNumeric(repoIdStr))
                ? Long.parseLong(repoIdStr) : 0;

        if (StringUtils.isEmpty(fileName)) {
            return;
        }
        try {
            JcaRepositoryDto repo = null;
            if (ObjectUtils.isNotEmpty(repositoryId)) {
                repo = jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
            }
            String repoPath = (repo != null && StringUtils.isNotEmpty(repo.getPhysicalPath())) ? repo.getPhysicalPath() : "";
            String pathFile = StringUtils.isNotEmpty(repoPath) ? repoPath + "/" + fileName : fileName;

            File fileTemp = new File(pathFile);
            if (repo != null && StringUtils.isNotEmpty(repo.getPhysicalPath()) &&
                    !StringUtils.containsIgnoreCase(fileTemp.getCanonicalPath(), Paths.get(repo.getPhysicalPath()).toString())) {
                throw new IOException("invalid path");
            }

            if (fileTemp.exists()) {
                FileInputStream fileInputStream = new FileInputStream(fileTemp);
                byte[] arr = new byte[1024];
                int numRead = -1;
                response.addHeader("Content-Length", Long.toString(fileTemp.length()));
                if (StringUtils.isNotEmpty(request.getParameter("isDownload"))
                        && request.getParameter("isDownload").equalsIgnoreCase("download")) {
                    response.setContentType("application/octet-stream");
                } else {
                    response.setContentType(getContentType(fileName));
                }

                response.addHeader("Content-Disposition",
                        "inline; filename=\"" + FileUtil.deAccent(fileTemp.getName()) + "\"");

                while ((numRead = fileInputStream.read(arr)) != -1) {
                    response.getOutputStream().write(arr, 0, numRead);
                }
                fileInputStream.close();
            } else {
                return;
            }
        } catch (IOException e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024801_APPAPI_DOWNLOAD_FILE_LOCAL_ERROR);
        }
    }
    
    @Override
    public void deleteFile(String fileName, Long repositoryId) throws Exception {
        if (StringUtils.isEmpty(fileName)) {
            return;
        }
        // path main
		String pathFile = "";
		JcaRepositoryDto repo = new JcaRepositoryDto();
		if(ObjectUtils.isNotEmpty(repositoryId)) {
		    repo = jcaRepositoryService.getJcaRepositoryDtoById(repositoryId);
		    pathFile = repo.getPhysicalPath() + "/" + fileName;
		} else {
		    pathFile = fileName;
		}
		File fileTemp = new File(pathFile);
		if (fileTemp.exists()) {
			fileTemp.delete();
		} else {
		    return;
		}
    }
}
