package vn.com.unit.cms.core.module.appointment.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "M_APPOINTMENT")
@Getter
@Setter
public class Appointment {

	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_APPOINTMENT")
    private Long id;
	
	@Column(name = "CLIENT_ID")
	private String clientId;
	
	@Column(name = "CLIENT_NAME")
	private String clientName;
	
	@Column(name = "PURPOSE_CODE")
	private String purposeCode;
	
	@Column(name = "APPOINTMENT_TIME")
	private Date appointmentTime;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "NOTES")
	private String notes;

	@Column(name = "CREATED_DATE")
	private Date createDate;

	@Column(name = "CREATED_BY")
	private String createBy;
}
