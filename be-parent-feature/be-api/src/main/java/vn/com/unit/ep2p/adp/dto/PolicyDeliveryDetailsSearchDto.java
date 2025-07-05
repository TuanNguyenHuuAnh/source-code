package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PolicyDeliveryDetailsSearchDto extends CommonSearchWithPagingDto {
    private String po;
    private String type;
    private String policyNo;
    private String agentCode;
    private String partner;
    private String uoCode;
    private String fromDate;
    private String toDate;
    private String issuedDate; // Ngày phát hành (ISSUEDDATE)
    private String sentDate; // Ngày gửi (SENTDATE)
    private String packageNo; // Mã gói (PACKAGENO)
    private String emsDeliveryDate; // Ngày giao hàng EMS (EMSDELIVERYDATE)
    private String unitConfirmedDate; // Ngày PGD xác nhận (EMSDELIVERYDATE)

    // Các field bổ sung
    private String fcReceivedDate; // Ngày TVTC xác nhận (FCReceivedDate)
    private String ackReceivedDate; // Ngày nộp thư XN (ACKRecievedDate)
    private String policyReceiveDate; // Ngày KH ký thư XN (PolicyReceiveDate)
    private String status; // Tình trạng (Status)
    private String ackComment; // Lý do thư xác nhận không hợp lệ (AckComment)
}
