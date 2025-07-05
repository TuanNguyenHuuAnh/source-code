package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PolicyDeliverySearchDto extends CommonSearchWithPagingDto {
    private String id;
    private String po;
    private String type;
    private String agentCode;
    private String partner;
    private String uoCode;
    private String fromDate;
    private String toDate;
    private String regionCode;
    private String zoneCode;
    
    private Object policyNo; 	// Số HĐ
    private Object issuedDate; 	// Ngày phát hành
    private Object sentDate; 	// Ngày DLVN gửi
    private Object packageNo; 	// Mã bưu kiện
    private Object status; 		// Tình trạng
}
