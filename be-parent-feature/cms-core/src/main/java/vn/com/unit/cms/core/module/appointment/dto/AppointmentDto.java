package vn.com.unit.cms.core.module.appointment.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDto {
	private String no;
	private Long id;
	private String clientId;
	private String clientName;
	private String purposeCode;
	private Date appointmentTime;
	private String location;
	private String notes;
}
