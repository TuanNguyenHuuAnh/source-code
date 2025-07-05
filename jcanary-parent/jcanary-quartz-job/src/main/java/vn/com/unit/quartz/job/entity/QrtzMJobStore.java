/*******************************************************************************
 * Class        ：QrtzMJobStore
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
 * QrtzMJobStore
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Setter
@Getter
@Table(name = "QRTZ_M_JOB_STORE")
public class QrtzMJobStore  extends AbstractTracking{

    /** Column: ID type NUMBER(22,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_QRTZ_M_JOB_STORE")
    private Long id;

    /** Column: GROUP_CODE type NVARCHAR2(510,0) NOT NULL */
    @Column(name = "GROUP_CODE")
    private String groupCode;

    /** Column: STORE_NAME type NVARCHAR2(510,0) NOT NULL */
    @Column(name = "STORE_NAME")
    private String storeName;

    /** Column: EXEC_ORDER type NUMBER(22,0) NOT NULL */
    @Column(name = "EXEC_ORDER")
    private Long execOrder;

    /** Column: DESCRIPTION type NVARCHAR2(510,0) NULL */
    @Column(name = "DESCRIPTION")
    private String description;

    /** Column: VALIDFLAG type NUMBER(22,0) NOT NULL */
    @Column(name = "VALIDFLAG")
    private Long validflag;


    
}

