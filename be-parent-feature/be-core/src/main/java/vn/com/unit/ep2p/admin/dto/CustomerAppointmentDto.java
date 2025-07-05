package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAppointmentDto {
    private String no;
    private Long id;
    private String clientId;
    private String clientName;
    private String purposeCode;
    private String purposeName;
    private Date appointmentTime;
    private String location;
    private String notes;
    private Date createdDate;
}
