/*******************************************************************************
 * Class        AccountListDto
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/2101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaAccountDto;

/**
 * AccountListDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@Setter
public class AccountListDto extends JcaAccountDto {

	private Long id;
	
    private String statusCode;
    
    private String url;
    
    private String companyName;
    
    private String orgName;
    
    private String positionName;
    
    
}
