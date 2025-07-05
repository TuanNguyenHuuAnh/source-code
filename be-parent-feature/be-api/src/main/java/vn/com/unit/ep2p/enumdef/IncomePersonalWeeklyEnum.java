package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IncomePersonalWeeklyEnum {
	NO("0")
	, INCOMETYPE("1")
	, PAYMENTDATE("2")
	, PAYMENTAMOUNTDTO("3")
	;
	private String value;
	public String toString() {
		return value;
	}
}
