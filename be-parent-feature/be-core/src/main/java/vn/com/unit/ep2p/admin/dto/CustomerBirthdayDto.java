package vn.com.unit.ep2p.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerBirthdayDto {
    private String customerBirthday;
    private String customerId;
    private String customerRole;
    private String customerName;
    private String no;
    private Date dateOfBirth;
    private String gioiTinh;
    private String poLnk;//Task #62077
}
