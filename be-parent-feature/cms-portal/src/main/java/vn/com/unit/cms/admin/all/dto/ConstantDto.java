/*******************************************************************************
 * Class        ：ConstantDto
 * Created date ：2017/10/17
 * Lasted date  ：2017/10/17
 * Author       ：TranLTH
 * Change log   ：2017/10/17：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

/**
 * ConstantDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class ConstantDto {
    private Long id;
    private String code;
    private String type;    
    @Valid
    private List<ConstantLanguageDto> constantLanguageDtos;
    private Date createDate;
    private String url;
    private String languageCode;
    private String name;
    private String typeName;
    private Boolean checkUpdateDelete;
    /**
     * Get id
     * @return Long
     * @author TranLTH
     */
    public Long getId() {
        return id;
    }
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Get code
     * @return String
     * @author TranLTH
     */
    public String getCode() {
        return code;
    }
    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * Get type
     * @return String
     * @author TranLTH
     */
    public String getType() {
        return type;
    }
    /**
     * Set type
     * @param   type
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Get constantLanguageDtos
     * @return List<ConstantLanguageDto>
     * @author TranLTH
     */
    public List<ConstantLanguageDto> getConstantLanguageDtos() {
        return constantLanguageDtos;
    }
    /**
     * Set constantLanguageDtos
     * @param   constantLanguageDtos
     *          type List<ConstantLanguageDto>
     * @return
     * @author  TranLTH
     */
    public void setConstantLanguageDtos(List<ConstantLanguageDto> constantLanguageDtos) {
        this.constantLanguageDtos = constantLanguageDtos;
    }
    /**
     * Get createDate
     * @return Date
     * @author TranLTH
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * Set createDate
     * @param   createDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * Get url
     * @return String
     * @author TranLTH
     */
    public String getUrl() {
        return url;
    }
    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Get languageCode
     * @return String
     * @author TranLTH
     */
    public String getLanguageCode() {
        return languageCode;
    }
    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
    /**
     * Get name
     * @return String
     * @author TranLTH
     */
    public String getName() {
        return name;
    }
    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get typeName
     * @return String
     * @author TranLTH
     */
    public String getTypeName() {
        return typeName;
    }
    /**
     * Set typeName
     * @param   typeName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    /**
     * Get checkUpdateDelete
     * @return Boolean
     * @author TranLTH
     */
    public Boolean getCheckUpdateDelete() {
        return checkUpdateDelete;
    }
    /**
     * Set checkUpdateDelete
     * @param   checkUpdateDelete
     *          type Boolean
     * @return
     * @author  TranLTH
     */
    public void setCheckUpdateDelete(Boolean checkUpdateDelete) {
        this.checkUpdateDelete = checkUpdateDelete;
    }
}