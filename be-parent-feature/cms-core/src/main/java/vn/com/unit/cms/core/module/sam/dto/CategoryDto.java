package vn.com.unit.cms.core.module.sam.dto;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private Long id;

	private String category;

	@Getter(value = AccessLevel.NONE)
	private String categoryDisplay;
	
	private String type;
	
	private String sm;

	private String il;

	private String ioIs;

	public String getCategoryDisplay() {
		categoryDisplay = category;
		if(StringUtils.isNotBlank(category) && category.length() > 25) {
			categoryDisplay = category.substring(0, 25) + ".....";
		}
		return categoryDisplay;
	}
	
}
