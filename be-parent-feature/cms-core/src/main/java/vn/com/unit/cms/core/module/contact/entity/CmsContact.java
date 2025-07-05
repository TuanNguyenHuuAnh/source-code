package vn.com.unit.cms.core.module.contact.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "CMS_CONTACT")
public class CmsContact {

	protected static final String DATE_PATTERN = "dd/MM/yyyy";
	protected static final String TIME_ZONE = "Asia/Saigon";

	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_CMS_CONTACT")
	private Long id;

	private String agentCode;

	private String name;

	private String email;

	private String address;

	private String phone;

	private String title;

	private String content;
	
	private Integer sendMail;

	private Long createdId;

	private Date createdDate;

	private Long updatedId;

	private Date updatedDate;

	private Long deletedId;

	private Date deletedDate;

}
