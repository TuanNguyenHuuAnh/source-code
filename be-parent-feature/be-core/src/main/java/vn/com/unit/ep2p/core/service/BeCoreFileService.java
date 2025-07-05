/**
 * @author TaiTM
 */
package vn.com.unit.ep2p.core.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author TaiTM
 *
 */
public interface BeCoreFileService {
    /**
     * @param file
     * @param destFolderName
     * @param destSubFolderName
     * @return
     * @throws IOException
     */
    public String uploadTemp(MultipartFile file, String destFolderName, String destSubFolderName) throws IOException;
}
