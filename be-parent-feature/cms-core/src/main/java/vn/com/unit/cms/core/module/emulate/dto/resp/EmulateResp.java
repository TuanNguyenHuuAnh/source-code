package vn.com.unit.cms.core.module.emulate.dto.resp;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmulateResp {
	private Long id;
	private String title;
	private String memoCode;
	private String description;
    private String contestPhysicalFile;
    private String contestPhysicalImg;
	private Date startDate;
	private Date endDate;
}
