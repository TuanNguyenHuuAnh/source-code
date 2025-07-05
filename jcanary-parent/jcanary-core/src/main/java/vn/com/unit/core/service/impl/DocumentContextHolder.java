/*******************************************************************************
 * Class        ：DocumentContextHolder
 * Created date ：2020/11/19
 * Lasted date  ：2020/11/19
 * Author       ：tantm
 * Change log   ：2020/11/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * DocumentContextHolder
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DocumentContextHolder {

    private Map<String, Object> informations = new HashMap<>();

    /**
     * Get Document Informations
     * 
     * @return Informations type Map
     * @author tantm
     */
    public Map<String, Object> getInformations() {
        return informations;
    }

    /**
     * Set Informations for Document
     * 
     * @param informations
     *            Informations of Document
     * @author tantm
     */
    public void setInformations(Map<String, Object> informations) {
        this.informations = informations;
    }

}
