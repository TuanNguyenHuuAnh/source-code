/*******************************************************************************
 * Class        ：CompanyUpdateReq
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：ngannh
 * Change log   ：2020/12/07：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.CompanyReq;

/**
 * CompanyUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
@Getter
@Setter
public class CompanyUpdateReq extends CompanyReq{
    
    @ApiModelProperty(notes = "Id of company on system", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "Desciprtion code of company on system", example = "Unit Corporation is a diversified energy company engaged through ...", required = false, position = 0)
    private String description;
    
    @ApiModelProperty(notes = "Package background login of company on system", example = "URL of logo", required = false, position = 0)
    private String packageLoginBackground;

    @ApiModelProperty(notes = "Package shortcut icon of company on system", example = "", required = false, position = 0)
    private String packageShortcutIcon;
    
    @ApiModelProperty(notes = "Package logo large of company on system", example = "", required = false, position = 0)
    private String packageLogoLarge;
    
    @ApiModelProperty(notes = "Package logo mini of company on system", example = "", required = false, position = 0)
    private String packageLogoMini;

    @ApiModelProperty(notes = "Login logo of company on system", example = "", required = false, position = 0)
    private String loginBackground;

    @ApiModelProperty(notes = "Login logo repository id of company on system", example = "1", required = false,  position = 0)
    private Long loginBackgroundRepoId;
    
    @ApiModelProperty(notes = "Shortcut icon of company on system", example = "", required = false, position = 0)
    private String shortcutIcon;
    
    @ApiModelProperty(notes = "Shortcut icon repository of company on system", example = "1", required = false, position = 0)
    private Long shortcutIconRepoId;
    
    @ApiModelProperty(notes = "Logo large of company on system", example = "", required = false, position = 0)
    private String logoLarge;
    
    @ApiModelProperty(notes = "Logo large repository id of company on system", example = "1", required = false, position = 0)
    private Long logoLargeRepoId;
    
    @ApiModelProperty(notes = "Logo mini of company on system", example = "", required = false, position = 0)
    private String logoMini;
    
    @ApiModelProperty(notes = "Logo mini repository id of company on system", example = "1", required = false, position = 0)
    private Long logoMiniRepoId;
    
    @ApiModelProperty(notes = "Style of company on system", example = "bg-unit", required = false, position = 0)
    private String style;
    
    @ApiModelProperty(notes = "Language of company on system", example = "EN", required = false, position = 0)
    private String language;
    
    @ApiModelProperty(notes = "Effected date of company on system", example = "20200121", required = false, position = 0)
    private String effectedDate;
    
    @ApiModelProperty(notes = "Expired date of company on system", example = "20200121", required = false, position = 0)
    private String expiredDate;
    
    @ApiModelProperty(notes = "Limit number users of company on system", example = "10", required = false, position = 0)
    private Long limitNumberUsers;
    
    @ApiModelProperty(notes = "Limit number transaction of company on system", example = "10", required = false, position = 0)
    private Long limitNumberTransaction;
    
    @ApiModelProperty(notes = "Active flag of company on system", example = "1", required = false, position = 0)
    private boolean activeFlag;
    
    @ApiModelProperty(notes = "Base 64 of file login background", required = false, position = 0)
    private String fileLoginBackground;
    
    @ApiModelProperty(notes = "Base 64 of file shortcut icon", required = false, position = 0)
    private String fileShortcutIcon;
    
    @ApiModelProperty(notes = "Base 64 of file logo large", required = false, position = 0)
    private String fileLogoLarge;
    
    @ApiModelProperty(notes = "Base 64 of file logo mini", required = false, position = 0)
    private String fileLogoMini;
    
    @ApiModelProperty(notes = "Name of file login background", example = "background.png", required = false, position = 0)
    private String fileNameLoginBackground;
    
    @ApiModelProperty(notes = "Name of file shortcut icon", example = "shortcut.png", required = false, position = 0)
    private String fileNameShortcutIcon;
    
    @ApiModelProperty(notes = "Name of file Logo large", example = "logo-large.png", required = false, position = 0)
    private String fileNameLogoLarge;
    
    @ApiModelProperty(notes = "Name of file logo mini", example = "logo-mini.png", required = false, position = 0)
    private String fileNameLogoMini;

}
