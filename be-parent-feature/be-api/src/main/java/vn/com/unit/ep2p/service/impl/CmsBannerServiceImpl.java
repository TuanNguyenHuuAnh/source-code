package vn.com.unit.ep2p.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.banner.dto.BannerSearchDto;
import vn.com.unit.cms.core.module.banner.dto.resp.BannerResp;
import vn.com.unit.cms.core.module.banner.repository.BannerRepository;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.ep2p.service.CmsBannerService;
import vn.com.unit.ep2p.utils.RestUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CmsBannerServiceImpl implements CmsBannerService {
    @Autowired
    private BannerRepository bannerRepository;
    
    @Override
	public List<BannerResp> searchByCondition(BannerSearchDto searchDto, Pageable pageable, Integer modeView, String channel) {
		List<BannerResp> list = bannerRepository.findListBannerResp(searchDto, pageable, modeView, channel).getContent();
        if (CollectionUtils.isNotEmpty(list)) {
            for (BannerResp banner : list) {
                if (StringUtils.isNotBlank(banner.getBannerPhysicalImg())
                        && !banner.getBannerPhysicalImg().contains("https://www.youtube.com")) {
                    banner.setBannerPhysicalImg(RestUtil.replaceImageUrl(banner.getBannerPhysicalImg(), null));
                }
            }
        }
        return list;
	}

	@Override
	public String getAnimationSlider(BannerSearchDto searchDto, String langCode) {
		return bannerRepository.getAnimationSlider(searchDto, langCode);
	}

	@Override
	public String getSlideTime(BannerSearchDto searchDto, String langCode) {
		return bannerRepository.getSlideTime(searchDto, langCode);
	}

	@Override
	public BannerResp getBannerVideo(BannerSearchDto searchDto, String langCode) {
		BannerResp bannerVideo = new BannerResp();
		BannerResp bannerHome = bannerRepository.getBannerVideoHomepage(searchDto);
		if(ObjectUtils.isNotEmpty(bannerHome)) {
			if(CommonStringUtil.isNotEmpty(bannerHome.getYoutubeVideo())) {
				bannerVideo.setBannerVideo(bannerHome.getYoutubeVideo());
				bannerVideo.setTypeVideo("youtube");
				bannerVideo.setAutoPlay(bannerHome.getAutoPlay());
				bannerVideo.setIsMute(bannerHome.getIsMute());
				bannerVideo.setType(bannerHome.getType());
				bannerVideo.setRepeatPlay(bannerHome.getRepeatPlay());
			}
			else {
				bannerVideo=bannerRepository.getBannerVideo(searchDto, langCode);
				bannerVideo.setBannerVideo(RestUtil.replaceImageUrl(bannerVideo.getBannerPhysicalVideo(), null));
            	bannerVideo.setTypeVideo("upload");
            	bannerVideo.setBannerPhysicalVideo(null);
			}
		}
		return bannerVideo;
	}

}
