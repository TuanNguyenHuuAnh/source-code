/*******************************************************************************
 * Class        ：CommonUtility
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.common.constant.CommonConstant;

/**
 * The Class CommonUtility.
 */
public class CommonUtility {

    /** configure file. */
    private static final String CONFIGURE_FILE = "configure-jcanary";

    /** logger. */
    private static final Logger logger = LoggerFactory.getLogger(CommonUtility.class);

    /**
     * Gets the property.
     *
     * @param key
     *            the key type String
     * @param propertyFile
     *            the property file type String
     * @return the property
     * @author tantm
     */
    public static String getProperty(String key, String propertyFile) {
        String result = CommonConstant.EMPTY;

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(propertyFile);
            result = bundle.getString(key);
        } catch (Exception e) {
            logger.error(" ＜ Utility.getConfigureProperty() error ＞ ", e);
        }

        return result;
    }

    /**
     * Gets the configure property.
     *
     * @param key
     *            the key type String
     * @return the configure property
     * @author tantm
     */
    public static String getConfigureProperty(String key) {
        return getProperty(key, CONFIGURE_FILE);
    }

}
