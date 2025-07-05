/*******************************************************************************
 * Class        ：MenuDto
 * Created date ：2024/06/12
 * Lasted date  ：2024/06/12
 * Author       ：tinhnt
 * Change log   ：2024/06/12：01-00 tinhnt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthorityDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tinhnt
 */
@Getter
@Setter
public class MenuDto {

    // Dto for entity
    private Long menuId;
    private String menuCode;
    private String menuName;
    private String url;
    private Long parentId;
    private String icon;
    private String functionCode;
    private String component;
    private boolean isPrivate;
    private boolean isExact;
    private int favorite;
}
