package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.cms.core.module.banner.dto.BannerSearchDto;
import vn.com.unit.cms.core.module.banner.dto.resp.BannerResp;

public interface CmsBannerService {
    //public List<BannerResp> getListBannerResp(String bannerType, String langCode);
    
    public List<BannerResp> searchByCondition(BannerSearchDto searchDto, Pageable pageable, Integer modeView, String channel);

    public String getAnimationSlider(BannerSearchDto searchDto, String langCode);
    
    public String getSlideTime(BannerSearchDto searchDto, String langCode);
    
    public BannerResp getBannerVideo(BannerSearchDto searchDto, String langCode);
}
