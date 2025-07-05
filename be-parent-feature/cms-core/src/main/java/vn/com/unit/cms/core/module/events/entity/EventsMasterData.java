package vn.com.unit.cms.core.module.events.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;

@Table(name = CmsTableConstant.TABLE_EVENTS_MASTERDATA)
@Getter
@Setter
public class EventsMasterData {

	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PARENT_ID")
	private String parentId;
	
	@Column(name = "ACTIVE")
	private Integer active;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "CREATE_BY")
	private String createBy;
	
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@Column(name = "UPDATE_BY")
	private String updateBy;
}
