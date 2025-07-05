package vn.com.unit.ep2p.enumdef;

public enum EmulateChallengeGroupExportOH {
		NO("0")
		, MEMONO("1")
		, AGENTCODE("2")
		, AGENTNAME("3")
		, AGENTTYPE("4")
		
		, REPORTINGTOCODE("5")
		, REPORTINGTONAME("6")
		, REPORTINGTOTYPE("7")
		
		, BMCODE("8")
		, BMNAME("9")
		, BMTYPE("10")
		
		, GADCODE("11")
		, GADNAME("12")
		
		, GACODE("13")
		
		, OFFICE("14")
		
		, POLICYNO("15")
		, APPLIEDDATE("16")
		, ISSUEDDATE("17")
		, RESULT("18")
		, ADVANCE("19")
		, BONUS("20")
		, CLAWBACK("21")
		, NOTE("22"); // OH 23

		private String value;

		private EmulateChallengeGroupExportOH(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
}
