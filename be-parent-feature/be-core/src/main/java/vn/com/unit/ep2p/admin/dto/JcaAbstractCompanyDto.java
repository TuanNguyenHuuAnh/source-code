/*******************************************************************************
 * Class        ：JcaAbstractCompanyDto
 * Created date ：2021/02/23
 * Lasted date  ：2021/02/23
 * Author       ：khadm
 * Change log   ：2021/02/23：01-00 khadm create a new
******************************************************************************/

package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Get company id list.
 * </p>
 *
 * @author khadm
 * @return {@link List<Long>}
 */
@Getter

/**
 * <p>
 * Sets the company id list.
 * </p>
 *
 * @author khadm
 * @param companyIdList
 *            the new company id list
 */
@Setter
public class JcaAbstractCompanyDto {

    /** The company id. */
    private Long companyId;
    
    /** The company name. */
    private String companyName;
    
    /** The company admin. */
    private boolean companyAdmin;
    
    /** The company id list. */
    private List<Long> companyIdList;
    
    
}
