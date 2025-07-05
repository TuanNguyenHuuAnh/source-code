package vn.com.unit.process.workflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.workflow.dto.JpmButtonLangDto;

@Getter
@Setter
@NoArgsConstructor
public class AppButtonDto {
    private Long id;
    private Long processId;

    private String buttonCode;
    private String buttonText;
    private String buttonValue;
    private String buttonClass;
    private String buttonType;

    private Long orders;
    private Long assignTo;
    
    private List<JpmButtonLangDto> listJpmButtonLang;
    
    public AppButtonDto(String code, String text, String value, String className, String type) {
        buttonCode = code;
        buttonText = text;
        buttonValue = value;
        buttonClass = className;
        buttonType = type;
    }

}
