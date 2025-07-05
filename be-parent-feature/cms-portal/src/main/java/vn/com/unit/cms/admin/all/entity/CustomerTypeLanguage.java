/*******************************************************************************
 * Class        ：CustomerTypeLanguage
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * CustomerTypeLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_customer_type_language")
public class CustomerTypeLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CUSTOMER_TYPE_LANGUAGE")
    private Long id;

    @Column(name = "m_customer_type_id")
    private Long customerTypeId;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "title")
    private String title;

    /**
     * Get id
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  hand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get customerTypeId
     * @return Long
     * @author hand
     */
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    /**
     * Set customerTypeId
     * @param   customerTypeId
     *          type Long
     * @return
     * @author  hand
     */
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    /**
     * Get languageCode
     * @return String
     * @author hand
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  hand
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get title
     * @return String
     * @author hand
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * @param   title
     *          type String
     * @return
     * @author  hand
     */
    public void setTitle(String title) {
        this.title = title;
    }

}