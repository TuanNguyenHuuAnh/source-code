/*******************************************************************************
 * Class        ：BannerEditDto
 * Created date ：2017/02/20
 * Lasted date  ：2017/02/20
 * Author       ：hand
 * Change log   ：2017/02/20：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.banner.dto.setting;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.entity.HomePageSetting;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.Select2Dto;

@Getter
@Setter
public class BannerSettingEditDto extends CmsCommonEditDto {
	@Valid
	private List<BannerLanguageDto> bannerLanguageList;
	
	private List<HomePageSetting> bannerTopList;

	private List<HomePageSetting> bannerTopMobileList;
	
	private String bannerPage;

	private String bannerPageName;

	private boolean autoPlay;
	
	private boolean autoMute;
	
	private boolean autoReplay;

	private String bannerEff;

	private String mBannerTopId;

	private String mBannerTopMobileId;

	private Integer statusSearch;

	private String codeSearch;

	private String nameSearch;

	private String bannerType;

	private String bannerTypeName;

	private String bannerDevice;

	private String bannerDeviceName;
	
	private String slideTime;

	private Date startDate;
	
	private Date endDate;
	
	private int isYoutube;
	
	private String bannerYoutubeVideo;
	
	private Date date;

	private String[] listMBannerTopId;

	private String[] listMBannerMobileTopId;
	
	private String channel;
	
	private List<String> channelList;

}
