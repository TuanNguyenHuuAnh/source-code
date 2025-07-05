/*******************************************************************************
 * Class        ：Faqs
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import javax.persistence.Lob;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * Faqs
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = "M_CONTEST_DETAIL_IMPORT")
@Getter
@Setter
public class EmulateResultImport  {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONTEST_DETAIL_IMPORT")
    private Long id;

    @Column(name = "MEMO_NO")
    private String memoNo;

    @Column(name = "AGENT_CODE")
    private String agentCode;

    @Column(name = "AGENT_NAME")
    private String agentName;

    @Column(name = "AGENT_TYPE")
    private String agentType;
    
    @Column(name = "REPORTINGTO_CODE")
    private String reportingtoCode;

    @Column(name = "REPORTINGTO_NAME")
    private String reportingtoName;
    
    @Column(name = "REPORTINGTO_Type")
    private String reportingtoType;

    @Column(name = "BM_CODE")
    private String bmCode;

    @Column(name = "BM_NAME")
    private String bmName;

    @Column(name = "BM_TYPE")
    private String bmType;
    
    @Column(name = "GAD_CODE")
    private String gadCode;

    @Column(name = "GAD_NAME")
    private String gadName;
    
    @Column(name = "GA_CODE")
    private String gaCode;
    
    @Column(name = "GA_NAME")
    private String gaName;

    @Column(name = "BDOH_CODE")
    private String bdohCode;
    
    @Column(name = "BDOH_NAME")
    private String bdohName;
    
    @Column(name = "BDRH_CODE")
    private String bdrhCode;
    
    @Column(name = "BDRH_NAME")
    private String bdrhName;
    
    @Column(name = "REGION")
    private String region;
    
    @Column(name = "BDAH_CODE")
    private String bdahCode;
    
    @Column(name = "BDAH_NAME")
    private String bdahName;
    
    @Column(name = "AREA")
    private String area;
    
    @Column(name = "BDTH_CDE")
    private String bdthCde;
    
    @Column(name = "BDTH_NAME")
    private String bdthName;
    
    @Column(name = "TERRITORY")
    private String territory;
    
    @Column(name = "POLICY_NO")
    private String policyNo;
    
    @Column(name = "APPLIED_DATE")
    private Date appliedDate;
    
    @Column(name = "ISSUED_DATE")
    private Date issuedDate;
    
    @Column(name = "RESULT")
    private String result;
    
    @Column(name = "ADVANCE")
    private String advance;

    @Column(name = "BONUS")
    private String bonus;
    
    @Column(name = "CLAWBACK")
    private String clawback;
    
    @Column(name = "NOTE")
    private String note;
    
    @Column(name = "IS_ERROR")
    private boolean isError;
    
    @Column(name = "MESSAGE_ERROR")
    private String messageError;
    
    @Column(name = "STATUS")
    private int status;
    
    @Column(name = "SESSION_KEY")
    private String sessionKey;

    
}