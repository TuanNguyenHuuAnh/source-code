package vn.com.unit.cms.core.module.product.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

@Getter
@Setter
public class ProductLanguageSearchDto extends CmsCommonSearchResultFilterDto {
    
    private String productCode;
    
    private String productName;
    
    private String productType;
    
    private BigDecimal unitPrice;
    
    private Date effectiveDate;
    
    private Date expiredDate;
    
    private String productImg;
    
    private String productPhysicalImg;

}
