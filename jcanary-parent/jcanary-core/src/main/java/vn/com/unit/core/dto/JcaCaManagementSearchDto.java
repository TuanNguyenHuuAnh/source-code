/*******************************************************************************
 * Class        ：JcaCaManagementSearchDto
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;

/**
 * JcaCaManagementSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaCaManagementSearchDto extends AbstractCompanyDto{
    /** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;

    private String caName;
    private String accountName;
    private String caSlot;
    
}
