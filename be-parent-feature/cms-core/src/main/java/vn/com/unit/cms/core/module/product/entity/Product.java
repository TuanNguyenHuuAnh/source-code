/*******************************************************************************
 * Class        ：Product
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.product.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * Product
 * 
 * @version 01-00
 * @since 01-00
 * @author kieuquan
 */
@Table(name = "M_PRODUCT")
@Getter
@Setter
public class Product extends AbstractTracking {

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_PRODUCT")
    private Long id;

    @Column(name = "CODE")
    private String code;
    
    @Column(name = "PRODUCT_NAME")
    private String productName;
    
    @Column(name = "PRODUCT_TYPE")
    private String productType;
    
    @Column(name = "UNIT_PRICE")
    private Float unitPrice;
    
    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;
    
    @Column(name = "EXPIRED_DATE")
    private Date expiredDate;
    
    @Column(name = "PRODUCT_IMG")
    private String productImg;
    
    @Column(name = "PRODUCT_PHYSICAL_IMG")
    private String productPhysicalImg;

}