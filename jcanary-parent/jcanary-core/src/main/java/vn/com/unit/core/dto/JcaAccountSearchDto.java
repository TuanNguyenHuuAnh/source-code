/*******************************************************************************
 * Class        ：AccountSearchDto
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AccountSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaAccountSearchDto extends ConditionSearchCommonDto {
    private String username;
    private String fullName;
    private String email;
    private String phone;

    private Long companyId;
    private Boolean actived;
    private Boolean enabled;

    // add
    private Date birthday;
    private String status;
    private String departmentName;
    private String positionName;
    private String branchName;
    private String orgName;
    private Boolean locked;
    private Boolean unknownOrg;
    private Boolean unknownPosition;
    private Boolean emptyOrg;
    private Boolean emptyPosition;
    private String searchKeyIds;
    private List<String> listSearchKeyIds;
    private String searchValue;
    private Boolean sentBOD;
    private Boolean BOD;

}
