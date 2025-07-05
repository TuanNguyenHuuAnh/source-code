package vn.com.unit.ep2p.enumdef;

public enum EmulateChallengeGroupExportUM {
		NO("0")
		, MEMONO("1")
		, AGENTCODE("2")
		, AGENTNAME("3")
		, AGENTTYPE("4")
		, POLICYNO("5")
		, APPLIEDDATE("6")
		, ISSUEDDATE("7")
		, RESULT("8")
		, ADVANCE("9")
		, BONUS("10")
		, CLAWBACK("11")
		, NOTE("12");//UM 13

		private String value;

		private EmulateChallengeGroupExportUM(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
}
