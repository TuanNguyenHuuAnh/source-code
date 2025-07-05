package vn.com.unit.cms.core.module.banner.dto.resp;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author TaiTM
 **/
@Getter
@Setter
public class BannerResp implements Serializable {
    private static final long serialVersionUID = -28652659059815594L;

    private Long id;
    private String bannerImg;
    private String bannerPhysicalImg;
    private String bannerPhysicalVideo;
    private String bannerYoutubeVideo;
    private String bannerVideo;
    private String typeVideo;
    private String bannerLink;
    private String title;
    private String description;
    private String type;
    private Boolean autoPlay;
    private Boolean isMute;
    private Boolean repeatPlay;
    private Long bannerTopId;
    private Long bannerTopMobileId;
    private String youtubeVideo;
    private String stringBannerTopId;
    private String stringBannerTopMobileId;
    private String bannerId;
}
