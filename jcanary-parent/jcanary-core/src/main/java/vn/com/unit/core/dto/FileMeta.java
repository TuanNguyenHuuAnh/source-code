package vn.com.unit.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({ "bytes" })
public class FileMeta {

	private String fileName;
	private String physicalFileName;
	private Long fileSize;
	private String fileType;
	private String description;
	private String contentType;
}
