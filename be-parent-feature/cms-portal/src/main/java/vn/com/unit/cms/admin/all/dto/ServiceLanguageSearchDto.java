/*******************************************************************************
 * Class        ：TermDto
 * Created date ：2017/04/26
 * Lasted date  ：2017/04/26
 * Author       ：tungns <tungns@unit.com.vn>
 * Change log   ：2017/04/26：01-00 tungns <tungns@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

/**
 * TermDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns <tungns@unit.com.vn>
 */
public class ServiceLanguageSearchDto {		

	private Long id;
    private String code;    
	private String langCode;
	private String title;
	private String mCustomerTypeName;
	private String descriptionAbv;
	private String descriptionSlogan;	
    private Date createDate;
	private int sortOrder;
	private String name;
	private String note;
	private String imageUrl;
	private String imageName;
	private List<ServiceDetailDto> serviceDetailDtoList;
	
	private String customerTypesSearch;
	private String mCustomerTypeId;
	private int currentPage;
	private String url;
	
    /** fieldValues */
	private List<String> fieldValues;
	/** fieldSearch */
	private String fieldSearch;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String name) {
		this.title = name;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getDescriptionAbv() {
		return descriptionAbv;
	}
	public void setDescriptionAbv(String description) {
		this.descriptionAbv = description;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<String> getFieldValues() {
		return fieldValues;
	}
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}
	public String getFieldSearch() {
		return fieldSearch;
	}
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}	
	public String getmCustomerTypeId() {
		return mCustomerTypeId;
	}
	public void setmCustomerTypeId(String mCustomerTypeId) {
		this.mCustomerTypeId = mCustomerTypeId;
	}	
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCustomerTypesSearch() {
		return customerTypesSearch;
	}
	public void setCustomerTypesSearch(String customerTypesSearch) {
		this.customerTypesSearch = customerTypesSearch;
	}
	public String getmCustomerTypeName() {
		return mCustomerTypeName;
	}
	public void setmCustomerTypeName(String mCustomerTypeName) {
		this.mCustomerTypeName = mCustomerTypeName;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
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
     * Get serviceDetailDtoList
     * @return List<ServiceDetailDto>
     * @author tungns <tungns@unit.com.vn>
     */
    public List<ServiceDetailDto> getServiceDetailDtoList() {
        return serviceDetailDtoList;
    }
    /**
     * Set serviceDetailDtoList
     * @param   serviceDetailDtoList
     *          type List<ServiceDetailDto>
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setServiceDetailDtoList(
            List<ServiceDetailDto> serviceDetailDtoList) {
        this.serviceDetailDtoList = serviceDetailDtoList;
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
}
