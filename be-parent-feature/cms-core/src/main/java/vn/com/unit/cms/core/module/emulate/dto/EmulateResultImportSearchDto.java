package vn.com.unit.cms.core.module.emulate.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;

/**
 * @author TaiTM
 */
@Getter
@Setter
public class EmulateResultImportSearchDto extends ImportExcelSearchDto {
    private static final long serialVersionUID = -3338698957344089232L;

    private String agentCode;

    private Long id;

    private String agentName;

    private String results;
}
