package vn.com.unit.cms.core.module.banner.dto.setting;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;

@Getter
@Setter
public class BannerSettingSearchDto extends CmsCommonSearchResultFilterDto {

	private String bannerPage;
	
	private String bannerPhysicalUrl;

	private String bannerYoutubeVideo;

	private Integer bannerVideoType;

	private String bannerPageName;

	private String mBannerTopId;

	private String mBannerTopMobileId;

	private int bannerType;
	
	private int isYoutube;

	private String bannerTypeName;

	private Date startDate;

	private Date endDate;

	private String youtobeVideo;

	private String createBy;

	private Date createDate;

	private String updateBy;

	private Date updateDate;

	private List<String> bannerTop;

	private List<BannerLanguageDto> bannerTopList;

	private List<String> bannerTopMobile;

	private List<BannerLanguageDto> bannerTopMobileList;

}
