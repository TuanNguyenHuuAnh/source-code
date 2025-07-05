package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum IncomePersonalTotalYearlyEnum {
    MAINNAME("1")
	, TOTALAMOUNT("2")	
	;
	private String value;
	public String toString() {
		return value;
	}
}
