/*******************************************************************************
 * Class        ：JcaDeviceToken
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.core.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;


/**
 * <p>
 * JcaDeviceToken
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = "JCA_DEVICE_TOKEN")
public class JcaDeviceToken {
    
    /** The id. */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  CoreConstant.TABLE_JCA_ACCOUNT)
    private Long id;
    
    /** The account id. */
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    
    /** The device token. */
    @Column(name = "DEVICE_TOKEN")
    private String deviceToken;
    
    /** The created by. */
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    /** The created date. */
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    /** The updated by. */
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    
    /** The updated date. */
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
}
