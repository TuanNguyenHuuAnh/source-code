/*******************************************************************************
 * Class        ：News
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：TaiTM
 * Change log   ：2017/02/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.emulate.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * News
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = CmsTableConstant.TABLE_EMULATE)
@Getter
@Setter
public class Emulate extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_TABLE_EMULATE)
    private Long id;

    @Column(name = "M_CUSTOMER_TYPE_ID")
    private Long customerTypeId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "TYPE")
    private int type;

    @Column(name = "MEMO_CODE")
    private String memoCode;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "HOT")
    private boolean hot;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @Column(name = "POSTED_DATE")
    private Date postedDate;
    
    @Column(name = "DOC_ID")
    private Long docId;
    
    @Column(name = "LINK_DMS")
    private boolean linkDms;
    
    @Column(name = "SUBJECTS_APPLICABLE")
    private int subjectsApplicable;
    
    @Column(name = "FC")
    private boolean fc;

    @Column(name = "AGENT_TYPE")
    private String agentType;

    @Column(name = "TERRITORY")
    private String territory;

    @Column(name = "AREA")
    private String area;

    @Column(name = "REGION")
    private String region;

    @Column(name = "OFFICE")
    private String office;
    
    @Column(name = "AGENT_CODE")
    private String agentCode;
    
    @Column(name = "position")
    private String position;
    
    
    @Column(name = "reportingto_Code")
    private String reportingtoCode;
    
    @Column(name = "CHANNEL")
    private String channel;
}
