package vn.com.unit.cms.core.module.emulate.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.emulate.dto.resp.EmulateResp;

@Getter
@Setter
public class EmulateViewOH extends EmulateResp{
	private String office;
	private String gadName;
	private String manager;
}
