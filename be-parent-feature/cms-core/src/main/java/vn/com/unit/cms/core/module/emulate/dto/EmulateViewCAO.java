package vn.com.unit.cms.core.module.emulate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.cms.core.module.emulate.dto.resp.EmulateResp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmulateViewCAO extends EmulateResp{
	private String bdth;
	private String bdah;
}
