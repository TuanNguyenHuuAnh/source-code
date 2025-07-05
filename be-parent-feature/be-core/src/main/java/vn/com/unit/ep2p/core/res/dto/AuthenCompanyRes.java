/*******************************************************************************
 * Class        ：AuthenCompanyRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthenCompanyRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AuthenCompanyRes {

    private Long companyId;
    private String companyName;
    private String systemCode;
    private String logoPath;
    private Long logoRepoId;
    
}
