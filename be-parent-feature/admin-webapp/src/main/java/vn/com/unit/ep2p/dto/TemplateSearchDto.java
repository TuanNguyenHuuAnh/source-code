/*******************************************************************************
 * Class        EmailSearchDto
 * Created date 2018/08/22
 * Lasted date  2018/08/22
 * Author       phatvt
 * Change log   2018/08/2201-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.ConditionSearchCommonDto;

/**
 * EmailSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Getter
@Setter
public class TemplateSearchDto extends ConditionSearchCommonDto {

    /** fieldSearch */
    private String fieldSearch;
    /** fieldValues */
    private String fieldValues;
    
    private String createdBy;
    
    private String templateName;
    
    private Long companyId;
    
    private String companyName;
    
    private boolean companyAdmin;
    
    private List<Long> companyIdList;
    
    private String searchKeyIds;
}
