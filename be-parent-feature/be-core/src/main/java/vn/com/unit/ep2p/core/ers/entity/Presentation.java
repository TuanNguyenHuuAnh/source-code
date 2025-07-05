package vn.com.unit.ep2p.core.ers.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "ERS_PRESENTATION")
public class Presentation {

	@Id
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_ERS_PRESENTATION")
	@Column(name = "ID")
	private Long id;

	@Column(name = "CHANNEL")
	private String channel;

	@Column(name = "TYPE_LINK")
	private String typeLink;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "LINK_URL")
	private String linkUrl;

	@Column(name = "TYPE_OPEN_PAGE")
	private String typeOpenPage;

	@Column(name = "ORDER_ON_PAGE")
	private Integer orderOnPage;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "STATUS_ITEM")
	private String statusItem;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "DELETED_BY")
	private String deletedBy;
	
	@Column(name = "DELETED_DATE")
	private Date deletedDate;
	
	@Transient
	private Byte[] versionData;
}
