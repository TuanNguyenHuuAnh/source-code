/*******************************************************************************
 * Class        ：DocumentTypeSelectionDto
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：thuydtn
 * Change log   ：2017/04/18：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

//import vn.com.unit.cms.admin.all.entity.DocumentType;

/**
 * DocumentTypeSelectionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentTypeSelectionDto {

    /** id */
    private Long id;
    private String name;

    /**
     * @param type
     */
    public DocumentTypeSelectionDto(DocumentTypeLanguageDto type) {
         this.id = type.getId();
         this.setName(type.getTitle());
    }

    /**
     * 
     */
    public DocumentTypeSelectionDto() {
        
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
}
