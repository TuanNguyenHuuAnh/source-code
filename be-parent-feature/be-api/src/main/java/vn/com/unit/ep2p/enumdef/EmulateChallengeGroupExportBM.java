package vn.com.unit.ep2p.enumdef;

public enum EmulateChallengeGroupExportBM {
		NO("0")
		, MEMONO("1")
		, AGENTCODE("2")
		, AGENTNAME("3")
		, AGENTTYPE("4")
		
		, REPORTINGTOCODE("5")
		, REPORTINGTONAME("6")
		, REPORTINGTOTYPE("7")
		
		, POLICYNO("8")
		, APPLIEDDATE("9")
		, ISSUEDDATE("10")
		, RESULT("11")
		, ADVANCE("12")
		, BONUS("13")
		, CLAWBACK("14")
		, NOTE("15");//BM  16

		private String value;

		private EmulateChallengeGroupExportBM(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
}
