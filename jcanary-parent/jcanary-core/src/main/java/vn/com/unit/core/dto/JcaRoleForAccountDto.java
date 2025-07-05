/*******************************************************************************
 * Class        ：JcaRoleForAccountDto
 * Created date ：2021/01/22
 * Lasted date  ：2021/01/22
 * Author       ：SonND
 * Change log   ：2021/01/22：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * JcaRoleForAccountDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@Getter
@Setter
public class JcaRoleForAccountDto {
    private Long roleForAccountId;
    private Long userId;
    private Long roleId;
    private Date startDate;
    private Date endDate;
    
    private String roleName;
}
