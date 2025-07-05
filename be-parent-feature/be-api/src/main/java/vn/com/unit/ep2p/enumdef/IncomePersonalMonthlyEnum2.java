package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum IncomePersonalMonthlyEnum2 {
    MAINDIRECTORY("0")
	, SUBDIRECTORY("1")
	, DETAILDIRECTORY("2")
	, AMOUNTDIRECTORY("3")
	;
	private String value;
	public String toString() {
		return value;
	}
}
