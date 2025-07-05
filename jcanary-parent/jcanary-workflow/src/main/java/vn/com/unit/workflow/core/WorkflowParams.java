/*******************************************************************************
 * Class        ：WorkflowParams
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：KhoaNA
 * Change log   ：2020/11/30：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.workflow.core;

import java.util.Map;

/**
 * WorkflowParams
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface WorkflowParams {

    /**
     * Get map params
     *
     * @return {@link Map<String, Object>}
     * @author KhoaNA
     */
    Map<String, Object> getParams();
    
    /**
     * Set map params
     *
     * @param params
     *          type Map<String, Object>
     * @author KhoaNA
     */
    void setParams(Map<String, Object> params);
    
    /**
     * Put param with key and value 
     * 
     * @param key
     *          type String
     * @param value
     *          type Object
     * @return {@link Object}
     * @author KhoaNA
     */
    Object putParam(String key, Object value);
    
    /**
     * Get param with key
     * 
     * @param key
     *          type String
     * @return {@link Object}
     * @author KhoaNA
     */
    Object getParam(String key);
}
