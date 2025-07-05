package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportK2K2PlusPagination<T> {
	private BigDecimal totalEpp;
	private BigDecimal totalApp;
	private BigDecimal totalTp;
	private BigDecimal totalEp;
	private Integer totalData;
	private List<T> data;
}
