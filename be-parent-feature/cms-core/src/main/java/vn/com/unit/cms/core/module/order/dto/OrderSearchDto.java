/*******************************************************************************
 * Class        ：OrderSearchDto
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.order.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;


/**
 * ProductSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Getter
@Setter
public class OrderSearchDto extends CmsCommonSearchFilterDto{

	private Long orderId;
    private String agentCode;
    private String agentName;
    private String officeCode;
    private String officeName;
    private String phone;
    private String statusOrder;
    private Date cancelDate;
    private String cancelBy;
    private String invoiceCompanyName;
    private String invoiceTaxCode;
    private String invoiceCompanyAddress;
    private BigDecimal totalAmount;
    private String attachmentPhysicalImg;

}
