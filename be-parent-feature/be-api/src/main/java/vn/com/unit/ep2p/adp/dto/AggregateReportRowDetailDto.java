package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregateReportRowDetailDto {
	private String id;
	private String category;
	private int colspan;
	private String[] details;
	private String[][] datas;
}
