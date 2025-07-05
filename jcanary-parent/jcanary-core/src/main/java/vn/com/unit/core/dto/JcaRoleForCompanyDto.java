/*******************************************************************************
 * Class        ：JcaRoleForCompanyDto
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaRoleForCompanyDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class JcaRoleForCompanyDto extends AbstractTracking{

//    private Long roleForCompanyId;

    private Long companyId;

    private Long roleId;

    private Long orgId;

    private Boolean isAdmin;
    
    //
    private Long id;
    
    private String companyName;
    
    private boolean flgChecked;
    
    private boolean active;
    
    private int defFlag;
    
    private String orgName;
    
    private Boolean isDisplayEmail;
}
