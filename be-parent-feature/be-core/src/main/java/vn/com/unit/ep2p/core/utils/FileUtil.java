/*******************************************************************************
 * Class        FileUtils
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

import vn.com.unit.common.exception.SystemException;

/**
 * FileUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class FileUtil {
    
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
    /**
     * Get fileName and fileExtension from fileName
     * 
     * @param fileName
     *            String
     * @return String[]
     * @author KhoaNA
     */
    public static String[] getFileNameAndFileExtension(String fileNameFull) {
        String[] result = null;
        
        if ( StringUtils.isNotEmpty(fileNameFull) ) {
            int midIndex = fileNameFull.lastIndexOf('.');
            if( midIndex != -1  ) {
                result = new String[2];
                
                midIndex = midIndex + 1;
                int endIndex = fileNameFull.length();

                // Get fileName
                result[0] = fileNameFull.substring(0, midIndex);
                // Get extension
                result[1] = fileNameFull.substring(midIndex, endIndex)
                                    .toLowerCase();
            } else {
                result = new String[2];
                
                // Get fileName
                result[0] = fileNameFull;
                // Get extension
                result[1] = null;
            }
        }
        
        return result;
    }
    
    /**
     * Get file extension
     * 
     * @param fileName
     *            String
     * @return String
     * @author KhoaNA
     */
    public static String getFileExtension(String fileNameFull) {
        String result = null;
        
        if ( StringUtils.isNotEmpty(fileNameFull) ) {
            int beginIndex = fileNameFull.lastIndexOf('.');
            
            if( beginIndex != -1 ) {
                beginIndex = beginIndex + 1;
                int endIndex = fileNameFull.length();

                result = fileNameFull.substring(beginIndex, endIndex)
                                    .toLowerCase();
            }
        }
        
        return result;
    }
    
    /**
     * Move file with new file name from folderSource to folderTarget
     * 
     * @param fileName
     *            String
     * @param folderSource
     *            String
     * @param newfileName
     *            String
     * @param folderTarget
     *            String
     * @return
     * @author KhoaNA
     * @throws IOException 
     */
    public static void moveFile(String fileName, String folderSource,
            String newFileName, String folderTarget) throws IOException {
            File source = new File(folderSource + fileName);
            File target = new File(folderTarget + newFileName);
            FileUtil.setPermission(target);
            try {
                FileUtils.copyFile(source, target);
                if(!source.delete()){
                    logger.error("source not delete");
                }
            } catch (IOException e) {
                StringBuilder msgError = new StringBuilder("IOException:");
                msgError.append("Can not move file ");
                msgError.append(fileName);
                msgError.append(" from folderSource ");
                msgError.append(folderSource);
                msgError.append(" to folderTarget ");
                msgError.append(folderTarget);
                msgError.append(":");
                throw new SystemException(msgError.toString()+e.toString());
            }
    }
    
    /**
     * Move file from folderSource to folderTarget
     * 
     * @param fileName
     *            String
     * @param folderSource
     *            String
     * @param folderTarget
     *            String
     * @return
     * @author KhoaNA
     * @throws IOException 
     */
    public static void moveFile(String fileName, String folderSource,
            String folderTarget) throws IOException {
        moveFile(fileName, folderSource, fileName, folderTarget);
    }
    
    /**
     * Get contentType file
     * 
     * @param fileNameFull
     *          type String
     * @return String
     * @author KhoaNA
     */
    public static String getContentType(String fileNameFull) {
        String contentType = "application/octet-stream";
        
        String extensionFile = getFileExtension(fileNameFull);
        
        if( StringUtils.isNotEmpty(extensionFile) ) {
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
    
    public static void setPermission(File file) throws IOException{
    	if(!file.setReadable(true, false)){
    		logger.error("file not set Readable");
    	}
    	if(!file.setWritable(true, false)){
    		logger.error("file not set Writable");
    	}
    	if(!file.setExecutable(true, false)){
    		logger.error("file not set Executable");
    	}
    }
    
    /**
     * Delete file by filePath
     * 
     * @param filePath
     *         type String
     * @return
     * @author KhoaNA
     */
    public static void deleteFile(String filePath) {
        File source = new File(filePath);
        source.deleteOnExit();
    }
    
    /**
     * Check file existed
     * 
     * @param filePath
     *         type String
     * @return boolean
     * @author KhoaNA
     */
    public static boolean isFileExisted(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
    
    /**
     * Create directory if path not exists
     *
     * @param path
     *             type String
     * @author hand
     * @throws IOException 
     */
    public static void createDirectoryNotExists(String path) throws IOException {
        // file path
        File file = new File(FilenameUtils.separatorsToSystem(path));
        // not exists directory
        if (!file.exists()) {
            // create directory
            setPermission(file);
            file.mkdirs();
        }
    }
    
    /**
     * getFileAutoRotate
     * @param subFolder
     * @param pathFile
     * @return
     * @author TuyenNX
     */
    public static String getFileAutoRotate(String subFolder, String pathFile) {
        String result = "";

        String path = pathFile;
        if (StringUtils.isNotBlank(subFolder)) {
            path = subFolder + "/" + pathFile;
        }

        try {
            if (StringUtils.isNotBlank(path)) {
                File file = new File(path);
                if (file.exists()) {
                    result = pathFile;
                    //create rotate file
                    String pathFileOut = pathFile.replace(".jpeg", "") + "_rotate" + ".jpeg";
                    String pathOut = subFolder + "/" + pathFileOut;
                    BufferedImage originalImage = ImageIO.read(file);
                    originalImage.getHeight();
                    Metadata metadata = ImageMetadataReader.readMetadata(file);
                    Collection<ExifIFD0Directory> exifIFD0DirectoryList = metadata.getDirectoriesOfType(ExifIFD0Directory.class);
                    Collection<JpegDirectory> jpegDirectoryList = metadata.getDirectoriesOfType(JpegDirectory.class);
                    AffineTransformOp affineTransformOp = null;
                    
                    int orientation = 1;
                    
                    try {
	                    ExifIFD0Directory exifIFD0Directory = (ExifIFD0Directory) exifIFD0DirectoryList.toArray()[0];
	                    JpegDirectory jpegDirectory = (JpegDirectory) jpegDirectoryList.toArray()[0];
	                    try {
	                        orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
	                    } catch (Exception ex) {
	                        ex.printStackTrace();
	                    }
	
	                    int width = jpegDirectory.getImageWidth();
	                    int height = jpegDirectory.getImageHeight();
	
	                    AffineTransform affineTransform = new AffineTransform();
	
	                    switch (orientation) {
	                    case 1:
	                        break;
	                    case 2: // Flip X
	                        affineTransform.scale(-1.0, 1.0);
	                        affineTransform.translate(-width, 0);
	                        break;
	                    case 3: // PI rotation
	                        affineTransform.translate(width, height);
	                        affineTransform.rotate(Math.PI);
	                        break;
	                    case 4: // Flip Y
	                        affineTransform.scale(1.0, -1.0);
	                        affineTransform.translate(0, -height);
	                        break;
	                    case 5: // - PI/2 and Flip X
	                        affineTransform.rotate(-Math.PI / 2);
	                        affineTransform.scale(-1.0, 1.0);
	                        break;
	                    case 6: // -PI/2 and -width
	                        affineTransform.translate(height, 0);
	                        affineTransform.rotate(Math.PI / 2);
	                        break;
	                    case 7: // PI/2 and Flip
	                        affineTransform.scale(-1.0, 1.0);
	                        affineTransform.translate(-height, 0);
	                        affineTransform.translate(0, width);
	                        affineTransform.rotate(3 * Math.PI / 2);
	                        break;
	                    case 8: // PI / 2
	                        affineTransform.translate(0, width);
	                        affineTransform.rotate(3 * Math.PI / 2);
	                        break;
	                    default:
	                        break;
	                    }      
	                    affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
                    }
                    catch (Exception e) {
                    	
                    }
                    
                    if (orientation == 0 || orientation == 1) {
                        result = pathFile;
                    } else {
                        BufferedImage destinationImage = new BufferedImage(originalImage.getHeight(),
                                originalImage.getWidth(), originalImage.getType());
                        if (affineTransformOp != null) {
                            destinationImage = affineTransformOp.filter(originalImage, destinationImage);
                        }
                        ImageIO.write(destinationImage, "jpeg", new File(pathOut));

                        result = pathFileOut;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("##### getFileAutoRotate #####", e);
        }

        return result;
    }
    
    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").replace('đ','d').replace('Đ','D');
    }
}
