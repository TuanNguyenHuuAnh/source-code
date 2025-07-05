package vn.com.unit.cms.core.module.categorizeCustomer.entity;
import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;

/**
 * @author lmi.quan
 * SR16451 - Phrase 3 Tính năng phân loại khách hàng 
 * @createdDate 10/6/2024 
 */
@Table(name = CmsTableConstant.TABLE_CATEGORIZE_CUSTOMER)
@Getter
@Setter
public class CategorizeCustomer {
	@Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_CATEGORIZE_CUSTOMER)
    private Long id;
	
	@Column(name = "PROPOSAL_NO")
	private String proposalNo;
	
	@Column(name = "CLIENT_CODE")
	private String clientCode;
	
	@Column(name = "BUSINESS_CODE") // don vi kinh doanh
	private String businessCode;
	
	@Column(name = "AGENT_CODE")
	private String agentCode;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name= "CLIENT_OTHER")
	private String clientOther;
	
	@Column(name= "BUSINESS_OTHER")
	private String businessOther;

}
