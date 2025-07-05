/*******************************************************************************
 * Class        ：JcaStyleDto
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：ngannh
 * Change log   ：2020/12/11：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaStyleDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class JcaStyleDto extends AbstractTracking{
    private Long id;
    private Long companyId;
    private String code;
    private String name;
    private String styleUrl;
    private String packageStyleUrl;
    private String imageUrl;
    private String packageImageUrl;
    private String packageLoginLogo;
    private String packageShortcutIcon;
    private String packageLogoLarge;
    private String packageLogoMini;
    private Long sort;

}
