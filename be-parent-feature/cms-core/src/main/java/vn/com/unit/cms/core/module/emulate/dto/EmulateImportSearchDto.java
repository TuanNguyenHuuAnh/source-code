package vn.com.unit.cms.core.module.emulate.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;

@Getter
@Setter
public class EmulateImportSearchDto extends ImportExcelSearchDto {
    private static final long serialVersionUID = -3338698957346089232L;

    private Long id;

    private String agentName;

    private String results;
    
    private String agentCode;

    
}
