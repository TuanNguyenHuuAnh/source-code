package vn.com.unit.cms.admin.all.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.UrlConst;
import vn.com.unit.ep2p.admin.constant.Utils;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.ep2p.dialect.FileMeta;
import vn.com.unit.ep2p.utils.CompressJPEGFile;
import vn.com.unit.ep2p.utils.ConvertVideo;

@Controller
@RequestMapping("/cmsAjax")
public class CmsFileController {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private ServletContext servletContext;

    /** files */
    private LinkedList<FileMeta> files = new LinkedList<FileMeta>();

    /** fileMeta */
    private FileMeta fileMeta = null;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CmsFileController.class);

    /** UNDERSCORE */
    private static final String UNDERSCORE = "__";

    /** EXTENSION OF IMAGE FILES */
    private static final String EXTENSION_IMAGE = "jpg-jpeg-bmp-png-tiff-pnj-jfif";

    /** EXTENSION OF DOCUMENT FILES */
    private static final String EXTENSION_DOCUMENT = "doc-docx-xls-xlsx-pdf";

    /** MESSAGE FOR FILE EXTENSION NOT SUPPORT */
    private static final String MSG_ERROR_EXTENSION_INVALID = "File format is not supported";

    @Autowired
    private CmsFileService cmsFileService;

    /**
     * upload to folder temp
     *
     * @param request
     * @param request2
     * @param model
     * @param response
     * @return String
     * @author hand
     * @throws IOException
     */
    @RequestMapping(value = "/uploadTemp", method = RequestMethod.POST)
    public @ResponseBody String uploadTemp(@RequestParam(required = false, value = "urlFunction") String urlFunction,
            @RequestParam(required = false, value = "requestToken") String requestToken,
            MultipartHttpServletRequest request, HttpServletRequest request2, Model model, HttpServletResponse response)
            throws IOException {

        // build an iterator
        Iterator<String> itr = request.getFileNames();

        // MultipartFile
        MultipartFile mpf = null;

        // source
        String source = StringUtils.EMPTY;

        // path temp
        String path = cmsFileService.getPathTemp();

        if (!StringUtils.isEmpty(path)) {
            // create directory if not exists
            FileUtil.createDirectoryNotExists(path);

            // loop iterator filenames
            while (itr.hasNext()) {
                // get next MultipartFile
                mpf = request.getFile(itr.next());

                // extensao
                String extensao = StringUtils.EMPTY;

                int extentionIndex = mpf.getOriginalFilename().lastIndexOf('.');

                // file khong co extent
                if (extentionIndex == -1) {
                    logger.error(MSG_ERROR_EXTENSION_INVALID);
                    throw new IOException(MSG_ERROR_EXTENSION_INVALID);
                } else {
                    extensao = mpf.getOriginalFilename()
                            .substring(extentionIndex + 1, mpf.getOriginalFilename().length()).toLowerCase();
                    if (!EXTENSION_DOCUMENT.contains(extensao) && !EXTENSION_IMAGE.contains(extensao)) {
                        logger.error(MSG_ERROR_EXTENSION_INVALID);
                        throw new IOException(MSG_ERROR_EXTENSION_INVALID);
                    }
                    // remove dau, ky tu dac biet
                    String newName = Utils.convertNewFileName(mpf.getOriginalFilename().substring(0, extentionIndex));
                    source = ConstantCore.AT_FILE + newName + UNDERSCORE + (new Date()).getTime() + ConstantCore.DOT
                            + extensao;
                    if ("gifimgjpejpgejpgpngtiff".contains(extensao)) {
                        String subPath;
                        if (UrlConst.BANNER.equals(urlFunction)) {
                            subPath = Paths.get(path, "banner", "editor", requestToken).toString();
                        } else if (UrlConst.SYSTEM_CONFIG.equals(urlFunction)) {
                            subPath = Paths.get(path, "picture").toString();
                        } else {
                            subPath = Paths.get(path, "account_avatar").toString();
                        }

                        File file = new File(subPath);
                        if (!file.exists()) {
                            try {
                                file.mkdir();
                            } catch (Exception e) {
                                logger.error("Error: " + e);
                            }
                        }

                        if (UrlConst.BANNER.equals(urlFunction)) {
                            source = Paths.get("banner", "editor", requestToken, source).toString();
                        } else if (UrlConst.SYSTEM_CONFIG.equals(urlFunction)) {
                            source = Paths.get("picture", source).toString();
                        } else {
                            source = Paths.get("account_avatar", source).toString();
                        }

                    }
                }

                try {
                    // save to folder temp
                    File destFile = new File(path, source);
                    FileUtil.setPermission(destFile);
                    FileCopyUtils.copy(mpf.getBytes(),
                            new FileOutputStream(FilenameUtils.separatorsToSystem(destFile.getPath())));
//					FileCopyUtils.copy(mpf.getBytes(),
//							new FileOutputStream(Paths.get(path, source).toString()));
                } catch (FileNotFoundException e) {
                    // log file not found
                    logger.error(e + ":" + e.getMessage());
                    throw new SystemException(e.getMessage());
                } catch (IOException e) {
                    // log IOException
                    logger.error(e + ":" + e.getMessage());
                    throw new SystemException(e.getMessage());
                }
            }

        } else {
            logger.error("path not found");
        }

        return source.replace("\\", "/");
    }

    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    public @ResponseBody List<String> uploadVideo(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response) throws IOException {

        // build an iterator
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        List<String> filenameLst = new ArrayList<String>();
        // path tmp
        String path = cmsFileService.getPathTemp();

        // create directory if not exists
        FileUtil.createDirectoryNotExists(path);

        while (itr.hasNext()) {
            // get next MultipartFile
            mpf = request.getFile(itr.next());
            String extensao = StringUtils.EMPTY;
            String source = StringUtils.EMPTY;

            // file khong co extent
            if (mpf.getOriginalFilename().lastIndexOf('.') == -1) {
                source = ConstantCore.AT_FILE
                        + mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.'))
                        + UNDERSCORE + (new Date()).getTime();
            } else {
                extensao = mpf.getOriginalFilename()
                        .substring(mpf.getOriginalFilename().lastIndexOf('.') + 1, mpf.getOriginalFilename().length())
                        .toLowerCase();
                // remove dau, ky tu dac biet
                String newName = Utils.convertNewFileName(
                        mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')));
                source = ConstantCore.AT_FILE + newName + UNDERSCORE + (new Date()).getTime() + ConstantCore.DOT
                        + extensao;
            }

            try {
                FileCopyUtils.copy(mpf.getBytes(),
                        new FileOutputStream(FilenameUtils.separatorsToSystem(Paths.get(path, source).toString())));

                String target;
                // extensao is "avi" or "wmv" or "wma"
                if (extensao.equals("avi") || extensao.equals("wmv") || extensao.equals("wma")) {
                    // convert to flv
                    target = ConvertVideo.convert(path, source);
                } else if (extensao.equals("pnj") || extensao.equals("jpg") || extensao.equals("jpeg")) {
                    // compress image
                    target = CompressJPEGFile.process(path, source);
                } else {
                    // don't convert
                    target = source;
                }
                filenameLst.add(target);
            } catch (FileNotFoundException e) {
                // log file not found
                logger.error(e + ":" + e.getMessage());
            } catch (IOException e) {
                // log IOException
                logger.error(e + ":" + e.getMessage());
            } catch (Exception e) {
                // log IOException
                logger.error(e + ":" + e.getMessage());
            }
        }

        return filenameLst;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam(required = true, value = "fileName") String fileName, HttpServletRequest request,
            HttpServletResponse response) {
        if (StringUtils.isEmpty(fileName)) {
            return;
        }

        try {
            String domain = StringUtils.EMPTY;
            // path main
            String pathMain = cmsFileService.getPathMain();

            String path = StringUtils.EMPTY;
            File fileTempCheck = new File(Paths.get(domain, pathMain, fileName).toString());

            if (!fileTempCheck.exists() && fileName.contains(ConstantCore.AT_FILE)) {
                // path temp
                path = Paths.get(cmsFileService.getPathTemp(), fileName).toString();
            } else {
                path = Paths.get(pathMain, fileName).toString();
            }
            File fileTemp = new File(FilenameUtils.separatorsToSystem(Paths.get(domain, path).toString()));

            try (FileInputStream fileInputStream = new FileInputStream(fileTemp)) {
                byte[] arr = new byte[1024];
                int numRead = -1;

                response.addHeader("Content-Length", Long.toString(fileTemp.length()));
                if (StringUtils.isNotEmpty(request.getParameter("isDownload"))
                        && request.getParameter("isDownload").equalsIgnoreCase("download")) {
                    response.setContentType("application/octet-stream");
                } else {
                    response.setContentType(FileUtil.getContentType(fileName));
                }
                response.addHeader("Content-Disposition", "inline; filename=\"" + fileTemp.getName() + "\"");
                while ((numRead = fileInputStream.read(arr)) != -1) {
                    response.getOutputStream().write(arr, 0, numRead);
                }
            }
        } catch (IOException e) {
            logger.error(e + ":" + e.getMessage());
        }
    }

    /***************************************************
     * URL: /rest/controller/upload upload(): receives files
     * 
     * @param request  : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     ****************************************************/
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request,
            HttpServletResponse response) {

        // Build an iterator
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;

        // 2. get each file
        while (itr.hasNext()) {

            // 2.1 get next MultipartFile
            mpf = request.getFile(itr.next());

            // 2.2 if files > 10 remove the first from the list
            if (files.size() >= 10)
                files.pop();

            // 2.3 create new fileMeta
            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize());
            fileMeta.setFileType(mpf.getContentType());
            fileMeta.setPhysicalFileName(mpf.getOriginalFilename() + (new Date()).getTime());

            try {
                fileMeta.setBytes(mpf.getBytes());
                logger.debug("temp path: {}", systemConfig.getConfig(SystemConfig.TEMP_FOLDER)
                        + System.getProperty("file.separator") + mpf.getOriginalFilename());
                // copy file to local disk (make sure the path
                // "e.g. D:/temp/files" exists)
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(systemConfig.getConfig(SystemConfig.TEMP_FOLDER)
                        + System.getProperty("file.separator") + fileMeta.getPhysicalFileName()));

            } catch (IOException e) {
                logger.error(e + ":" + e.getMessage());
            }
            // 2.4 add to files
            files.add(fileMeta);

        }

        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8
        // Kb","fileType":"image/png"},...]
        return files;

    }

    @RequestMapping(value = "/uploadDoc", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    // public @ResponseBody FileMeta uploadDocument(MultipartHttpServletRequest
    // request, HttpServletResponse response) {
    public @ResponseBody String uploadDocument(MultipartHttpServletRequest request, HttpServletResponse response) {
        // 1. build an iterator
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;

        // 2. get each file
        if (itr.hasNext()) {
            // 2.1 get next MultipartFile
            mpf = request.getFile(itr.next());

            // 2.2 if files > 10 remove the first from the list
            if (files.size() >= 10)
                files.pop();

            // 2.3 create new fileMeta
            String fileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1);
            if (!EXTENSION_DOCUMENT.contains(fileExtension)) {
                logger.error(MSG_ERROR_EXTENSION_INVALID);
                return StringUtils.EMPTY;
            }
            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize());
            fileMeta.setFileType(fileExtension);
            fileMeta.setPhysicalFileName(Utils.convertNewFileName(
                    mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')))
                    + (new Date()).getTime() + ConstantCore.DOT + fileMeta.getFileType());
            try {
                fileMeta.setBytes(mpf.getBytes());
                String temp_folder = systemConfig.getConfig(SystemConfig.TEMP_FOLDER);
                temp_folder = temp_folder.endsWith(System.getProperty("file.separator")) == true ? temp_folder
                        : temp_folder + System.getProperty("file.separator");
                logger.debug("temp path: {}", temp_folder + fileMeta.getPhysicalFileName());
                // copy file to local disk (make sure the path
                // "e.g. D:/temp/files" exists)
                File f = new File(temp_folder + fileMeta.getPhysicalFileName());
                if (!f.exists())
                    FileUtil.setPermission(f);

                if (f.createNewFile()) {
                    FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(f));
                } else {
                    logger.error("file can not create");
                }
            } catch (IOException e) {
                logger.error(e + ":" + e.getMessage());
            }
            // 2.4 add to files

        }
        response.setContentType("text/plain");
        response.setHeader("Content-Type", "text/plain");
        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8
        // Kb","fileType":"image/png"},...]
        ObjectMapper mapper = new ObjectMapper();
        String result = StringUtils.EMPTY;
        try {
            result = mapper.writeValueAsString(fileMeta);
        } catch (JsonGenerationException e) {
            logger.error(e + ":" + e.getMessage());
        } catch (JsonMappingException e) {
            logger.error(e + ":" + e.getMessage());
        } catch (IOException e) {
            logger.error(e + ":" + e.getMessage());
        }
        // return fileMeta;
        return result;
    }

    /***************************************************
     * URL: /rest/controller/get/{value} get(): get file as an attachment
     * 
     * @param response : passed by the server
     * @param value    : value from the URL
     * @return void
     ****************************************************/
    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response, @PathVariable String value) {
        FileMeta getFile = files.get(Integer.parseInt(value));
        try {
            response.setContentType(getFile.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
            FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
        } catch (IOException e) {
            logger.error(e + ":" + e.getMessage());
        }
    }

    @RequestMapping(value = "/showVideo_PopupHomepage", method = RequestMethod.GET)
    public String showVideo_PopupHomepage(Model model, HttpServletRequest request, Locale locale) throws Exception {

        String fileName = request.getParameter("fileName");
        String language = request.getParameter("language");
        model.addAttribute("fileName", fileName);
        model.addAttribute("language", language);

        return "master_data.popup_homepage.video";
    }

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void preview(@RequestParam(required = true) String fileName,
            @RequestParam(required = false) String downloadViewID, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            File fileTemp = new File(servletContext.getRealPath("/WEB-INF/temp_folder")
                    + System.getProperty("file.separator") + fileName);

            try (FileInputStream fileInputStream = new FileInputStream(fileTemp)) {
                byte[] arr = new byte[1024];
                int numRead = -1;

                response.addHeader("Content-Length", Long.toString(fileTemp.length()));
                response.setContentType("application/pdf");

                response.addHeader("Content-Disposition", "attachment; filename=\"" + fileTemp.getName() + "\"");
                while ((numRead = fileInputStream.read(arr)) != -1) {
                    response.getOutputStream().write(arr, 0, numRead);
                }
            }
        } catch (IOException e) {
            logger.error("__preview__", e);
        }
    }

    @RequestMapping(value = "/uploadFileChat", method = RequestMethod.POST)
    public @ResponseBody String uploadFileChat(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response) throws IOException {

        // build an iterator
        Iterator<String> itr = request.getFileNames();

        // MultipartFile
        MultipartFile mpf = null;

        // source
        String source = StringUtils.EMPTY;

        // path temp
        String folderName = "chat/";
        String path = cmsFileService.getPathMain();

        if (!StringUtils.isEmpty(path)) {
            // create directory if not exists
            FileUtil.createDirectoryNotExists(path);

            // loop iterator filenames
            while (itr.hasNext()) {
                // get next MultipartFile
                mpf = request.getFile(itr.next());

                // extensao
                String extensao = StringUtils.EMPTY;

                // file khong co extent
                if (mpf.getOriginalFilename().lastIndexOf('.') == -1) {
                    source = ConstantCore.AT_FILE
                            + mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.'))
                            + UNDERSCORE + (new Date()).getTime();
                } else {
                    extensao = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1,
                            mpf.getOriginalFilename().length()).toLowerCase();

                    // remove dau, ky tu dac biet
                    String newName = Utils.convertNewFileName(
                            mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')));
                    source = newName + UNDERSCORE + (new Date()).getTime() + ConstantCore.DOT + extensao;
                }

                try {
                    // save to folder temp
                    FileCopyUtils.copy(mpf.getBytes(),
                            new FileOutputStream(FilenameUtils.separatorsToSystem(path + source)));
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }
            }

        } else {
        }

        return (folderName + source);
    }

    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response) {
        boolean retVal = false;
        if (fileUrl != null) {
            if (CmsUtils.fileExistedInMain(fileUrl)) {
                cmsFileService.download(fileUrl, request, response);
                retVal = true;
            } else if (CmsUtils.fileExistedInTemp(fileUrl)) {
                cmsFileService.downloadTemp(fileUrl, request, response);
                retVal = true;
            }
        }
        return retVal;
    }

    /**
     * editorDownload
     *
     * @param fileUrl
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @author TaiTM
     */
    @RequestMapping(value = "/editor/download", method = RequestMethod.GET)
    public void editorDownload(@RequestParam(required = true, value = "url") String fileUrl, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String url = AdminConstant.NEWS_EDITOR_FOLDER + "/" + fileUrl;
        requestEditorDownload(url, request, response);
    }

    /**
     * editorUpload
     *
     * @param request
     * @param request2
     * @param model
     * @param response
     * @param requestToken
     * @param funcNum
     * @return
     * @throws JSONException
     * @author TaiTM
     * @throws IOException
     */
    @RequestMapping(value = "/editor/upload", method = RequestMethod.POST)
    public @ResponseBody String editorUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response,
            @RequestParam(required = true, value = "requesttoken") String requestToken,
            @RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
            throws JSONException, IOException {
        String fileName = cmsFileService.uploadTemp(request, request2, model, response,
                AdminConstant.NEWS_EDITOR_FOLDER, requestToken);
        String fileUrl = URLEncoder.encode(fileName, "UTF-8");
        String downloadUrl = request2.getContextPath().concat("/editor/download?url=").concat(fileUrl);
        return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
                + "</script>";
    }

    /**
     * upload file CKEDITOR
     * 
     * @param request
     * @param request2
     * @param model
     * @param response
     * @param requestToken
     * @param funcNum
     * @return
     * @throws JSONException
     * @throws IOException
     */
    @RequestMapping(value = "/editor/file/upload", method = RequestMethod.POST)
    public @ResponseBody String editorFileUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response,
            @RequestParam(required = true, value = "requesttoken") String requestToken,
            @RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
            throws JSONException, IOException {
        String fileName = cmsFileService.uploadTemp(request, request2, model, response,
                Paths.get(UrlConst.NEWS, AdminConstant.EDITOR_FOLDER).toString(), requestToken);
        String fileUrl = URLEncoder.encode(fileName, "UTF-8");
        String downloadUrl = request2.getContextPath().concat("/editor/download?url=").concat(fileUrl);
        return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
                + "</script>";
    }
}
