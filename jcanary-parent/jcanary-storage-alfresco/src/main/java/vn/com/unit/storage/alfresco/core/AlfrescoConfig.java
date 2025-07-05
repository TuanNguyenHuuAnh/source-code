/*******************************************************************************
 * Class        AlfrescoConfig
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * AlfrescoConfig.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class AlfrescoConfig {

    /** The config. */
    private static Properties config;

    /**
     * Gets the config.
     *
     * @return the config
     * @author tantm
     */
    public static Properties getConfig() {
        if (config == null) {
            config = new Properties();
            try {
                config.load(new FileInputStream("config.properties"));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }
        return config;
    }

}
