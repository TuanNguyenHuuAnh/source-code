/*******************************************************************************
 * Class        ：JcaCompanyDto
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：NganNH
 * Change log   ：2020/12/07：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaCompanyDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
@Getter
@Setter
public class JcaCompanyDto extends AbstractTracking{
   
    private Long companyId;

    private String name;

    private String systemName;
    
    private String systemCode;

    private String description;

    private String packageLoginBackground;

    private String packageShortcutIcon;

    private String packageLogoLarge;

    private String packageLogoMini;

    private String loginBackground;

    private Long loginBackgroundRepoId;

    private String shortcutIcon;

    private Long shortcutIconRepoId;

    private String logoLarge;
 
    private Long logoLargeRepoId;

    private String logoMini;

    private Long logoMiniRepoId;

    private String style;

    private String language;

    private Date effectedDate;

    private Date expiredDate;

    private Long limitNumberUsers;

    private Long limitNumberTransaction;

    private Boolean actived;
    
    private String fileLoginBackground;
    
    private String fileShortcutIcon;
    
    private String fileLogoLarge;
    
    private String fileLogoMini;
    
    private String fileNameLoginBackground;
    
    private String fileNameShortcutIcon;
    
    private String fileNameLogoLarge;
    
    private String fileNameLogoMini;

}
