package vn.com.unit.cms.core.module.banner.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

@Getter
@Setter
public class BannerLanguageSearchDto extends CmsCommonSearchResultFilterDto {

    private int bannerType;

    private String bannerTypeName;

    private String bannerDeviceName;
    
    private Long bannerDevice;

    private String bannerPhysicalUrl;

    private String bannerYoutubeVideo;

    private int bannerVideoType;

    private boolean isUsed;

    private Long numberBanner;

    private Long numberProduct;

    private Long numberProductType;

    private Long numberProductCategory;

    private Long canBeDeletedMobileApp;
    private Long canBeDeletedWeb;

    private Long canBeUpdatedMobileApp;
    private Long canBeUpdatedWeb;
}
