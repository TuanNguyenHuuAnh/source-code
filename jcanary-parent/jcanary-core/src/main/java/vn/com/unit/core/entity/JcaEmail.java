/*******************************************************************************
 * Class        ：JcaEmaiHistory
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
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
 * JcaEmaiHistory
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_EMAIL)
public class JcaEmail extends AbstractTracking {
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_EMAIL)
    private Long id;
    
    @Column(name = "SENDER_ADDRESS")
    private String senderAddress;
    
    @Column(name = "SENDER_NAME")
    private String senderName;
    
    @Column(name = "TO_STRING")
    private String toString;
    
    @Column(name = "CC_STRING")
    private String ccString;
    
    @Column(name = "BCC_STRING")
    private String bccString;
    
    @Column(name = "SEND_DATE")
    private Date sendDate;
    
    @Column(name = "SEND_STATUS")
    private String sendStatus;
    
    @Column(name = "SUBJECT")
    private String subject;
    
    @Column(name = "EMAIL_CONTENT")
    private String emailContent;
    
    @Column(name = "CONTENT_TYPE")
    private String contentType;
    
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;
    
    
}
