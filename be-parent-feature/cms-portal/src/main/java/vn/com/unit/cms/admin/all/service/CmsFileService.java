/*******************************************************************************
 * Class        FileService
 * Created date 2017/04/25
 * Lasted date  2017/04/25
 * Author       hand
 * Change log   2017/04/2501-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
//import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * FileService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface CmsFileService {

    /**
     * download
     *
     * @param fileName
     * @param request
     * @param response
     * @author hand
     */
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response);
    
    /**
     * @param fileName
     * @param request
     * @param response
     */
    public void downloadTemp(String fileName, HttpServletRequest request, HttpServletResponse response);
    
    /**
     * @param request
     * @param request2
     * @param model
     * @param response
     * @param destFolder
     * @return
     * @throws IOException 
     */
    public String uploadTemp(MultipartHttpServletRequest request, HttpServletRequest request2, Model model,
            HttpServletResponse response, String destFolderName, String destSubFolderName) throws IOException;
        
    /** uploadImageTemp
     *
     * @param request
     * @param request2
     * @param model
     * @param response
     * @param destFolderName
     * @param destSubFolderName
     * @param widthImg
     * @param heightImg
     * @throws IOException
     * @author nhutnn
     */
    public String uploadImageTemp(MultipartHttpServletRequest request, HttpServletRequest request2, Model model,
            HttpServletResponse response, String destFolderName, String destSubFolderName, Integer widthImg, Integer heightImg) throws IOException;
    /**
     * @param webSubdirectoryName
     * @param repositorySubDirectoryName
     * @param contentSubDirectoryName
     * @throws IOException 
     */
//    public void webCopyRepositoryFiles(String repositoryDataLocation) throws IOException;
    
    /**
     * 
     * @param request
     * @param request2
     * @param model
     * @param response
     * @return
     * @throws IOException 
     */
    public String uploadImageChat(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response) throws IOException;
    
    /**
     * 
     * @param content
     * @param reposSubDirectoryPath
     * @param webSubdirectoryPath
     * @param pathExtractRegex
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
//    public void webCopyInnerDataFiles(String content,String reposSubDirectoryPath, String webSubdirectoryPath, String pathExtractRegex) throws UnsupportedEncodingException, IOException;
    
    /**
     * 
     * @param webSubDirectoryPath
     * @param relativeFilePath
     * @return
     * @throws IOException
     */
//    public boolean webCopyDataFile(String webSubDirectoryPath, String relativeFilePath) throws IOException;
    
    /**
     * @param fileUrl
     * @param request
     * @param response
     * @return
     */
    public void requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);

    /**
     * Move file from repository temp to folder upload main
     * 
     * @param fileName
     * 			type String
     * @param folderName
     * 			type String
     * @return String
     * @author HaND
     */
    public String moveFileFromTempToFolderUploadMain(String fileName, String folderName) throws IOException;
    
    
    //Download File Word In Folder(excel-template)
    public void downloaFileWord(String fileName, HttpServletRequest request, HttpServletResponse response);
    
    public String getPathTemp();
    
    public String getPathMain();
}
