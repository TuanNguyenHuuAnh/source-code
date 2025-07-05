package vn.com.unit.ep2p.core.req.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErsAgSelfInputSearchReq {

	private Integer page;
	private Integer pageSize;
	
	private String channel;
	
	
	private String applyForPosition;
	private String formStatus;
	private String province;
 	private Date beforeADLocationDate;
	private String adCode;
	private String recruiterCode;
	
	
}
