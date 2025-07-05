/*******************************************************************************
 * Class        ：CompanySearchReq
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：NganNH
 * Change log   ：2020/12/07：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * CompanySearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
@Getter
@Setter
public class CompanySearchReq{
    @ApiModelProperty(notes = "Search by ", example = "superadmin", required = true, position = 0)
    private String keySearch;
     
    private String name;
    
    private String description;
    
    private String systemCode;
    
    private String systemName;
}
