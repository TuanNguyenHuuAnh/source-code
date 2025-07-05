/*******************************************************************************
 * Class        ：QrtzMJobClass
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
 * QrtzMJobClass
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = "QRTZ_M_JOB_CLASS")
public class QrtzMJobClass extends AbstractTracking{
	
	/** Column: ID type NUMBER(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_QRTZ_M_JOB_CLASS")
    private Long id;
    
    /** Column: JOB_TYPE type NUMBER(20,0) NOT NULL */
    @Column(name = "JOB_TYPE_ID")
    private Long jobTypeId;
    
    /** Column: PATH_CLASS type VARCHAR2(255,0) NULL */
    @Column(name = "PATH_CLASS")
    private String pathClass;
    
    /** Column: NAME_CLASS type VARCHAR2(255,0) NULL */
    @Column(name = "NAME_CLASS")
    private String nameClass;
    
    /** Column: DESCRIPTION type NVARCHAR2(510,0) NULL */
    @Column(name = "DESCRIPTION")
    private String description;
    
    /** Column: COMPANY_ID type NUMBER(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
}
