/*******************************************************************************
 * Class        :CompanyDto
 * Created date :2019/05/07
 * Lasted date  :2019/05/07
 * Author       :HungHT
 * Change log   :2019/05/07:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

/**
 * CompanyDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@Setter
public class CompanyDto {

	private Long id;
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
    private boolean actived;
	private MultipartFile fileLoginBackground;
	private MultipartFile fileShortcutIcon;
    private MultipartFile fileLogoLarge;
    private MultipartFile fileLogoMini;
    private String createdId;
	private Date createdDate;
	private String updatedId;
	private Date updatedDate;
	private Long deletedId;
	private Date deletedDate;
    private boolean screenConfig;

}