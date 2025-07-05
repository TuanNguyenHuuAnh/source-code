/*******************************************************************************
 * Class        ：JcaMBannerLanguage
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：hand
 * Change log   ：2017/02/14：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.banner.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import jp.sf.amateras.mirage.annotation.Table;
//import vn.com.unit.jcanary.entity.AbstractTracking;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * JcaMBannerLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_banner_language")
@Getter
@Setter
public class BannerLanguage extends AbstractTracking {
    @Id
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_BANNER_LANGUAGE")
    @Column(name = "id")
    private Long id;

    @Column(name = "banner_id")
    private Long bannerId;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "banner_image_middle")
    private String bannerTextLeft;

    @Column(name = "banner_link")
    private String bannerLink;

    @Column(name = "banner_img")
    private String bannerImg;

    @Column(name = "banner_physical_img")
    private String bannerPhysicalImg;

    @Column(name = "banner_video")
    private String bannerVideo;
    
    @Column(name = "BANNER_VIDEO_TYPE")
    private int bannerVideoType;

    @Column(name = "banner_physical_video")
    private String bannerPhysicalVideo;

    @Column(name = "BANNER_YOUTUBE_VIDEO")
    private String bannerYoutubeVideo;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

}