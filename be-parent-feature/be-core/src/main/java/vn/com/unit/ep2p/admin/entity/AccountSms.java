/*******************************************************************************
 * Class        :AccountPassword
 * Created date :2017/10/25
 * Lasted date  :2017/10/25
 * Author       :LongPNT
 * Change log   :2017/10/25:01-00 LongPNT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.entity;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import vn.com.unit.db.entity.AbstractTracking;

import java.util.Date;

/**
 * JCA_M_ACCOUNT_PASSWORD
 * @version 01-00
 * @since 01-00
 * @author LongPNT
 */
@Getter
@Setter
@Table(name = "JCA_ACCOUNT_OTP")
public class AccountSms{

    /** Column: ID type NUMBER(22,0) NULL */
	@Id
	@Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_ACCOUNT_OTP")
    private Long id;

    /** Column: ACCOUNT_ID type NUMBER(22,0) NULL */
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    /** Column: PASSWORD type NVARCHAR2(256,0) NULL */
    @Column(name = "OTP")
    private String otp;

    /** Column: EFFECTED_DATE type DATE(7,0) NULL */
    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;

    /** Column: EFFECTED_DATE type DATE(7,0) NULL */
    @Column(name = "SMS_COUNT")
    private Integer smsCount;
    @Column(name = "TYPE_OTP")
    private String typeOtp;
}

