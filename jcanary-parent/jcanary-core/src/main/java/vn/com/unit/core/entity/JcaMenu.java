/*******************************************************************************
 * Class        :JcaMenu
 * Created date :2020/12/08
 * Lasted date  :2020/12/08
 * Author       :SonND
 * Change log   :2020/12/08:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaMenu
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CoreConstant.TABLE_JCA_MENU)
public class JcaMenu extends AbstractTracking {

    // "ID" NUMBER(20,0) DEFAULT ("SEQ_JCA_MENU"."NEXTVAL") NOT NULL,
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_MENU)
    private Long id;
    
    // "CODE" VARCHAR2(100 CHAR) NOT NULL,
    @Column(name = "CODE")
    private String code;
    
    // "DISPLAY_ORDER" NUMBER(10,0),
    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;
    
    // "URL" VARCHAR2(255 CHAR),
    @Column(name = "URL")
    private String url;
    
    // "ITEM_ID" NUMBER(20,0),
    @Column(name = "ITEM_ID")
    private Long itemId;
    
    // "CLASS_NAME" VARCHAR2(50 CHAR),
    @Column(name = "CLASS_NAME")
    private String className;
    
    // "MENU_TYPE" NUMBER(1,0) NOT NULL,
    @Column(name = "MENU_TYPE")
    private Integer menuType;
    
    // "GROUP_FLAG" NUMBER(1,0),
    @Column(name = "GROUP_FLAG")
    private boolean groupFlag;
    
    // "STATUS" NUMBER(1,0),
    @Column(name = "STATUS")
    private Integer status;
    
    // "ACTIVED" NUMBER(20,0) NOT NULL,
    @Column(name = "ACTIVED")
    private boolean actived;
    
    // "COMPANY_ID" NUMBER(20,0) NOT NULL,
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
	@Column(name = "MENU_MODULE")
	private String menuModule;

    @Transient
    private Long parentId;

}