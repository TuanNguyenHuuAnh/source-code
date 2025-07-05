/*******************************************************************************
 * Class        ：DocumentAction
 * Created date ：2020/12/04
 * Lasted date  ：2020/12/04
 * Author       ：tantm
 * Change log   ：2020/12/04：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.workflow.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.service.DocumentActionEventService;
import vn.com.unit.core.service.DocumentService;

/**
 * DocumentAction
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class DocumentAction {

    /** DocumentService */
    @SuppressWarnings("rawtypes")
    DocumentService documentService;

    /** DocumentService */
    @SuppressWarnings("rawtypes")
    DocumentActionEventService documentActionEventService;

    /** Action of document */
    Map<String, Object> actions;

    public DocumentAction(DocumentService documentService) {
        this.documentService = documentService;
        this.actions = new HashMap<String, Object>();
    }

    public void addAction(String key, Object value) {
        actions.put(key, value);
    }

    public Object getAction(String key) {
        return actions.get(key);
    }
}
