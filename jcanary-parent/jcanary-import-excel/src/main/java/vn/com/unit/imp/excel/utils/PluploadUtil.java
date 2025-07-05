/*******************************************************************************
 * Class        ：PluploadUtil
 * Created date ：2017/08/07
 * Lasted date  ：2017/08/07
 * Author       ：phunghn
 * Change log   ：2017/08/07：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import vn.com.unit.imp.excel.constant.Message;
import vn.com.unit.imp.excel.dto.PluploadDto;

/**
 * PluploadUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 * @url: http://blog.csdn.net/t894690230/article/details/37956277
 */
public class PluploadUtil {
	
	static final Logger logger = LoggerFactory.getLogger(PluploadUtil.class);

	private static final int BUF_SIZE = 2 * 1024;  
    
    public static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";  
      
    public static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";  
      
   
    public static void upload(PluploadDto plupload, File dir, String filename) throws IllegalStateException, IOException {  
        
    	startUpload(plupload, dir, filename);  
    }  
      
   
    public static void startUpload(PluploadDto plupload, File dir, String filename) throws IllegalStateException, IOException {  
        int chunks = plupload.getChunks();  
        int chunk = plupload.getChunk(); 
          
          
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();   
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();  
          
        if(map != null) {  
            if (!dir.exists()) dir.mkdirs();  
            Iterator<String> iter = map.keySet().iterator();  
            while(iter.hasNext()) {  
                String str = (String) iter.next();  
                List<MultipartFile> fileList =  map.get(str);  
                for(MultipartFile multipartFile : fileList) {  
                	
                    plupload.setMultipartFile(multipartFile);  
                      
                    File targetFile = new File(dir.getPath()+ "/" + filename);  
                      
                    
                    if (chunks > 1) {  
                      
                        File tempFile = new File(dir.getPath()+ "/" + multipartFile.getName());  
                      
                        saveUploadFile(multipartFile.getInputStream(), tempFile, chunk == 0 ? false : true);  
                          
                        
						if (chunks - chunk == 1) {
							if (tempFile.renameTo(targetFile)) {
								logger.error(Message.ERROR, "rename fail");
							}
						}
                          
                    } else {  
                          
                        multipartFile.transferTo(targetFile);  
                    }  
                }  
            }  
        }  
          
    }  
      
   
    private static void saveUploadFile(InputStream input, File targetFile, boolean append) throws IOException {  
        OutputStream out = null;  
        try {  
            if (targetFile.exists() && append) {  
                out = new BufferedOutputStream(new FileOutputStream(targetFile, true), BUF_SIZE);  
            } else {  
                out = new BufferedOutputStream(new FileOutputStream(targetFile), BUF_SIZE);  
            }  
              
            byte[] buffer = new byte[BUF_SIZE];  
            int len = 0;  
            
            while ((len = input.read(buffer)) > 0) {  
                out.write(buffer, 0, len);  
            }  
        } catch (IOException e) {  
            throw e;  
        } finally {  
              
            if (null != input) {  
                try {  
                    input.close();  
                } catch (IOException e) {  
                    logger.error(Message.ERROR, e);  
                }  
            }  
            if (null != out) {  
                try {  
                    out.close();  
                } catch (IOException e) {  
                    logger.error(Message.ERROR, e);  
                }  
            }  
        }  
    }  
      
    public static boolean isUploadFinish(PluploadDto plupload) {  
        return (plupload.getChunks() - plupload.getChunk() == 1);  
    }  

    public static boolean checkOLEObject(InputStream inputStream) throws InvalidFormatException, IOException {
        OPCPackage opcPackage = OPCPackage.open(inputStream);
        ArrayList<PackagePart> parts = opcPackage.getParts();
        for (PackagePart part : parts) {
            if (part.getContentType().equals("application/vnd.openxmlformats-officedocument.oleObject")) {
                return true;
            }
        }
        return false;
    }
}
