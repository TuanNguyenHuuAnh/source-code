package vn.com.unit.cms.core.module.favorite.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteSearchResultDto {
	private Integer no;
	private Long id;
	private Long menuId;
	private String type;
	private String title;
	private String link;
	private String named;
	private String name;
	private String icon;
	private Integer defaultFlag;
	private boolean localLink;
	private String itemId;
	private List<FavoriteSearchResultDto> subMenuList;
}
