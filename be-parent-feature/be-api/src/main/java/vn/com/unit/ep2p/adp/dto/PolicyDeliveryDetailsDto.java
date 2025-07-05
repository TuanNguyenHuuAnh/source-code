package vn.com.unit.ep2p.adp.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyDeliveryDetailsDto {
    private String no; // PO
    private String orderId; // Mã đơn hàng (ORDERID)
    private String policyNo; // Số hợp đồng (POLICYNO)
    private String po; // PO
    private String unitId; // Mã đơn vị (UNITID)
    private String partnerCode; // Mã đối tác (PARTNERCODE)
    private String unitNm; // Tên đơn vị (UNITNM)
    private Date issuedDate; // Ngày phát hành (ISSUEDDATE)
    private Date sentDate; // Ngày gửi (SENTDATE)
    private String packageNo; // Mã gói (PACKAGENO)
    private Date emsDeliveryDate; // Ngày giao hàng EMS (EMSDELIVERYDATE)
    private Date unitConfirmedDate; // Ngày PGD xác nhận(EMSDELIVERYDATE)
    private String note; // Ghi chú (NOTE)

    private String agentName; // Tên đại lý
    private String agentCode; // Mã đại lý

    // Các field được bổ sung
    private Date fcReceivedDate; // Ngày TVTC xác nhận (FCReceivedDate)
    private Date ackReceivedDate; // Ngày nộp thư XN (ACKRecievedDate)
    private Date policyReceiveDate; // Ngày KH ký thư XN (PolicyReceiveDate)
    private String status; // Tình trạng (Status)
    private String ackComment; // Lý do thư xác nhận không hợp lệ (AckComment)
}
