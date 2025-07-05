/*******************************************************************************
 * Class        ：QrtzMJobType
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * <p>
 * QrtzMJobType
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = "QRTZ_M_JOB_TYPE")
public class QrtzMJobType extends AbstractTracking{
	
	/** Column: ID type NUMBER(20,0) NOT NULL. */
    @Id
    @Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_QRTZ_M_JOB_TYPE")
    private Long id;
    
    /** Column: TYPE type VARCHAR2(20,0) NULL. */
    @Column(name = "TYPE")
    private String type;

    /** Column: CODE type VARCHAR2(255,0) NULL. */
    @Column(name = "CODE")
    private String code;
    
    /** Column: OFFICIAL_NAME type VARCHAR2(255,0) NULL. */
    @Column(name = "OFFICIAL_NAME")
    private String officialName;
    
    /** Column: DESCRIPTION type NVARCHAR2(510,0) NULL. */
    @Column(name = "DESCRIPTION")
    private String description;
    
    /** Column: COMPANY_ID type NUMBER(20,0) NULL. */
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
}
