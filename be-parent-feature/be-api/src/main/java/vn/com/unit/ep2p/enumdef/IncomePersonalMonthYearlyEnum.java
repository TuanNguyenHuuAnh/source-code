package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum IncomePersonalMonthYearlyEnum {
    MAINNAME("1")
	, JANAMOUNT("2")
	, FEBAMOUNT("3")
	, MARAMOUNT("4")
	, APRAMOUNT("5")
	, MAYAMOUNT("6")
	, JUNAMOUNT("7")
	, JULAMOUNT("8")
	, AUGAMOUNT("9")
	, SEPAMOUNT("10")
	, OCTAMOUNT("11")
	, NOVAMOUNT("12")
	, DECAMOUNT("13")
	;
	private String value;
	public String toString() {
		return value;
	}
}
