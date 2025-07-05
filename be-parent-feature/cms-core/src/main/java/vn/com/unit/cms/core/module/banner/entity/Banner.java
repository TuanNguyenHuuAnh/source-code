/*******************************************************************************
 * Class        ：JcaMBanner
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：hand
 * Change log   ：2017/02/14：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.banner.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
//import vn.com.unit.jcanary.entity.AbstractTracking;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * JcaMBanner
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_banner")
@Getter
@Setter
public class Banner extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_BANNER")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "note")
    private String note;

    @Column(name = "banner_type")
    private String bannerType;

    @Column(name = "BANNER_DEVICE")
    private String bannerDevice;

    @Column(name = "DOC_ID")
    private Long docId;

    @Column(name = "ENABLED")
    private boolean enabled;
    
    @Column(name = "CHANNEL")
    private String channel;
}
