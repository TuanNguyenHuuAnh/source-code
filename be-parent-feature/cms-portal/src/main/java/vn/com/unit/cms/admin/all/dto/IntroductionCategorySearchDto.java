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
public class IntroductionCategorySearchDto{
    private String code;
    private String name;
    private String note;
    private String imageUrl;
    private String description;
    private String label;
    private Integer status;
    private String statusName;
    private Integer enabled;
    private Integer typeOfMain;
    private Integer pictureIntroduction;

    public IntroductionCategorySearchDto(){
        
    }
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
     * Get imageUrl
     * @return String
     * @author thuydtn
     */
    public String getImageUrl() {
        return imageUrl;
    }
    /**
     * Set imageUrl
     * @param   imageUrl
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
     * Get label
     * @return String
     * @author thuydtn
     */
    public String getLabel() {
        return label;
    }
    /**
     * Set label
     * @param   label
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setLabel(String label) {
        this.label = label;
    }
	public Integer getStatus() {
		return status;
	}

    public void setStatus(Integer status) {
        this.status = status;
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

    /**
     * @return the typeOfMain
     * @author taitm
     */
    public Integer getTypeOfMain() {
        return typeOfMain;
    }

    /**
     * @param typeOfMain
     *            the typeOfMain to set
     * @author taitm
     */
    public void setTypeOfMain(Integer typeOfMain) {
        this.typeOfMain = typeOfMain;
    }

    /**
     * @return the pictureIntroduction
     * @author taitm
     */
    public Integer getPictureIntroduction() {
        return pictureIntroduction;
    }
    
    /**
     * @param pictureIntroduction
     *            the pictureIntroduction to set
     * @author taitm
     */
    public void setPictureIntroduction(Integer pictureIntroduction) {
        this.pictureIntroduction = pictureIntroduction;
    }

}
