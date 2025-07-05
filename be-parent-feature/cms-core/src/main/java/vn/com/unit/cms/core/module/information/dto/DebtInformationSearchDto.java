package vn.com.unit.cms.core.module.information.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class DebtInformationSearchDto extends CommonSearchWithPagingDto{
	private String agentCode;
	private String months;
	private String year;
	private String keyType;
	private Object type;
	private Object arisingDate;
	private Object debtMoney;
	private String explain;
	private String orgCode;
	private Object typeSearch;
	private Object allowancetype;

}
