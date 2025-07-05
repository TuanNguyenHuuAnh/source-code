/*******************************************************************************
 * Class        ：NewsSearchDto
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：TaiTM
 * Change log   ：2017/02/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.emulate.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

/**
 * NewsSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class EmulateSearchDto extends CmsCommonSearchFilterDto {
    private static final long serialVersionUID = -6687567796397819851L;

    private String memoNo;
    
    private String contestName;
    
    private String applicableObject;
    
    private String agentCode;
    
    private String orgCode;
    
    private String agentGroup;
    
    private String contestPhysicalFile;
    
    private String contestPhysicalImg;
    
    private String description;

    private String startDate;
    
    private String endDate;
    
    private String effectiveDate;
    
    private String expiredDate;
    
    private String contestType;

    private String isHot;
    
    private String type;
    private String enabled;
    
    private String createBy;

    private String createDate;

    private String updateBy;  
    
    private String updateDate;
        
    private String territory;

    private String area;

    private String region;

    private String office;

    private String position;

    private String reportingtoCode;

}
