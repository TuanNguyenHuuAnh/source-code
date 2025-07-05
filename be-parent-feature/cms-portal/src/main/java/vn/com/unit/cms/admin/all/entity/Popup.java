/*******************************************************************************
 * Class        ：Popup
 * Created date ：2017/10/12
 * Lasted date  ：2017/10/12
 * Author       ：TranLTH
 * Change log   ：2017/10/12：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * Popup
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "m_popup")
public class Popup extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_POPUP")
    private Long id;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "type")
    private String type;

    @Column(name = "effective_date")
    private Date effectiveDate;
    
    @Column(name = "expiry_date")
    private Date expiryDate;
    
    @Column(name = "active")
    private boolean active;
    
    @Column(name = "position_display")
    private String positionDisplay;
    
    @Column(name = "page_display")
    private String pageDisplay;
    
    /**
     * Get id
     * @return Long
     * @author TranLTH
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get code
     * @return String
     * @author TranLTH
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get type
     * @return String
     * @author TranLTH
     */
    public String getType() {
        return type;
    }

    /**
     * Set type
     * @param   type
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Get effectiveDate
     * @return Date
     * @author TranLTH
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Set effectiveDate
     * @param   effectiveDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    /**
     * Get active
     * @return Boolean
     * @author TranLTH
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Set active
     * @param   active
     *          type Boolean
     * @return
     * @author  TranLTH
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get expiryDate
     * @return Date
     * @author TranLTH
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Set expiryDate
     * @param   expiryDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    /**
     * Get positionDisplay
     * @return String
     * @author TranLTH
     */
    public String getPositionDisplay() {
        return positionDisplay;
    }

    /**
     * Set positionDisplay
     * @param   positionDisplay
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPositionDisplay(String positionDisplay) {
        this.positionDisplay = positionDisplay;
    }

    /**
     * Get pageDisplay
     * @return String
     * @author TranLTH
     */
    public String getPageDisplay() {
        return pageDisplay;
    }

    /**
     * Set pageDisplay
     * @param   pageDisplay
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPageDisplay(String pageDisplay) {
        this.pageDisplay = pageDisplay;
    }
}