/*******************************************************************************
 * Class        ：ServiceDetailsDto
 * Created date ：2017/05/26
 * Lasted date  ：2017/05/26
 * Author       ：tungns <tungns@unit.com.vn>
 * Change log   ：2017/05/26：01-00 tungns <tungns@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * ServiceDetailsDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns <tungns@unit.com.vn>
 */
public class ServiceDetailDto {
    
    private Long id;
    private Long mServiceId;
    private String mLanguageCode;
    
    private String groupContent;
    
    private String content;
    private String keyContent;
    private String backgroundUrl;
    private String backgroundPhysical;

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
     * Get mServiceId
     * @return Long
     * @author tungns <tungns@unit.com.vn>
     */
    public Long getmServiceId() {
        return mServiceId;
    }

    /**
     * Set mServiceId
     * @param   mServiceId
     *          type Long
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setmServiceId(Long mServiceId) {
        this.mServiceId = mServiceId;
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
