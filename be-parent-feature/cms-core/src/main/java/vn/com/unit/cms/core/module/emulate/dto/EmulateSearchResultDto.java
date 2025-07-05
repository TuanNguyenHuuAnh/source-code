/*******************************************************************************
 * Class        ：NewsSearchDto
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：TaiTM
 * Change log   ：2017/02/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.emulate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

/**
 * NewsSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class EmulateSearchResultDto extends CmsCommonSearchResultFilterDto {
	
    private String memoNo;
    
    private String contestName;

    private Date startDate;
    
    private Date endDate;
    
    private Date effectiveDate;
    
    private Date expiredDate;
    
    private String contestType;

    private int isHot;
    
    private String applicableObject;
    
    private String territory;

    private String area;

    private String region;

    private String office;

    private String position;

    private String reportingtoCode;
    
    private String agentCode;
}
