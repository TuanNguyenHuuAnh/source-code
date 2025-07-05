/*******************************************************************************
 * Class        ：EfoFormDto
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：taitt
 * Change log   ：2020/12/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * EfoFormDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class EfoFormDto extends AbstractTracking{
    
    private Long formId;
    private Long companyId;
    private Long categoryId;
    private Long businessId;
    private String  businessCode;
    private String businessName;
    private String formName;
    private String description;
    private String ozFilePath;
    private String iconFilePath;
    private Long iconRepoId;
    private Long displayOrder;
    private String deviceType;
    private String actived;
    private String formType;
    private String ozAppendFilePath;
    private String categoryName;
    private String userName;
    
    // only view
    private String companyName;
}
