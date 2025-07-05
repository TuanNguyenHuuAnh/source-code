/*******************************************************************************
 * Class        FileServiceImpl
 * Created date 2017/04/25
 * Lasted date  2017/04/25
 * Author       hand
 * Change log   2017/04/2501-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.service.CmsFileService;
//import vn.com.unit.constant.ConstantCore;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Utils;
import vn.com.unit.ep2p.admin.utils.URLUtil;
import vn.com.unit.ep2p.core.utils.FileUtil;


/**
 * FileServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CmsFileServiceImpl implements CmsFileService {
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CmsFileService.class);
    
    @Autowired
    ServletContext servletContext;
    
    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    private JRepositoryService jRepositoryService;
    
    @Override
    public String getPathTemp() {
        String path = systemConfig.getConfig(SystemConfig.PHYSICAL_PATH_TEMP);
        if (StringUtils.isBlank(path)) {
            path = jRepositoryService.getPathByRepository(SystemConfig.REPO_UPLOADED_TEMP);
        }
        return path;
    }

    @Override
    public String getPathMain() {
        String path = systemConfig.getConfig(SystemConfig.PHYSICAL_PATH_MAIN);
        if (StringUtils.isBlank(path)) {
            path = jRepositoryService.getPathByRepository(SystemConfig.REPO_UPLOADED_MAIN);
        }
        return path;
    }

    /**
     * download
     *
     * @param fileName
     * @param request
     * @param response
     * @author hand
     */
    @Override
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(fileName)) {
            return;
        }

		// clean path
		fileName = URLUtil.cleanPath(fileName);
		
        try {
            // path main
            String pathRepo = getPathMain();
            File fileTemp = Paths.get(pathRepo, fileName).toFile();
            if (fileTemp.exists()) {
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
            } else {
                return;
            }
        } catch (IOException e) {
            logger.error(e + ":" + e.getMessage());
        }
    }
    
    @Override
    public void downloadTemp(String fileName, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(fileName)) {
            return;
        }

		// clean path
		fileName = URLUtil.cleanPath(fileName);

        try {
            String pathRepo = getPathTemp();
            File fileTemp = Paths.get(pathRepo, fileName).toFile();
			if (fileTemp.exists()) {
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
			} else {
				return;
			}
            
        } catch (IOException e) {
            logger.error(e + ":" + e.getMessage());
        }
    }
    
    @Override
    public String uploadTemp(MultipartHttpServletRequest request, HttpServletRequest request2, Model model,
            HttpServletResponse response, String destFolderName, String destSubFolderName) throws IOException {


        // build an iterator
        Iterator<String> itr = request.getFileNames();

        // MultipartFile
        MultipartFile mpf = null;

        // source
        String source = StringUtils.EMPTY;

        // path temp
        String tempPath = getPathTemp();
        
        if (!StringUtils.isEmpty(tempPath)) {
            // create directory if not exists
            String path = Paths.get(tempPath, destFolderName, destSubFolderName).toString();
            File destSubFolder = new File(path);
            path = destSubFolder.getPath();
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
                            + ConstantCore.UNDERSCORE + (new Date()).getTime();
                } else {
                    extensao = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1,
                            mpf.getOriginalFilename().length()).toLowerCase();
                    String newName = Utils.convertNewFileName(
                            mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')));
                    source = ConstantCore.AT_FILE + newName + ConstantCore.UNDERSCORE + (new Date()).getTime() + ConstantCore.DOT
                            + extensao;
                }
                try {
                    File destFile = new File(path, source);
                    FileUtil.setPermission(destFile);
                    FileCopyUtils.copy(mpf.getBytes(),
                            new FileOutputStream(FilenameUtils.separatorsToSystem(destFile.getPath())));
                } catch (FileNotFoundException e) {
                    logger.error(e + ":" + e.getMessage());
                } catch (IOException e) {
                    logger.error(e + ":" + e.getMessage());
                }
            }
        } else {
            logger.error("path not found");
        }
        return Paths.get(destSubFolderName, source).toString();
    }
    
    @Override
    public String uploadImageTemp(MultipartHttpServletRequest request, HttpServletRequest request2, Model model,
            HttpServletResponse response, String destFolderName, String destSubFolderName, Integer widthImg, Integer heightImg) throws IOException {
        // build an iterator
        Iterator<String> itr = request.getFileNames();

        // MultipartFile
        MultipartFile mpf = null;

        // source
        String source = StringUtils.EMPTY;

        // path temp
        String tempPath = getPathTemp();
        
        if (!StringUtils.isEmpty(tempPath)) {
            // create directory if not exists
            String path = Paths.get(tempPath, destFolderName, destSubFolderName).toString();
            File destSubFolder = new File(path);
            path = destSubFolder.getPath();
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
                            + ConstantCore.UNDERSCORE + (new Date()).getTime();
                } else {
                    extensao = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1,
                            mpf.getOriginalFilename().length()).toLowerCase();
                    String newName = Utils.convertNewFileName(
                            mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')));
                    source = ConstantCore.AT_FILE + newName + ConstantCore.UNDERSCORE + (new Date()).getTime()
                            + ConstantCore.DOT + extensao;
                }
                try {
                    File destFile = new File(path, source);
                    FileUtil.setPermission(destFile);
                    
                    if (widthImg != null && heightImg != null) {
                        BufferedImage inputImage = ImageIO.read(mpf.getInputStream());

                        // creates output image
                        BufferedImage outputImage = new BufferedImage(widthImg, heightImg, inputImage.getType());

                        // scales the input image to the output image
                        Graphics2D g2d = outputImage.createGraphics();
                        g2d.drawImage(inputImage.getScaledInstance(widthImg, heightImg, Image.SCALE_SMOOTH), 0, 0, widthImg, heightImg, null);
                        g2d.dispose();

                        // extracts extension of output file
                        String formatName = destFile.getPath().substring(destFile.getPath().lastIndexOf(".") + 1);

                        // writes to output file
                        ImageIO.write(outputImage, formatName, new File(FilenameUtils.separatorsToSystem(destFile.getPath())));
                    } else {
                        FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(FilenameUtils.separatorsToSystem(destFile.getPath())));
                    }
                } catch (FileNotFoundException e) {
                    logger.error(e + ":" + e.getMessage());
                } catch (IOException e) {
                    logger.error(e + ":" + e.getMessage());
                }
            }
        } else {
            logger.error("path not found");
        }
        return Paths.get(destSubFolderName, source).toString();
    }
    
//    @Override
//    public void webCopyRepositoryFiles(String repositoryDataLocation) throws IOException{
//        File sourceParentFolder = Paths.get(systemConfig.getConfig(SystemConfig.PHYSICAL_PATH_MAIN), repositoryDataLocation).toFile();
//        File[] contentDirectories = sourceParentFolder.listFiles();
//        for(File contentDirectory : contentDirectories){
//            if(contentDirectory.isDirectory()){
//                this.webCopyDirectoryDataFiles(Paths.get(repositoryDataLocation, contentDirectory.getName()).toString(), contentDirectory);
//            }else{
//                this.webCopyDataFile(repositoryDataLocation, contentDirectory);
//            }
//        }
//    }
//    
//    @Override
//    public boolean webCopyDataFile(String webSubDirectoryPath, String relativeFilePath) throws IOException {
//        String reposMainPath = systemConfig.getConfig(SystemConfig.PHYSICAL_PATH_MAIN);
//        String srcFilePath = Paths.get(reposMainPath, relativeFilePath).toString();
//        File sourceFile = new File(srcFilePath);
//        if (sourceFile.exists()) {
//            this.webCopyDataFile(webSubDirectoryPath, sourceFile);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    
//    @Override
//    public void webCopyInnerDataFiles(String content, String reposSubDirectoryPath, String webSubdirectoryPath, String pathExtractRegex) throws IOException{
//        Pattern patt = Pattern.compile(pathExtractRegex);
//        Matcher matcher = patt.matcher(content);
//        matcher.matches();
//        while(matcher.find()){
//            String fileName = matcher.group(3);
//            String domain = StringUtils.EMPTY;
//            String decodeFileUrl = URLDecoder.decode(fileName, "UTF-8");
//            String lastFolderPath = webSubdirectoryPath;
//            
//            File webRootDir = new File(servletContext.getRealPath(WEB_INF_DIRECTORY_NAME), lastFolderPath);
//            if(!webRootDir.exists()){
////                Utils.setPermission(webRootDir);
//                webRootDir.mkdirs();
//            }
//            String webFilePath = Paths.get(webRootDir.getPath(), decodeFileUrl).toString();
//            File webFile = new File(webFilePath);
//            if(!webFile.exists()){
//                String pathMain = systemConfig.getConfig(SystemConfig.PHYSICAL_PATH_MAIN);
//                
//                String path = Paths.get(domain, pathMain,reposSubDirectoryPath, decodeFileUrl).toString();
//                String[] decodeFileUrlComponent = decodeFileUrl.split("[\\\\/]{1,}");
//                if(decodeFileUrlComponent.length > 1){
//                    String folderPath = "";
//                    for(int index = 0;index < decodeFileUrlComponent.length -1; ++index){
//                        folderPath = Paths.get(folderPath, decodeFileUrlComponent[index]).toString();
//                    }
//                    if(!StringUtils.isEmpty(folderPath)){
//                        lastFolderPath = Paths.get(lastFolderPath, folderPath).toString();
//                    }
//                }
//                File fileTemp = new File(FilenameUtils.separatorsToSystem(path));
//                if(fileTemp.exists()){
//                    this.webCopyDataFile(lastFolderPath, fileTemp);
//                }
//            }
//        }
//    }
//    
//    private void webCopyDirectoryDataFiles(String destRelativeDirectoryPath, File sourceFolder) throws IOException{
//        String webContentDirectoryPath = Paths.get(servletContext.getRealPath(WEB_INF_DIRECTORY_NAME),destRelativeDirectoryPath ).toString();
//        File webContentDirectory = new File(webContentDirectoryPath);
//        if(!webContentDirectory.exists()){
////            Utils.setPermission(webContentDirectory);
//            webContentDirectory.mkdirs();
//        }
//        File[] fileList = sourceFolder.listFiles();
//        for(File file : fileList){
//            this.webCopyDataFile(destRelativeDirectoryPath, file);
//        }
//    }
//    
//    private String webCopyDataFile(String webSubDirectoryName, File sourceFile) throws IOException{
//        String fileName = sourceFile.getName();
//        File rootDir = new File(servletContext.getRealPath(WEB_INF_DIRECTORY_NAME), webSubDirectoryName);
//        if(!rootDir.exists()){
////            Utils.setPermission(rootDir);
//            rootDir.mkdirs();
//        }
//        File destFile = new File(rootDir.getPath(), fileName);
//        if(!destFile.exists()){
////            FileUtil.setPermission(destFile);
//            FileCopyUtils.copy(sourceFile, destFile);
//        }
//        String destFilePath = destFile.getPath();
//        return destFilePath;
//    }
//    
//    /**
//     * 
//     * @param request
//     * @param request2
//     * @param model
//     * @param response
//     * @return
//     * @throws IOException 
//     */
    @Override
    public String uploadImageChat(MultipartHttpServletRequest request, HttpServletRequest request2, Model model,
            HttpServletResponse response) throws IOException {
        // build an iterator
        Iterator<String> itr = request.getFileNames();

        // MultipartFile
        MultipartFile mpf = null;

        // source
        String source = StringUtils.EMPTY;

        // path temp
        String folderName = "chat/";
        String pathRepo = getPathMain();
        String path = Paths.get(pathRepo, folderName).toString();

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
                            + ConstantCore.UNDERSCORE + (new Date()).getTime();
                } else {
                    extensao = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1,
                            mpf.getOriginalFilename().length()).toLowerCase();

                    // remove dau, ky tu dac biet
                    String newName = Utils.convertNewFileName(
                            mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')));
                    source =newName + ConstantCore.UNDERSCORE + (new Date()).getTime() + ConstantCore.DOT
                            + extensao;
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

//    /**
//     * @param fileUrl
//     * @param request
//     * @param response
//     * @return
//     */
    @Override
    public void requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response) {
        if (fileUrl != null) {
            if (this.isFileExistedByRepository(fileUrl, getPathMain())) {
                this.download(fileUrl, request, response);
            } else if (this.isFileExistedByRepository(fileUrl, getPathTemp())) {
                this.downloadTemp(fileUrl, request, response);
            }
        }
    }
    
//    /**
//     * Check file or folder existed in repository
//     * 
//     * @param fileOrFolderName
//     *          type String
//     * @param repositoryKey (SystemConfig.REPO_*)
//     *          type String
//     * @return boolean
//     * @author KhoaNA
//     */
    private boolean isFileExistedByRepository(String fileOrFolderName, String repositoryKey) {
        String filePath = Paths.get(repositoryKey, fileOrFolderName).toString();
        return FileUtil.isFileExisted(filePath);
    }

//    /**
//     * Move file from repository temp to folder upload main
//     * 
//     * @param fileName
//     *          type String
//     * @param folderName
//     *          type String
//     * @return String
//     * @author HaND
//     */
    public String moveFileFromTempToFolderUploadMain(String fileName, String folderName) throws IOException {
        if (fileName.startsWith(ConstantCore.AT_FILE) == true) {
            String newName = fileName.substring(fileName.lastIndexOf("@") + 1);
            // file source
            File source = Paths.get(getPathTemp(), fileName).toFile();

            String pathTarget = Paths.get(getPathMain(), fileName).toString();

            // create directory taget if not exists
            FileUtil.createDirectoryNotExists(pathTarget);

            // file target
            File target = new File(pathTarget, newName);
            if(!target.exists()){
                FileUtil.setPermission(target);
//                target.createNewFile();
            }
            try {
                FileCopyUtils.copy(source, target);
               
                if(!source.delete()){
                	logger.error("soure not delete");
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return Paths.get(folderName, newName).toString();
        } else {
            return fileName;
        }
    }

	@Override
	public void downloaFileWord(String fileName, HttpServletRequest request, HttpServletResponse response) {
		 if (StringUtils.isEmpty(fileName)) {
	            return;
	        }
		 
		// clean path
		fileName = URLUtil.cleanPath(fileName);

	        try {
	            // path main
	        	String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + fileName
						+ CmsCommonConstant.TYPE_WORD;
	        	File fileTemp = new File(template);
	            if (fileTemp.exists()) {
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
	            } else {
	                return;
	            }
	        } catch (IOException e) {
	            logger.error(e + ":" + e.getMessage());
	        }
	}

}
