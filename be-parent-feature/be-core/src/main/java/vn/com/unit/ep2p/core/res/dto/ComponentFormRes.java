/*******************************************************************************
 * Class        ：ComponentFormRes
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：taitt
 * Change log   ：2020/12/31：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.api.dto.ResRESTApi;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;

/**
 * ComponentFormRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class ComponentFormRes extends ResRESTApi{    
    
    private List<EfoComponent> resultObj;
}
