package vn.com.unit.ep2p.enumdef;

public enum EmulateChallengeGroupExportRH {
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
		, BDOHCODE("15")
		
		, TERRYTORY("16")
		, BDRHCODE("17")
		
		//, AREA("18")
		//, BDAHCODE("19")
		
		//, REGION("20")
		//, BDTHCDE("21")
		
		, POLICYNO("18")
		, APPLIEDDATE("19")
		, ISSUEDDATE("20")
		, RESULT("21")
		, ADVANCE("22")
		, BONUS("23")
		, CLAWBACK("24")
		, NOTE("25");

		private String value;

		private EmulateChallengeGroupExportRH(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
}
