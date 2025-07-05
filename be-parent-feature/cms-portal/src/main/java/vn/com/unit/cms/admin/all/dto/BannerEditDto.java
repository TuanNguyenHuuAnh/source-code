/*******************************************************************************
 * Class        ：BannerEditDto
 * Created date ：2017/02/20
 * Lasted date  ：2017/02/20
 * Author       ：hand
 * Change log   ：2017/02/20：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.utils.CmsUtils;

@Getter
@Setter
public class BannerEditDto extends CmsCommonEditDto {

    @Setter(AccessLevel.NONE)
    private String code;

    public void setCode(String code) {
        this.code = CmsUtils.toUppercase(code);
    }

    private String name;

    @Valid
    private List<BannerLanguageDto> bannerLanguageList;

    private String url;

    private String bannerType;

    private String bannerTypeName;

    private String bannerDevice;

    private String bannerDeviceName;

    private String requestToken;

    private String buttonAction;

    private String createBy;

    private String languageCode;

    private String comment;

    private String referenceType;

    private Long referenceId;

    private Date updateDate;

    private String nameSearch;

    private Integer statusSearch;

    private String codeSearch;

    private boolean enabled;
    
    private int isYoutube;
	
	private String bannerYoutubeVideo;
	
	private String channel;
	
	private List<String> channelList;
}
