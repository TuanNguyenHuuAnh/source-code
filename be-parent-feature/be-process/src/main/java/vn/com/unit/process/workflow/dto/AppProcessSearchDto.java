/*******************************************************************************
 * Class        AppProcessSearchDto
 * Created date 2019/04/12
 * Lasted date  2019/04/12
 * Author       KhoaNA
 * Change log   2019/04/12 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;
import vn.com.unit.core.dto.ConditionSearchCommonDto;


/**
 * AppProcessSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@Setter
public class AppProcessSearchDto extends ConditionSearchCommonDto {
	
	/** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;

    private String code;
    private String name;
    
    private Long companyId;
    private String companyName;
    private boolean companyAdmin;
    private List<Long> companyIdList;

    
}
