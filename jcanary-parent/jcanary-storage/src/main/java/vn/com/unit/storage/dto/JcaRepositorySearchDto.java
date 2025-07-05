/*******************************************************************************
 * Class        ：JcaRepositorySearchDto
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;

/**
 * JcaRepositorySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class JcaRepositorySearchDto extends AbstractCompanyDto {

    private String code;
    private String name;
    private String physicalPath;
    private String subFolderPath;
    private Long companyId;
    private String langCode;
    
}
