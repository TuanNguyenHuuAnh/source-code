/*******************************************************************************
 * Class        ：MenuInfoReq
 * Created date ：2020/12/10
 * Lasted date  ：2020/12/10
 * Author       ：SonND
 * Change log   ：2020/12/10：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * MenuInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class MenuInfoReq {
	@ApiModelProperty(notes = "Url on system", example = "/api/v1/admin/menu", required = true, position = 0)
    private String url;
	@ApiModelProperty(notes = "Position of menu", example = "1", required = true, position = 0)
    private Integer menuOrder;
	@ApiModelProperty(notes = "Icon of menu", example = "fa fa-user-circle-o", required = true, position = 0)
    private String icon;
	@ApiModelProperty(notes = "Id parent of menu", example = "1", required = true, position = 0)
    private Long parentId;
	@ApiModelProperty(notes = "Show or hide menu", example = "1", position = 0)
    private boolean actived;
	@ApiModelProperty(notes = "Id role of menu", example = "1", position = 0)
	private Long itemId;
	@ApiModelProperty(notes = "Id of company", example = "1", position = 0)
	private Long companyId;
	@ApiModelProperty(notes = "Header of menu", example = "1", position = 0)
	private boolean headerFlag;
	
	@ApiModelProperty(notes = "List information language of menu",  required = true,  position = 0)
	private List<MenuLangInfoReq> menuInfoLang;
}
