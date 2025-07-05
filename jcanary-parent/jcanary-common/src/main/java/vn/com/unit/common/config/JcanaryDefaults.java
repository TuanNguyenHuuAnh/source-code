/*******************************************************************************
 * Class        ：JcanaryDefaults
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：KhoaNA
 * Change log   ：2020/12/03：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.config;


/**
 * JcanaryDefaults
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface JcanaryDefaults {

    interface Security {
        
        interface Authentication {

            interface Jwt {

                String secret = null;
                String base64Secret = null;
                long tokenValidityInSeconds = 1800; // 30 minutes
                long tokenValidityInSecondsForRememberMe = 86400; // 1 days
            }
        }
    }
}
