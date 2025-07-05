package vn.com.unit.ep2p.enumdef;

public enum GroupInsuranceDocumentsExportCanelEnum {
	NO("0")
	,MANAGER("1")
	,AGENT("2")
	,POLICYNO("3")
	,insuranceBuyer("4")
	,insuredPerson("5")
	,effectiveDate("6")
	,cancelDate("7")
	,recurringFeePayment("8")
	,polSndryAmt("9")
	,polMpremAmt("10")
	,ip("11")
	,polBaseFaceAmt("12")
	,polNtuCnclFeeAmt("13")
	;

	private String value;

	private GroupInsuranceDocumentsExportCanelEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
