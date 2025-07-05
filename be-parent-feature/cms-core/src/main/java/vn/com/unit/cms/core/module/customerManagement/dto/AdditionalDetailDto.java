package vn.com.unit.cms.core.module.customerManagement.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalDetailDto {
    private String requestDesc;     //Chi tiết yêu cầu
    private String requestNo;       //Loại yêu cầu
    private Date requestDate;     //Ngày gửi yêu cầu
    private Date expiredDate;     //Ngày hết hạn
    private String requestType;
    private String cliId; //mã người được bảo hiểm chính
    private String cliName; //người được bảo hiểm chính
    private Date cpreqEffDt;
}
