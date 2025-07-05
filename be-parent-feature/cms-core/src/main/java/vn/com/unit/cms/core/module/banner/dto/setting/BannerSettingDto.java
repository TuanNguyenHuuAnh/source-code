package vn.com.unit.cms.core.module.banner.dto.setting;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

@Getter
@Setter
public class BannerSettingDto extends CmsCommonSearchFilterDto {
    private static final long serialVersionUID = -1591011265972703631L;

    private String bannerPage;
        
    private String bannerPageName;
     
    private String startDate;
    
    private String endDate;
    
    private String mBannerTopId;
   
    private String mBannerMobileId;
    
    private String mBannerTopMobileId;
    
    private String bannerTypeName;
    
}
