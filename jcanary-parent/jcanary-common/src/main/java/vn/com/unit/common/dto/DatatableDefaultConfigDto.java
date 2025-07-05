package vn.com.unit.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatatableDefaultConfigDto {
    private Long id;
    private String functionCode;
    private String jsonConfig;
    private String functionType;
    private String field;
    private String fieldName;
    private String fieldType;
    private String fieldFormat;
    private boolean isChecked;
    private boolean isPrimary;
    private int sort;
    private String langugeCode;
    private String column;
    private String selectUrl;
    private String aliasTable;
    private Integer showInTable;
    private Integer showInSearch;
}
