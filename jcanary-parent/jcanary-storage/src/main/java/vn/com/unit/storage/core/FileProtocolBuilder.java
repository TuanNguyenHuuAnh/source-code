/*******************************************************************************
 * Class        ：FileProtocolBuilder
 * Created date ：2020/07/22
 * Lasted date  ：2020/07/22
 * Author       ：HungHT
 * Change log   ：2020/07/22：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.storage.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import vn.com.unit.storage.constant.StorageConstant;
import vn.com.unit.storage.core.FileStorageInstance;
import vn.com.unit.storage.enumdef.FileProtocol;

/**
 * FileProtocolBuilder
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Component
public class FileProtocolBuilder {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * Get FileStorage by repository type getFileStorage
     * 
     * @param fileProtocol
     *            FileStorage type
     * @return FileStorage
     * @author tantm
     */
    public FileStorageInstance getFileStorageInstance(Long fileProtocol) {
        FileProtocol fileProtocolObj = FileProtocol.resolveByValue(fileProtocol);
        FileStorageInstance fileStorageInstance = null;
        if (null != fileProtocolObj) {
            switch (fileProtocolObj) {
            case WEBDAV:
                fileStorageInstance = (FileStorageInstance) applicationContext.getBean(StorageConstant.BEAN_WEB_DAV_FILE_STORAGE);
                break;
            case ECM_ALFRESCO:
                fileStorageInstance = (FileStorageInstance) applicationContext.getBean(StorageConstant.BEAN_ECM_ALFRESCO_FILE_STORAGE);
                break;
            case FILENET:
                fileStorageInstance = (FileStorageInstance) applicationContext.getBean(StorageConstant.BEAN_FILENET_FILE_STORAGE);
                break;
            default:
                fileStorageInstance = (FileStorageInstance) applicationContext.getBean(StorageConstant.BEAN_LOCAL_FILE_STORAGE);
                break;
            }
        }

        return fileStorageInstance;
    }
}
