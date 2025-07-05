package vn.com.unit.ep2p.enumdef;

public enum ListPolicyMaturedPersonal {

		No("0")					// STT
,		policyKey("1")			// Số HDBH
,		poName("2")				// Bên mua BH
,		liName("3")				// Người được bảo hiểm chính
,		planName("4")			// Sản phẩm bảo hiểm chính
,		homeAddress("5")		// Địa chỉ
,		cellPhone("6")			// Số điện thoại
,		homePhone("7")			// Điện thoại bàn
,		workPhone("8")			// Điện thoại cơ quan
,		polIssueEff("9")		// Ngày hiệu lực
,		polCeasDt("10")			// Ngày đáo hạn
,		polBaseFaceAmt("11")		// Số tiền BH
,		amount("12")			// Số tiền tạm tính của quyền lợi đáo hạn
,		maturedStatus("13")		// Tình trạng đáo hạn
,		surveyResultDisplay("14")		// 0: Empty; 1: Có nhu cầu mua mới; 2: Đã nộp hồ sơ mua mới
;

	private String value;
	
	private ListPolicyMaturedPersonal(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
