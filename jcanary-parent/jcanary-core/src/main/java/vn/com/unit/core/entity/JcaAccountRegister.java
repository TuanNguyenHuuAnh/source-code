/*******************************************************************************
 * Class        :Account
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :SonND
 * Change log   :2020/12/01:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import java.util.Date;

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
 * Account
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_ACCOUNT_REGISTER)
public class JcaAccountRegister {

	/** Column: id type decimal(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  CoreConstant.TABLE_JCA_ACCOUNT_REGISTER)
    private Long id;
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    @Column(name = "FULLNAME")
    private String fullName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "REGISTER_DATE")
    private Date registerDate;
    @Column(name = "OFFICE_REG")
    private String officeReg;
    @Column(name = "OFFICE_NAME_REG")
    private String officeNameReg;
    @Column(name = "PROVINCE_REG")
    private String provinceReg;
    @Column(name = "IS_SEND_MAIL")
    private boolean sendMail;
    
}