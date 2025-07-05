/*******************************************************************************
 * Class        ：IntroductionDto
 * Created date ：2017/02/22
 * Lasted date  ：2017/02/22
 * Author       ：thuydtn
 * Change log   ：2017/02/22：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * IntroductionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class IntroductionSearchDto{
    private String code;
    private String name;
    private String note;
    private String description;
    private String subTitle;
    private String keyWord;
    private String categoryName;
    private String categoryCode;
    private String title;
    private String shortContent;
    private String content;
    private Integer status;
    private Long categoryId;
    private String statusName;
    private Integer enabled;
    
    /**
     * Get code
     * @return String
     * @author thuydtn
     */
    public String getCode() {
        return code;
    }
    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * Get name
     * @return String
     * @author thuydtn
     */
    public String getName() {
        return name;
    }
    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get note
     * @return String
     * @author thuydtn
     */
    public String getNote() {
        return note;
    }
    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     * Get description
     * @return String
     * @author thuydtn
     */
    public String getDescription() {
        return description;
    }
    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Get subTitle
     * @return String
     * @author thuydtn
     */
    public String getSubTitle() {
        return subTitle;
    }
    /**
     * Set subTitle
     * @param   subTitle
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
    /**
     * Get keyWord
     * @return String
     * @author thuydtn
     */
    public String getKeyWord() {
        return keyWord;
    }
    /**
     * Set keyWord
     * @param   keyWord
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    /**
     * Get categoryName
     * @return String
     * @author thuydtn
     */
    public String getCategoryName() {
        return categoryName;
    }
    /**
     * Set categoryName
     * @param   categoryName
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    /**
     * Get title
     * @return String
     * @author thuydtn
     */
    public String getTitle() {
        return title;
    }
    /**
     * Set title
     * @param   title
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Get shortContent
     * @return String
     * @author thuydtn
     */
    public String getShortContent() {
        return shortContent;
    }
    /**
     * Set shortContent
     * @param   shortContent
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }
    /**
     * Get content
     * @return String
     * @author thuydtn
     */
    public String getContent() {
        return content;
    }
    /**
     * Set content
     * @param   content
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * Get categoryCode
     * @return String
     * @author thuydtn
     */
    public String getCategoryCode() {
        return categoryCode;
    }
    /**
     * Set categoryCode
     * @param   categoryCode
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the statusName
	 * @author taitm
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 * @author taitm
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * @return the enabled
	 * @author taitm
	 */
	public Integer getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 * @author taitm
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
    
}
