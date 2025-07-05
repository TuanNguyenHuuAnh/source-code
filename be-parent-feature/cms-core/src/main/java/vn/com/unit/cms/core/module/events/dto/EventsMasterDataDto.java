package vn.com.unit.cms.core.module.events.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsMasterDataDto {
	private String type;
	private String code;
	private String name;
	private String parentId;
}
