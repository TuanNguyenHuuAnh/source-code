/*******************************************************************************
 * Class        ：ECard
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：TaiTM
 * Change log   ：2017/04/18：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.ecard.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * ECard
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = "M_ECARD")
@Getter
@Setter
public class ECard extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_ECARD")
    private Long id;

    @Column(name = "m_customer_type_id")
    private Long customerTypeId;

    @Column(name = "code")
    private String code;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @Column(name = "AGENT_NAME")
    private Integer agentName;

    @Column(name = "AGENT_TYPE")
    private Integer agentType;

    @Column(name = "OFFICE")
    private Integer office;

    @Column(name = "PHONE")
    private Integer phone;

    @Column(name = "ZALO")
    private Integer zalo;

    @Column(name = "FACEBOOK")
    private Integer facebook;

    @Column(name = "EMAIL")
    private Integer email;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "SORT")
    private Long sort;

    @Column(name = "DOC_ID")
    private Long docId;

    @Column(name = "BACKGROUND")
    private String background;
    @Column(name = "LABEL")
    private Integer label;
    
    @Column(name = "CHANNEL")
    private String channel;
}
