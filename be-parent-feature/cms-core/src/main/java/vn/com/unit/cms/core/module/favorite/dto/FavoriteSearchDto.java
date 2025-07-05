package vn.com.unit.cms.core.module.favorite.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class FavoriteSearchDto extends CommonSearchWithPagingDto {

	private String agentCode;
	private String type;
	private String title;
	private String link;
	private String named;
	private String icon;
	private String itemId;
}
