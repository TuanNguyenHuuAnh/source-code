package vn.com.unit.ep2p.enumdef;

public enum EnumExportListTrainingCourses {

		No("0")						// STT
,		courseCode("1")				// Mã khóa HL
,		courseName("2")				// Tên khóa huấn luyện
,		contents("3")				// Nội dung huấn luyện
,		createdName("4")			// Người huấn luyện
,		startDate("5")				// Thời gian bắt đầu
,		endDate("6")				// Thời gian kết thúc
,		location("7")				// Địa điểm
,		statusText("8")				// Trạng thái
,		notes("9")					// Ghi chú
,		quantity("10")				// Số lượng học viên ghi danh
,		quantityAttendance("11")	// Số lượng học viên ghi danh
,		approvedDate("12")			// Ngày duyệt
,		approvedName("13")			// Người duyệt
,		rejectedDate("14")			// Ngày từ chối
,		rejectedName("15")			// Người từ chối
,		rejectedReason("16")		// Lý do từ chối
;

	private String value;
	
	private EnumExportListTrainingCourses(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
