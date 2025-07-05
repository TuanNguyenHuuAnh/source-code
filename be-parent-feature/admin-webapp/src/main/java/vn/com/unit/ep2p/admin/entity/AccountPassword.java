/*******************************************************************************
 * Class        :AccountPassword
 * Created date :2017/10/25
 * Lasted date  :2017/10/25
 * Author       :LongPNT
 * Change log   :2017/10/25:01-00 LongPNT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JCA_M_ACCOUNT_PASSWORD
 * @version 01-00
 * @since 01-00
 * @author LongPNT
 */
@Getter
@Setter
@Table(name = "JCA_ACCOUNT_PASSWORD")
public class AccountPassword extends AbstractTracking{

    /** Column: ID type NUMBER(22,0) NULL */
	@Id
	@Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_ACCOUNT_PASSWORD")
    private Long id;

    /** Column: ACCOUNT_ID type NUMBER(22,0) NULL */
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    /** Column: PASSWORD type NVARCHAR2(256,0) NULL */
    @Column(name = "PASSWORD")
    private String password;

    /** Column: EFFECTED_DATE type DATE(7,0) NULL */
    @Column(name = "EFFECTED_DATE")
    private Date effectedDate;

    /** Column: EXPIRED_DATE type DATE(7,0) NULL */
    @Column(name = "EXPIRED_DATE")
    private Date expiredDate;

    /** Column: FIRST_LOGIN type NUMBER(22,0) NULL */
    @Column(name = "FIRST_LOGIN")
    private Long firstLogin;

    /** Column: FIRST_LOGIN_DATE type DATE(7,0) NULL */
    @Column(name = "FIRST_LOGIN_DATE")
    private Date firstLoginDate;

}

