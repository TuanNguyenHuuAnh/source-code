/*******************************************************************************
 * Class        ：ServiceDetail
 * Created date ：2017/05/29
 * Lasted date  ：2017/05/29
 * Author       ：tungns <tungns@unit.com.vn>
 * Change log   ：2017/05/29：01-00 tungns <tungns@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;

/**
 * ServiceDetail
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns <tungns@unit.com.vn>
 */
@Table( name = "m_service_detail")
public class ServiceDetail {
    
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_SERVICE_DETAIL")
    private Long id;
    
    @Column(name = "m_service_id")
    private Long serviceId;
    @Column(name = "m_language_code")
    private String mLanguageCode;       
    @Column(name = "content")
    private String content;
    @Column(name = "key_content")
    private String keyContent;
    @Column(name = "background_url")
    private String backgroundUrl;
    @Column(name = "background_physical")
    private String backgroundPhysical;
    @Column(name = "group_content")
    private String groupContent;
    /**
     * Get id
     * @return Long
     * @author tungns <tungns@unit.com.vn>
     */
    public Long getId() {
        return id;
    }
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Get serviceId
     * @return Long
     * @author tungns <tungns@unit.com.vn>
     */
    public Long getServiceId() {
        return serviceId;
    }
    /**
     * Set serviceId
     * @param   serviceId
     *          type Long
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    /**
     * Get mLanguageCode
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getmLanguageCode() {
        return mLanguageCode;
    }
    /**
     * Set mLanguageCode
     * @param   mLanguageCode
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setmLanguageCode(String mLanguageCode) {
        this.mLanguageCode = mLanguageCode;
    }
    /**
     * Get content
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getContent() {
        return content;
    }
    /**
     * Set content
     * @param   content
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * Get keyContent
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getKeyContent() {
        return keyContent;
    }
    /**
     * Set keyContent
     * @param   keyContent
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setKeyContent(String keyContent) {
        this.keyContent = keyContent;
    }
    /**
     * Get backgroundPhysical
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getBackgroundPhysical() {
        return backgroundPhysical;
    }
    /**
     * Set backgroundPhysical
     * @param   backgroundPhysical
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setBackgroundPhysical(String backgroundPhysical) {
        this.backgroundPhysical = backgroundPhysical;
    }
    /**
     * Get backgroundUrl
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getBackgroundUrl() {
        return backgroundUrl;
    }
    /**
     * Set backgroundUrl
     * @param   backgroundUrl
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }
    /**
     * Get groupContent
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getGroupContent() {
        return groupContent;
    }
    /**
     * Set groupContent
     * @param   groupContent
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setGroupContent(String groupContent) {
        this.groupContent = groupContent;
    }
}
