package vn.com.unit.cms.core.module.faqs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqsCategoryLangDto {
	private Long id;

	/** code */
	private String code;

	/** name */
	private String label;
}
