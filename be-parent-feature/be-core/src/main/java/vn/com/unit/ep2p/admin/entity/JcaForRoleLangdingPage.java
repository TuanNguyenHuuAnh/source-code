package vn.com.unit.ep2p.admin.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;

@Getter
@Setter
@Table(name = "JCA_ROLE_FOR_LANDING_PAGE")
public class JcaForRoleLangdingPage extends AbstractCreatedTracking{
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_ROLE_FOR_LANDING_PAGE")
    private Long id;
    
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "MENU_ID")
    private Long menuId;
    
    @Column(name = "COMPANY_ID")
    private Long companyId;
}
