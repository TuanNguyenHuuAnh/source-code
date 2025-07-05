package vn.com.unit.ep2p.enumdef;

public enum GroupInsuranceDocumentsExportActiveEnum {
	 NO("0")
	,MANAGER("1")
	,AGENT("2")
	,POLICYNO("3")
	,insuranceBuyer("4")
	,insuredPerson("5")
	,effectiveDate("6")
	,polInfcDt("7")
	,recurringFeePayment("8")
	,polSndryAmt("9")
	,polMpremAmt("10")
	,ip("11")
	,polBaseFaceAmt("12")
	;

	private String value;

	private GroupInsuranceDocumentsExportActiveEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
