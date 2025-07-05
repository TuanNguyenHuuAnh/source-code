package vn.com.unit.cms.core.module.emulate.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.emulate.dto.resp.EmulateResp;

@Getter
@Setter
public class EmulateViewBM extends EmulateResp{
	private String manager;
	private String tvtc;
}
