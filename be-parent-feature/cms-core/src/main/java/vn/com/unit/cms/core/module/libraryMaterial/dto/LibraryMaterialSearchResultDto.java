package vn.com.unit.cms.core.module.libraryMaterial.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryMaterialSearchResultDto {
    private Long id;
    private Long categoryId;
    private String title;
    private String keywordsSeo;
    private String physicalFileName;
    private String fileName;
}
