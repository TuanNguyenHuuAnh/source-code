/*******************************************************************************
 * Class        ：NewsTypeLanguageDto
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * NewsTypeLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@SuppressWarnings("deprecation")
public class NewsTypeLanguageDto {

    /** id */
    private Long id;

    /** languageCode */
    private String languageCode;

    /** label */
    @NotEmpty
    @Size(max = 255)
    private String label;
    
    /** description */
    private String description;
    
    private String keyWord;
    
    private String descriptionKeyword;
    
    private String linkAlias;
    
    /** imageName */
    private String imageName;

    /** physicalImg */
    private String physicalImg;

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
     * Get label
     * @return String
     * @author hand
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set label
     * @param   label
     *          type String
     * @return
     * @author  hand
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get description
     * @return String
     * @author hand
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  hand
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get keyWord
     * 
     * @return String
     * @author taitm
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * Set keyWord
     * 
     * @param keyWord
     *            type String
     * @return
     * @author taitm
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * Get descriptionKeyword
     * 
     * @return String
     * @author taitm
     */
    public String getDescriptionKeyword() {
        return descriptionKeyword;
    }

    /**
     * Set descriptionKeyword
     * 
     * @param descriptionKeyword
     *            type String
     * @return
     * @author taitm
     */
    public void setDescriptionKeyword(String descriptionKeyword) {
        this.descriptionKeyword = descriptionKeyword;
    }

    /**
     * Get linkAlias
     * 
     * @return String
     * @author taitm
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * Set linkAlias
     * 
     * @param linkAlias
     *            type String
     * @return
     * @author taitm
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    /**
     * @return the imageName
     * @author taitm
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     * @author taitm
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the physicalImg
     * @author taitm
     */
    public String getPhysicalImg() {
        return physicalImg;
    }

    /**
     * @param physicalImg
     *            the physicalImg to set
     * @author taitm
     */
    public void setPhysicalImg(String physicalImg) {
        this.physicalImg = physicalImg;
    }

}
