/*******************************************************************************
 * Class        ：JcaRuleExceptionDto
 * Created date ：2021/03/11
 * Lasted date  ：2021/03/11
 * Author       ：tantm
 * Change log   ：2021/03/11：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * JcaRuleExceptionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class JcaRuleExceptionDto extends AbstractAuditTracking{

    private Long businessId;
    private Long orgId;
    private Long accountId;
    private Long ruleOrgType;
    
    private String userName;
    private String orgName;
}
