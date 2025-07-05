package vn.com.unit.ep2p.admin.entity;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "M_CONTEST_APPLICABLE_DETAIL")
public class ContestApplicableDetailAdmin {

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
