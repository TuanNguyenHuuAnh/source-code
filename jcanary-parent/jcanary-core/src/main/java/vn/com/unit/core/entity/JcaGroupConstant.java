/*******************************************************************************
 * Class        :JcaGroupConstant
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :tantm
 * Change log   :2020/12/01:01-00 tantm create a new
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

/**
 * 
 * JcaGroupConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_GROUP_CONSTANT)
public class JcaGroupConstant{
    /** Column: CODE type NVARCHAR2(30) NULL */
	@Id
	@PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "CODE")
    private String code;
    @Column(name="DISPLAY_ORDER")
    private Long displayOrder;
    @Column(name="NAME")
    private String name;
	@Id
	@PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name="LANG_ID")
    private Long langId;
    @Column(name="LANG_CODE")
    private String langCode;
    @Column(name="ACTIVED")
    private Integer actived;
    @Column(name="CREATED_DATE")
    private Date createdDate;
    @Column(name="CREATED_ID")
    private Long createdId;
    @Column(name="UPDATED_DATE")
    private Date updatedDate;
    @Column(name="UPDATED_ID")
    private Long updatedId;

}