/*******************************************************************************
 * Class        ：MenuDto
 * Created date ：2024/06/12
 * Lasted date  ：2024/06/12
 * Author       ：tinhnt
 * Change log   ：2024/06/12：01-00 tinhnt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * MenuInfo
 * 
 * @version 01-00
 * @since 01-00
 * @author tinhnt
 */
@Getter
@Setter
public class MenuInfo {

    // Dto for entity
    private Long id;
    private String name;
    private String itemId;
    private String icon;
    private String link;
    private boolean isLocalLink;
    private boolean isFavorite;
    private List<MenuItem> subMenuList;
}
