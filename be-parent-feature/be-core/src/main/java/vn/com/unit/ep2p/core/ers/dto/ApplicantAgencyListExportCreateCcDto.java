package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantAgencyListExportCreateCcDto {
	private String no;
	
	private String agentCode; // Mã số/ CC
	private String dateAppointed; // Ngày cấp code
	private String dateTerminate;
	
	private String bpCode; // bp => null
	
	private String applyForPosition; // Vị trí ứng tuyển
	private String candidateName; // Họ và tên
	private String idNo;
	private String recruiterIdNo; // Số CMT hoặc Mã số NGT
	private String recruiterName;
	private String managerIdNo; // Số CMT hoặc Mã số QLTT
	private String managerName; // Họ tên quản lý trực tiếp
	private String managerIndirectCode; // Mã số Quản lý cấp trên gần => giáp tiếp cấp 1
	private String managerIndirectName; // Họ tên Quản lý cấp trên gần nhất đã gia nhập Công ty => gián tiếp cấp 1
	private String managerAdName; // Họ tên Giám đốc kinh doanh khu vực (AD)
	
	// ERS_AGENCY_STRUCTURE
	private String directRdName; // managerName => Họ tên Giám đốc kinh doanh vùng (RD)
	private String areaManagerName; // managerName => Họ tên Giám đốc kinh doanh vùng cấp cao (SRD)

	private String mbfuFlag; // Hoàn thành MBSU
	private String mbfsFlag; // Hoàn thành MBFS
	private String classCode; // Đăng ký học lớp MBFS
	private String examFlag; // Hoàn thành đào tạo để cấp mã số (chỉ áp dụng đối với loại đăng ký Học full MBSU và Đủ ĐK chỉ học MBFS

	// ASC kiểm tra
	private String checkAg;// AR kiểm tra
	
	// 2021-06-11 LocLT Fix Bug #44375
	private String recruiterAdName;
	private String managerIndirectAdName;
}
