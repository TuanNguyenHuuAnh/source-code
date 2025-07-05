package vn.com.unit.ep2p.enumdef;

public enum EmulateChallengeGroupExportTH {
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
		
		, AREA("18")
		, BDAHCODE("19")
		
		, POLICYNO("20")
		, APPLIEDDATE("21")
		, ISSUEDDATE("22")
		, RESULT("23")
		, ADVANCE("24")
		, BONUS("25")
		, CLAWBACK("26")
		, NOTE("27"); //TH  28

		private String value;

		private EmulateChallengeGroupExportTH(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
}
