package vn.com.unit.cms.core.module.emulate.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Getter
@Setter
@Table(name = "M_CONTEST_APPLICABLE_DETAIL")
public class ContestApplicableDetail extends AbstractTracking {
//	@Id
//	@Column(name = "id")
//	private Long id;
	
    @Column(name = "CONTEST_ID")
    private Long contestId;
	
    @Column(name = "TERRITORY")
    private String territory;
    
    @Column(name = "AREA")
    private String area;
    
    @Column(name = "REGION")
    private String region;
    
    @Column(name = "OFFICE")
    private String office;
    
    @Column(name = "POSITION")
    private String position;
    
    @Column(name = "AGENT_NAME")
    private String agentName;

    @Column(name = "AGENT_CODE")
    private String agentCode;

    @Column(name = "REPORTINGTO_CODE")
    private String reportingtoCode;

    @Column(name = "REPORTINGTO_NAME")
    private String reportingtoName;
    
    @Column(name = "IS_PERSON")
    private boolean isPerson;

    

	
}
