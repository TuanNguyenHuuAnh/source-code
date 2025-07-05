/*******************************************************************************
 * Class        ：FaqsCategoryLanguageDto
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * FaqsCategoryLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@SuppressWarnings("deprecation")
public class FaqsTypeLanguageDto {

    /** id */
    private Long id;

    /** faqsId */
    private Long typeId;

    /** languageCode */
    private String languageCode;
        
    private String keyWord;
    
    private String keyWordDescription;
    
    private String linkAlias;

    /** title */
    @NotEmpty
    @Size(max = 255)
    private String title;

    /**
     * Get id
     * 
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * 
     * @param id
     *            type Long
     * @return
     * @author hand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get typeId
     * 
     * @return Long
     * @author hand
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * Set typeId
     * 
     * @param typeId
     *            type Long
     * @return
     * @author hand
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * Get languageCode
     * 
     * @return String
     * @author hand
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * 
     * @param languageCode
     *            type String
     * @return
     * @author hand
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get title
     * 
     * @return String
     * @author hand
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * 
     * @param title
     *            type String
     * @return
     * @author hand
     */
    public void setTitle(String title) {
        this.title = title;
    }

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getKeyWordDescription() {
		return keyWordDescription;
	}

	public void setKeyWordDescription(String keyWordDescription) {
		this.keyWordDescription = keyWordDescription;
	}

	public String getLinkAlias() {
		return linkAlias;
	}

	public void setLinkAlias(String linkAlias) {
		this.linkAlias = linkAlias;
	}
    
    

}
