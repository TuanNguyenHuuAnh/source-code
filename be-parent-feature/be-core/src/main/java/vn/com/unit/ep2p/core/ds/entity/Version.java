package vn.com.unit.ep2p.core.ds.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Table(name = "TB_VERSION")
@Getter
@Setter
public class Version {

	@Id
    @Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.IDENTITY	)
    private Integer id;
	
	@Column(name = "PLATFORM")
	private String platform;
	
	@Column(name = "VERSION")
	private String version;
	
	@Column(name = "CONFIGURATION")
	private String configuration;
	
	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "UPDATE_NOTE")
	private String updateNote;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
}
