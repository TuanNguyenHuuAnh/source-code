/*******************************************************************************
 * Class        ：AuthorityAccountDto
 * Created date ：2021/03/09
 * Lasted date  ：2021/03/09
 * Author       ：Tan Tai
 * Change log   ：2021/03/09：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthorityAccountDto
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */
@Getter
@Setter
public class AuthorityAccountDto {

    private Long accountId;
    private String accountName;
    private String accountFullName;
    private String accountCode;
    private String accountEmail;
    private String accountAvatar;
    private Long accountAvatarRepoId; 
}
