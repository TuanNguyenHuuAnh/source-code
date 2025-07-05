package vn.com.unit.ep2p.core.ds.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VersionDto {
	
	private Integer id;
	
	private String platform;
	
	private String version;
	
	private String configuration;
	
	private String message;
	
	private String updateNote;
	
	private String createdDate;
	
	private String updatedDate;
}
