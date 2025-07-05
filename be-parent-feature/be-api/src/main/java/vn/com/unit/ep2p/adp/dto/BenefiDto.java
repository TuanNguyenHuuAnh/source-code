package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BenefiDto {
	public String cliId;
	public String bnfyRelInsrdCd;
	public BigDecimal bnfyPrcdsPct;
	public String cliSexCd;
	public Date cliBthDt;
	public String cliIdNum;
	public String beneName;
}
