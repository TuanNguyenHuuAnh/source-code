package vn.com.unit.ep2p.enumdef;

public enum GroupInsuranceDocumentsExportActiveUMEnum {
	 NO("0")
	,AGENT("1")
	,POLICYNO("2")
	,insuranceBuyer("3")
	,insuredPerson("4")
	,effectiveDate("5")
	,polInfcDt("6")
	,recurringFeePayment("7")
	,polSndryAmt("8")
	,polMpremAmt("9")
	,ip("10")
	,polBaseFaceAmt("11")
	;

	private String value;

	private GroupInsuranceDocumentsExportActiveUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
