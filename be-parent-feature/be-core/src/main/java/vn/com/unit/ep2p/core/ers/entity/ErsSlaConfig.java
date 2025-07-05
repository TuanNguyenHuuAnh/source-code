package vn.com.unit.ep2p.core.ers.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name="ERS_SLA_CONFIG")
public class ErsSlaConfig extends AbstractTracking{
    
    @Id
    @PrimaryKey(generationType =GenerationType.SEQUENCE, generator = "SEQ_ERS_SLA_CONFIG")
    @Column(name = "ID")
    private Long id;    
    
    @Column(name="CHANNEL")
    private String channel;
    
    @Column(name="PROCESS_STATUS_CODE")
    private String processStatusCode;
    
    @Column(name="PROCESS_STATUS")
    private String processStatus;
    
    @Column(name="MAIL_TYPE")
    private String mailType;
    
    @Column(name="DUE_DATE")
    private Integer dueDate;
    
    @Column(name="REMIDER_BEFORE")
    private Integer reminderBefore;
    
    @Column(name="STATUS_SLA")
    private String statusSla;

}