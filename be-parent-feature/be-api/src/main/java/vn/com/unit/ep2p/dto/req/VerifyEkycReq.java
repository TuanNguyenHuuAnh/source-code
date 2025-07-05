package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyEkycReq {

	private Long id;
	private String live;
	private String idcard;
}
