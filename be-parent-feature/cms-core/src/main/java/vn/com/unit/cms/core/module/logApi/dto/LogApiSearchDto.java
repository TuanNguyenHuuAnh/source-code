package vn.com.unit.cms.core.module.logApi.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.ConditionSearchCommonDto;

@Getter
@Setter
public class LogApiSearchDto extends ConditionSearchCommonDto {
	private static final long serialVersionUID = 1L;
	/** fieldSearch */
    private String fieldSearch;
    /** fieldValues */
    private String fieldValues;
    private String searchKeyIds;
    private String username;
    private String endpoint;
    private String status;
    private String clientIp;
    private String storeName;
    private String message;
    private String startTime;
    private String endTime;
    private Date startDate;
    private Date endDate;
    private String url;
}
