package vn.com.unit.cms.core.module.agent.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateDto {
	private Integer no;
	private String certificateCategoryCode; //ma loai bang cap
	private String certificateName; //ten chung nhan chung chi
	private String linkLetter; //link file PDF
	private String productCode; //ma sp
	private String categoryName; //loai sp
	private Date dateCert; //ngay ser
	private Date effectiveDate;
	private boolean certificateExpired;
	private boolean share;
	private boolean download;
	private String base64File;
	private String fileName;
	private String type;

}
