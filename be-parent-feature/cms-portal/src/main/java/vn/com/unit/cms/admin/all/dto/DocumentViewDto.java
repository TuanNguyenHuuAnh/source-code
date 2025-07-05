/*******************************************************************************
 * Class        ：DocumentViewDto
 * Created date ：2017/05/15
 * Lasted date  ：2017/05/15
 * Author       ：thuydtn
 * Change log   ：2017/05/15：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * DocumentViewDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentViewDto {
    private String title;
    private Long categoryId;
    private String fileUrl;
    private String imageUrl;
    private Long id;
    private String categoryTitle;
    
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
     * Get categoryId
     * @return Long
     * @author thuydtn
     */
    public Long getCategoryId() {
        return categoryId;
    }
    /**
     * Set categoryId
     * @param   categoryId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    /**
     * Get fileUrl
     * @return String
     * @author thuydtn
     */
    public String getFileUrl() {
        return fileUrl;
    }
    /**
     * Set fileUrl
     * @param   fileUrl
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    /**
     * Get id
     * @return Long
     * @author thuydtn
     */
    public Long getId() {
        return id;
    }
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setId(Long id) {
        this.id = id;
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
     * Get categoryTitle
     * @return String
     * @author thuydtn
     */
    public String getCategoryTitle() {
        return categoryTitle;
    }
    /**
     * Set categoryTitle
     * @param   categoryTitle
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
