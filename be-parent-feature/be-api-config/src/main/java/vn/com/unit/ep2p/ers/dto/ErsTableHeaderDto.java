// 2021-04-12 LocLT Task #41067

package vn.com.unit.ep2p.ers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsTableHeaderDto {
	private String title;
	private String dataIndex;
	private int width = 100;
	private String align = "center";
	private String format = "text";
}
