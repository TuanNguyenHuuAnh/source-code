/*******************************************************************************
 * Class        ：ConstantLanguage
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
 * ConstantLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "m_constant_language")
public class ConstantLanguage extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONSTANT_LANGUAGE")
    private Long id;

    @Column(name = "m_constant_code")
    private String constantCode;
    
    @Column(name = "m_language_code")
    private String languageCode;
    
    @Column(name = "name")
    private String name;

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
     * Get constantCode
     * @return String
     * @author TranLTH
     */
    public String getConstantCode() {
        return constantCode;
    }

    /**
     * Set constantCode
     * @param   constantCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setConstantCode(String constantCode) {
        this.constantCode = constantCode;
    }

    /**
     * Get languageCode
     * @return String
     * @author TranLTH
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get name
     * @return String
     * @author TranLTH
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setName(String name) {
        this.name = name;
    }
}