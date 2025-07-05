package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
//import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "CMS_DOCUMENT_MANAGEMENT")
@Getter
@Setter
public class DocumentManagement {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_CMS_DOCUMENT_MANAGEMENT")
	private Long id;

//	@Column(name = "code")
	private String code;

//	@Column(name = "name")
	private String name;

//	@Column(name = "parentId")
	private Long parentId;

	private Integer isFolder;

//	@Column(name = "companyId")
	private Long companyId;

	private Date createdDate;
	private Long createdId;
	private Date updatedDate;
	private Long updatedId;
	private Date deletedDate;
	private Long deletedId;

}
