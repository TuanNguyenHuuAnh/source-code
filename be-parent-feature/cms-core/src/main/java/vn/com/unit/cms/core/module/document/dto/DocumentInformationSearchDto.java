package vn.com.unit.cms.core.module.document.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentInformationSearchDto {
	private Integer No;
	private String agentCode; 
	private String docNo; 			// so HSYCBH
	private String policyNo; 		// so HĐBH
	private String customerName; 	// ten ben mua BH
	private String phone;
	private String typeOfRequest; 	// Loai yeu cau
	private String requestDetail; 	// chi tiet yeu cau gui
	private String requestSent; 	// lan goi yeu cau
	private String sentDate; 		// ngay gui
	private String expirationDate;  // ngay het han
	private String additionalDocuments; //chung tu bo sung
	private String dateOfFiling; 	// ngay gui chung tu
	private String confirm; 		// xac nhan khac hang
	private Integer page;
	private Integer pageSize;
}
