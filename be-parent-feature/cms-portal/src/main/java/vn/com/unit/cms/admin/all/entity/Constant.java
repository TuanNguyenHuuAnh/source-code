/*******************************************************************************
 * Class        ：Constant
 * Created date ：2017/10/17
 * Lasted date  ：2017/10/17
 * Author       ：TranLTH
 * Change log   ：2017/10/17：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * Constant
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "m_constant")
public class Constant extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONSTANT")
    private Long id;

    @Column(name = "code")
    private String code;
    
    @Column(name = "type")
    private String type;

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
}