package vn.com.unit.cms.core.module.customer.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;
@Getter
@Setter
@Table(name = CmsTableConstant.TABLE_NICKNAME)

public class ClientNickName {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CLIENT_NICKNAME")
	private Long id;
	
	@Column(name = "CLIENT_ID")
	private String clientId;
	
	@Column(name = "NICK_NAME")
	private String nickName;

	@Column(name = "AGENT_CODE")
	private String agentCode;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "CREATE_BY")
	private String createBy;
	
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@Column(name = "UPDATE_BY")
	private String updateBy;
	
	@Column(name = "DELETE_DATE")
	private Date deleteDate;
	
	@Column(name = "DELETE_BY")
	private String deleteBy;
	
	@Column(name = "NOTES")
	private String notes;
	
}
