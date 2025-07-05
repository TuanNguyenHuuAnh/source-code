/*******************************************************************************
* Class        JpmBusinessDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmBusinessDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmBusinessDto {

    private Long businessId;

    private String businessCode;

    private String businessName;

    private String description;

    private boolean actived;

    private Long companyId;

    private Integer processType;

    private boolean authority;

    private String companyName;
}