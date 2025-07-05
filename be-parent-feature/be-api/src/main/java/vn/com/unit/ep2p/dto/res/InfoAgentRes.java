package vn.com.unit.ep2p.dto.res;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoAgentRes {
	private String fullname;
	private int code;
	private String email;
	private String office;
	private Date expireDate;
	private long debt;
	private int receipts;
	private boolean liquidation;
}
