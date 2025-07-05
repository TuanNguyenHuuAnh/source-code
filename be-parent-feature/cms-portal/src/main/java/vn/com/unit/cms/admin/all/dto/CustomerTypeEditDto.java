/*******************************************************************************
 * Class        ：CustomerTypeEditDto
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * CustomerTypeEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@SuppressWarnings("deprecation")
public class CustomerTypeEditDto {

    /** id */
    private Long id;

    /** code */
    @Size(min = 1, max = 30)
    @NotEmpty
    private String code;

    /** name */
    @Size(min = 1, max = 255)
    @NotEmpty
    private String name;

    /** description */
    private String description;

    /** note */
    private String note;

    /** sort */
    private Long sort;

    /** enabled */
    private boolean enabled;

    /** typeLanguageList */
    @Valid
    private List<CustomerTypeLanguageDto> typeLanguageList;

    /** url */
    private String url;

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
     * Get code
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  hand
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get name
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  hand
     */
    public void setName(String name) {
        this.name = name;
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
     * Get note
     * @return String
     * @author hand
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  hand
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get sort
     * @return Long
     * @author hand
     */
    public Long getSort() {
        return sort;
    }

    /**
     * Set sort
     * @param   sort
     *          type Long
     * @return
     * @author  hand
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get enabled
     * @return boolean
     * @author hand
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * @param   enabled
     *          type boolean
     * @return
     * @author  hand
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get typeLanguageList
     * @return List<CustomerTypeLanguageDto>
     * @author hand
     */
    public List<CustomerTypeLanguageDto> getTypeLanguageList() {
        return typeLanguageList;
    }

    /**
     * Set typeLanguageList
     * @param   typeLanguageList
     *          type List<CustomerTypeLanguageDto>
     * @return
     * @author  hand
     */
    public void setTypeLanguageList(List<CustomerTypeLanguageDto> typeLanguageList) {
        this.typeLanguageList = typeLanguageList;
    }

    /**
     * Get url
     * @return String
     * @author hand
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  hand
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
}
