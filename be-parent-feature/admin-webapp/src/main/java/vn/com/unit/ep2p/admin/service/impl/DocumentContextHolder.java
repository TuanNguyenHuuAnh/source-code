package vn.com.unit.ep2p.admin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component("appDocumentContextHolder")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DocumentContextHolder {

    private Map<String, Object> informations = new HashMap<>();

    /**
     * @return the informations
     */
    public Map<String, Object> getInformations() {
        return informations;
    }

    /**
     * @param informations the informations to set
     */
    public void setInformations(Map<String, Object> informations) {
        this.informations = informations;
    }

}
