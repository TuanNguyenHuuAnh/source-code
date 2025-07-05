/*******************************************************************************
 * Class        SessionCountConfig
 * Created date 2018/08/09
 * Lasted date  2018/08/09
 * Author       phatvt
 * Change log   2018/08/0901-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * SessionCountConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public class SessionConfig implements HttpSessionListener {

    private static int totalActiveSessions = 0 ;
    
    public static int getTotalActiveSession(){
      return totalActiveSessions;
    }
    
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        totalActiveSessions++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        totalActiveSessions--;
    }

}
