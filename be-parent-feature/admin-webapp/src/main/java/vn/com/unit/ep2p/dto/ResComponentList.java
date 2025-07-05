/*******************************************************************************
 * Class        ：ResComponentList
 * Created date ：2019/04/17
 * Lasted date  ：2019/04/17
 * Author       ：HungHT
 * Change log   ：2019/04/17：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

import vn.com.unit.core.res.ResAPI;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;

/**
 * ResComponentList
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class ResComponentList extends ResAPI {
    
    private List<EfoComponent> resultObj;

    /**
     * Get resultObj
     * @return List<Component>
     * @author HungHT
     */
    public List<EfoComponent> getResultObj() {
        return resultObj;
    }

    /**
     * Set resultObj
     * @param   resultObj
     *          type List<Component>
     * @return
     * @author  HungHT
     */
    public void setResultObj(List<EfoComponent> resultObj) {
        this.resultObj = resultObj;
    }
}
