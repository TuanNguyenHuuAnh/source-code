/*******************************************************************************
 * Class        RoleListDto
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

/**
 * RoleListDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class RoleListDto extends AbstractCompanyDto {

    /** id */
    private Long id;
    
    /** name */
    private String name;
    
    private String code;
    
    /** description */
    private String description;
    
    /** statusCode */
    private String statusCode;
    
    /** roleType */
    private String roleType;

    /**
     * Get id
     * @return Long
     * @author KhoaNA
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get name
     * @return String
     * @author KhoaNA
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get description
     * @return String
     * @author KhoaNA
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get statusCode
     * @return String
     * @author KhoaNA
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode
     * @param   statusCode
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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
     * Get roleType
     * @return String
     * @author HungHT
     */
    public String getRoleType() {
        return roleType;
    }

    
    /**
     * Set roleType
     * @param   roleType
     *          type String
     * @return
     * @author  HungHT
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
