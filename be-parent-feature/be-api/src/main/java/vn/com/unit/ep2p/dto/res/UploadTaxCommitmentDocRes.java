package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadTaxCommitmentDocRes {
	private Integer status;
	private String message;
	private String errorStr;
}