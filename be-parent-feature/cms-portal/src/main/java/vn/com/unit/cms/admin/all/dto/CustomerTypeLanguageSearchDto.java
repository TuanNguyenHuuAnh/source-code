/*******************************************************************************
 * Class        ：CustomerTypeLanguageSearchDto
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
 * CustomerTypeLanguageSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class CustomerTypeLanguageSearchDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** name */
    private String name;

    /** title */
    private String title;

    /** sort */
    private Long sort;

    /** enabled */
    private int enabled;

    /** description */
    private String description;

    /** createDate */
    private Date createDate;

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
     * @return int
     * @author hand
     */
    public int getEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * @param   enabled
     *          type int
     * @return
     * @author  hand
     */
    public void setEnabled(int enabled) {
        this.enabled = enabled;
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
     * Get createDate
     * @return Date
     * @author hand
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * @param   createDate
     *          type Date
     * @return
     * @author  hand
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
