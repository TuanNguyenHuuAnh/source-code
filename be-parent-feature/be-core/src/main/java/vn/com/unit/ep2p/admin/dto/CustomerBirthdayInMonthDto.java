package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerBirthdayInMonthDto {
    private String agentCode;
    private String cliId;
    private String cliRole;
    private Date dateOfBirth;
    private String dob;
    private String mob;
}
