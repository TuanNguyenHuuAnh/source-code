package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfor {
	private String documentTitle;
	private String mimeType;
	private String fileSize;
	private String content;
	private String numberOfPages;
	private String filePath;
}
