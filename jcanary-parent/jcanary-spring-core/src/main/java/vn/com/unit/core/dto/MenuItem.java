/*******************************************************************************
 * Class        ：MenuItem
 * Created date ：2024/06/12
 * Lasted date  ：2024/06/12
 * Author       ：tinhnt
 * Change log   ：2024/06/12：01-00 tinhnt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * MenuItem
 * 
 * @version 01-00
 * @since 01-00
 * @author tinhnt
 */
@Getter
@Setter
public class MenuItem {

    // Dto for entity
    private Long id;
    private Long parentId;
    private String name;
    private String link;
    private boolean isLocalLink;
    private String itemId;
    private String icon;
    private String component;
    private boolean isPrivate;
    private boolean isExact;
    private boolean isFavorite;
}
