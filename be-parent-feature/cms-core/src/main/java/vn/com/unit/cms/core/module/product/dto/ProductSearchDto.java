/*******************************************************************************
 * Class        ：ProductSearchDto
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.product.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

//import vn.com.unit.util.Util;

/**
 * ProductSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Getter
@Setter
public class ProductSearchDto extends CmsCommonSearchFilterDto{

    private String productCode;
    
    private String productName;
    
    private String productType;
    
    private BigDecimal unitPrice;
    
    private Date effectiveDate;
    
    private Date expiredDate;
    
    private String productImg;
    
    private String productPhysicalImg;

}
