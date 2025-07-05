/*******************************************************************************
 * Class        :CalendarTypeSearchDto
 * Created date :2019/06/25
 * Lasted date  :2019/06/25
 * Author       :HungHT
 * Change log   :2019/06/25:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;
import vn.com.unit.core.dto.ConditionSearchCommonDto;

/**
 * CalendarTypeSearchDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@Setter
public class CalendarTypeSearchDto extends ConditionSearchCommonDto {
	private String code;
	private String name;
	private String description;
	
	/** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private String fieldValues;

	private List<String> orderColumn;
	
	private Long companyId;
	
    private String companyName;
    
    private boolean companyAdmin;
    
    private List<Long> companyIdList;
	
    private String searchKeyIds;
    
    private String sName;
    
    private String sWorkingHours;
    
    private String sDescription;
    
}