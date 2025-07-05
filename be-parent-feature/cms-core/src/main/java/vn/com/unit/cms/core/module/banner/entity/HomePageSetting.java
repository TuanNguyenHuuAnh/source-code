package vn.com.unit.cms.core.module.banner.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import lombok.Getter;
import lombok.Setter;

@Table(name = "m_homepage_setting")
@Getter
@Setter
public class HomePageSetting extends AbstractTracking {
    @Id
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_HOMEPAGE_SETTING")
    @Column(name = "ID")
    private Long id;

    @Column(name = "BANNER_PAGE")
    private String bannerPage;

    @Column(name = "SLIDE_TIME")
    private String slideTime;

    @Column(name = "BANNER_TYPE")
    private String bannerType;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "YOUTUBE_VIDEO")
    private String youtubeVideo;

    @Column(name = "LINK_YOUTUBE_VIDEO")
    private String linkYoutubeVideo;

    @Column(name = "M_BANNER_TOP_ID")
    private String mBannerTopId;

    @Column(name = "M_BANNER_TOP_MOBILE_ID")
    private String mBannerMobileId;

    @Column(name = "AUTO_PLAY")
    private boolean autuPlay;

    @Column(name = "AUTO_MUTE")
    private boolean autoMute;

    @Column(name = "AUTO_REPLAY")
    private boolean autoReplay;

    @Column(name = "EFFECT")
    private String effect;
    
    @Column(name = "CHANNEL")
    private String channel;

}
