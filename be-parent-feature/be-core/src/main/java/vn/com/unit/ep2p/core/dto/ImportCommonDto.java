// 2021-04-06 LocLT Task #40894

package vn.com.unit.ep2p.core.dto;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportCommonDto<E> extends PluploadDto {

    private Long id;

    private String sessionKey;

    private String messageError;

    private List<String> listMessageErrors;

    private String messageWarning;

    private List<String> listMessageWarnings;

    // @Getter(value=AccessLevel.NONE)
    // @Setter(value=AccessLevel.NONE)
    private boolean isError;

    public boolean isError() {
		return isError;
	}

	public void setIsError(boolean isError) {
		this.isError = isError;
	}

	private boolean isWarning;

    private boolean isSaved;

    private Integer startRowData = new Integer(5);

    private Class<E> enumImport;

    private Map<String, List<String>> colsValidate;

    private ModelAndView mav;
    
    
}
