package vn.com.unit.cms.core.module.libraryMaterial.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryMaterialSuperDto {
	private Long id;
	private Long parentId;
	private String title;
	private String no;
	private String physicalFileName;
	private String typeOfFile;
	private Date postedDate;
}
