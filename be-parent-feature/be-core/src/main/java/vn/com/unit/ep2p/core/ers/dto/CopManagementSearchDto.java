package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopManagementSearchDto {

	private Date sDateFrom;
	
	private Date sDateTo;
	
	private String candidateName;
	
	private String idNo;
	
    private Date startDateFrom;

    private Date startDateTo;

}
