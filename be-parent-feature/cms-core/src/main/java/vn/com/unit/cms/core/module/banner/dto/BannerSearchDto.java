package vn.com.unit.cms.core.module.banner.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

@Getter
@Setter
public class BannerSearchDto extends CmsCommonSearchFilterDto {
    private static final long serialVersionUID = -1591011265972703631L;

    private String bannerType;

    private String bannerDevice;
    
    private String bannerPage;
    
    private String bannerPhysicalImg;
    
    private String bannerTypeName;

    private String bannerDeviceName;
    
    private String bannerPhysicalUrl;
}
