/*******************************************************************************
 * Class        ：ResMapObject
 * Created date ：2019/04/17
 * Lasted date  ：2019/04/17
 * Author       ：HungHT
 * Change log   ：2019/04/17：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Map;

import vn.com.unit.core.res.ResAPI;


/**
 * ResMapObject
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class ResMapObject extends ResAPI {

    private Map<String, Object> resultObj;

    
    /**
     * Get resultObj
     * @return Map<String,Object>
     * @author HungHT
     */
    public Map<String, Object> getResultObj() {
        return resultObj;
    }
    
    /**
     * Set resultObj
     * @param   resultObj
     *          type Map<String,Object>
     * @return
     * @author  HungHT
     */
    public void setResultObj(Map<String, Object> resultObj) {
        this.resultObj = resultObj;
    }
    
}
