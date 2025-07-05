/*******************************************************************************
 * Class        ：OZDocViewTracking
 * Created date ：2019/12/17
 * Lasted date  ：2019/12/17
 * Author       ：KhuongTH
 * Change log   ：2019/12/17：01-00 KhuongTH create a new
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
 * OZDocViewTracking
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_EFO_OZ_DOC_VIEW_TRACKING)
public class EfoOzDocViewTracking extends AbstractTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  CoreConstant.TABLE_EFO_OZ_DOC_VIEW_TRACKING)
    private Long id;

    /** Column: DOC_ID type NUMBER (20,0) NULL */
    @Column(name = "DOC_ID")
    private Long docId;

    /** Column: USERNAME type NVARCHAR2 (255) NULL */
    @Column(name = "USERNAME")
    private String username;

    /** Column: USER_ID type NUMBER (20,0) NULL */
    @Column(name = "USER_ID")
    private Long userId;

    /** Column: FIRST_VIEW_TIME type DATE NULL */
    @Column(name = "FIRST_VIEW_TIME")
    private Date firstViewTime;

    /** Column: LAST_VIEW_TIME type DATE NULL */
    @Column(name = "LAST_VIEW_TIME")
    private Date lastViewTime;

    /** Column: TOTAL_VIEW type NUMBER (10,0) NULL */
    @Column(name = "TOTAL_VIEW")
    private Long totalView;

}
