package vn.com.unit.cms.core.module.banner.dto;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerLanguageDto {

	private Long id;

	private Long bannerId;

	private String languageCode;

	private String bannerTextLeft;

	private String bannerTextRight;

	@Size(max = 500)
	private String bannerLink;

	private String bannerImg;

	private String bannerPhysicalImg;

	private String bannerVideo;
	
    private Integer bannerVideoType;

	private String bannerPhysicalVideo;

	@Size(max = 500)
	private String bannerYoutubeVideo;

	@Size(max = 30)
	private String textButton;

	private String description;

	private String title;

	private String bannerImageMiddle;

}