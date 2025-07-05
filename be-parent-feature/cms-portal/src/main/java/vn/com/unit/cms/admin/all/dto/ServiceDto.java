package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * ServiceDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns <tungns@unit.com.vn>
 */
public class ServiceDto {

    private Long id;    
    private String mCustomerTypeId;
    private String code;    
    private String name;    
    private String descriptionAbv;
    private String descriptionSlogan;
    private String note;    
    private int sortOrder;
    private String imageUrl;
    private String imageName;
    private String url;
    private String requestToken;
    private String imagePhysicalName;
    private List<ServiceLanguageDto> serviceLanguageList;
    private List<Long> customerTypeIDs;
    private List<ServiceDetailLanguageDto> serviceDetailLanguageDtoList;
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
     * Get mCustomerTypeId
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getmCustomerTypeId() {
        return mCustomerTypeId;
    }
    /**
     * Set mCustomerTypeId
     * @param   mCustomerTypeId
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setmCustomerTypeId(String mCustomerTypeId) {
        this.mCustomerTypeId = mCustomerTypeId;
    }
    /**
     * Get code
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getCode() {
        return code;
    }
    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * Get name
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getName() {
        return name;
    }
    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get descriptionAbv
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getDescriptionAbv() {
        return descriptionAbv;
    }
    /**
     * Set descriptionAbv
     * @param   descriptionAbv
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setDescriptionAbv(String descriptionAbv) {
        this.descriptionAbv = descriptionAbv;
    }
    /**
     * Get descriptionSlogan
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getDescriptionSlogan() {
        return descriptionSlogan;
    }
    /**
     * Set descriptionSlogan
     * @param   descriptionSlogan
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setDescriptionSlogan(String descriptionSlogan) {
        this.descriptionSlogan = descriptionSlogan;
    }
    /**
     * Get note
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getNote() {
        return note;
    }
    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     * Get sortOrder
     * @return int
     * @author tungns <tungns@unit.com.vn>
     */
    public int getSortOrder() {
        return sortOrder;
    }
    /**
     * Set sortOrder
     * @param   sortOrder
     *          type int
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    /**
     * Get imageUrl
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getImageUrl() {
        return imageUrl;
    }
    /**
     * Set imageUrl
     * @param   imageUrl
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    /**
     * Get imageName
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getImageName() {
        return imageName;
    }
    /**
     * Set imageName
     * @param   imageName
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    /**
     * Get url
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getUrl() {
        return url;
    }
    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Get requestToken
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getRequestToken() {
        return requestToken;
    }
    /**
     * Set requestToken
     * @param   requestToken
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
    /**
     * Get imagePhysicalName
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getImagePhysicalName() {
        return imagePhysicalName;
    }
    /**
     * Set imagePhysicalName
     * @param   imagePhysicalName
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setImagePhysicalName(String imagePhysicalName) {
        this.imagePhysicalName = imagePhysicalName;
    }
    /**
     * Get serviceLanguageList
     * @return List<ServiceLanguageDto>
     * @author tungns <tungns@unit.com.vn>
     */
    public List<ServiceLanguageDto> getServiceLanguageList() {
        return serviceLanguageList;
    }
    /**
     * Set serviceLanguageList
     * @param   serviceLanguageList
     *          type List<ServiceLanguageDto>
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setServiceLanguageList(
            List<ServiceLanguageDto> serviceLanguageList) {
        this.serviceLanguageList = serviceLanguageList;
    }
    /**
     * Get customerTypeIDs
     * @return List<Long>
     * @author tungns <tungns@unit.com.vn>
     */
    public List<Long> getCustomerTypeIDs() {
        return customerTypeIDs;
    }
    /**
     * Set customerTypeIDs
     * @param   customerTypeIDs
     *          type List<Long>
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setCustomerTypeIDs(List<Long> customerTypeIDs) {
        this.customerTypeIDs = customerTypeIDs;
    }
    /**
     * Get serviceDetailLanguageDtoList
     * @return List<ServiceDetailLanguageDto>
     * @author tungns <tungns@unit.com.vn>
     */
    public List<ServiceDetailLanguageDto> getServiceDetailLanguageDtoList() {
        return serviceDetailLanguageDtoList;
    }
    /**
     * Set serviceDetailLanguageDtoList
     * @param   serviceDetailLanguageDtoList
     *          type List<ServiceDetailLanguageDto>
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setServiceDetailLanguageDtoList(
            List<ServiceDetailLanguageDto> serviceDetailLanguageDtoList) {
        this.serviceDetailLanguageDtoList = serviceDetailLanguageDtoList;
    }
    
    
}
