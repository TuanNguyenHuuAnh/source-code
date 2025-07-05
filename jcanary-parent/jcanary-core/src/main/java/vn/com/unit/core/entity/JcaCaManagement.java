/*******************************************************************************
 * Class        ：JcaCaManagement
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaCaManagement
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Table(name = CoreConstant.TABLE_JCA_ACCOUNT_CA)
@Setter
@Getter
public class JcaCaManagement extends AbstractTracking {

    /** Column: ID type NUMBER(22,20) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_ACCOUNT_CA)
    private Long id;

    /** Column: ACCOUNT_ID type NUMBER(22,20) NOT NULL */
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    /** Column: STORE_TYPE type NUMBER(1,0) NULL */
    @Column(name = "STORE_TYPE")
    private int storeType;

    /** Column: CA_SLOT type VARCHAR2(255) NOT NULL */
    @Column(name = "CA_SLOT")
    private String caSlot;

    /** Column: CA_PASSWORD type VARCHAR2(255) NULL */
    @Column(name = "CA_PASSWORD")
    private String caPassword;

    /** Column: CA_LABEL type VARCHAR2(255) NULL */
    @Column(name = "CA_LABEL")
    private String caLabel;

    /** Column: CA_SERIAL type VARCHAR2(255) NULL */
    @Column(name = "CA_SERIAL")
    private String caSerial;

    /** Column: CA_NAME type NVARCHAR2(510) NULL */
    @Column(name = "CA_NAME")
    private String caName;

    /** Column: CA_DEFAULT type NUMBER(1,0) NULL */
    @Column(name = "CA_DEFAULT")
    private boolean caDefault;

    /** Column: COMPANY_ID type NUMBER(22,20) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
}
