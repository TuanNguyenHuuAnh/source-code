/*******************************************************************************
 * Class        ：Utility
 * Created date ：2016/06/01
 * Lasted date  ：2016/06/01
 * Author       ：KhoaNA
 * Change log   ：2016/06/01：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.utils;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.ep2p.admin.constant.ConstantCore;

/**
 * Utility
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class Utility {

    /** configure file */
    private static final String CONFIGURE_FILE = "configure";
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(Utility.class);
    
    /**
     * Get property
     * 
     * @param key
     *          type String
     * @param propertyFile
     *          type String
     * @return String
     * @author KhoaNA
     */
    public static String getProperty(String key, String propertyFile) {
        String result = ConstantCore.EMPTY;
        
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(propertyFile);
            result = bundle.getString(key);
        } catch (Exception e) {
            logger.error(" ＜ Utility.getConfigureProperty() error ＞ ", e);
        }
        
        return result;
    }

    /**
     * Get property configure
     * 
     * @param key
     *          type String
     * @return String
     * @author KhoaNA
     */
    public static String getConfigureProperty(String key) {
        return getProperty(key, CONFIGURE_FILE);
    }
    
    /**
     * Calculate offset SQL
     *
     * @param page
     * @param sizeOfPage
     * @return int
     * @author KhoaNA
     */
    public static int calculateOffsetSQL(int page, int sizeOfPage) {
        if( page <= 0 ) {
            page = 1;
        }
        
        return (page - 1)*sizeOfPage; 
    }
}
