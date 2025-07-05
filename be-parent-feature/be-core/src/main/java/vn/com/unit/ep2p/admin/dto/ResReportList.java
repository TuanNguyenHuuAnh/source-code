/*******************************************************************************
 * Class        ：ResReportList
 * Created date ：2019/04/17
 * Lasted date  ：2019/04/17
 * Author       ：HungHT
 * Change log   ：2019/04/17：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import vn.com.unit.core.res.ResAPI;
import vn.com.unit.ep2p.dto.PPLRegisterSvcDto;



/**
 * ResReportList
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class ResReportList extends ResAPI {
    
    private List<PPLRegisterSvcDto> resultObj;
    
    /**
     * Get resultObj
     * @return List<RegisterSvcDto>
     * @author HungHT
     */
    public List<PPLRegisterSvcDto> getResultObj() {
        return resultObj;
    }

    /**
     * Set resultObj
     * @param   resultObj
     *          type List<RegisterSvcDto>
     * @return
     * @author  HungHT
     */
    public void setResultObj(List<PPLRegisterSvcDto> resultObj) {
        this.resultObj = resultObj;
    }
}
