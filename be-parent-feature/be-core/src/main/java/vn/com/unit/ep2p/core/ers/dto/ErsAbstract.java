package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsAbstract {
    protected static final String DATE_PATTERN = "dd/MM/yyyy";
    protected static final String TIME_ZONE = "Asia/Saigon";

	private Long id;
    private Long no;
    @JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
    private Date modifiedDate;
    private String modifiedBy;
    private Date deletedDate;
    private String deletedBy;
    private Date createdDateFrom;
    private Date createdDateTo;
}
