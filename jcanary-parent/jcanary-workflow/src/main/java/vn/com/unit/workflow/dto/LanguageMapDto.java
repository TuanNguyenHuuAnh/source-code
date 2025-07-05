package vn.com.unit.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageMapDto {

    private String langCode;
    private String nameValue;
    private String passiveNameValue;

}
