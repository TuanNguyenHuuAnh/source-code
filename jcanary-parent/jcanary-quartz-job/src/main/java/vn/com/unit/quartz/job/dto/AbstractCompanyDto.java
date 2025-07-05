/*******************************************************************************
 * Class        ：AbstractCompanyDto
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * AbstractCompanyDto
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class AbstractCompanyDto {

    /** The company id. */
    private Long companyId;
    
    /** The company name. */
    private String companyName;
    
    /** The company admin. */
    private boolean companyAdmin;
    
    /** The company id list. */
    private List<Long> companyIdList;
    
}
