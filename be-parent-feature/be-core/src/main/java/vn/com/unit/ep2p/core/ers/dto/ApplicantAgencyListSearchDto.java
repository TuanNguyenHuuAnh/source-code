package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantAgencyListSearchDto {

	private String applyForPosition;

	private String statusProcess; // ? trạng thái quy trình

	private String statusForm; // ? trạng thái hồ sơ
	
	private String formStatusCode;

	private String idNo; // CMND/CCCD

	private String candidateName; // tên ứng tuyển

	private String recruiterCode; // mã người tuyển dụng

	private String adCode; // mã AD

	private String avicadStatus; // trạng thái avicad

	private String classCode; // mã lớp học

	private Date createdDateFrom; // từ ngày tạo

	private Date createdDateTo; // đến ngày tạo
	
	private int deletedFlag;

	private String language;

    private int hasViewAll = 0;
    
    private int hasViewHighLevel = 0;

    private Long userActionId;
}
