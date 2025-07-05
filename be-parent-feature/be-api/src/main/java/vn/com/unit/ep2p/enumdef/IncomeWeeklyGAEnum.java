package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IncomeWeeklyGAEnum {
	TYPEPAYMENTNAME("1")
	, PAYMENTDATE("2")
	, PAYAMOUNTBIG("3")
	;
	private String value;
	public String toString() {
		return value;
	}
}
