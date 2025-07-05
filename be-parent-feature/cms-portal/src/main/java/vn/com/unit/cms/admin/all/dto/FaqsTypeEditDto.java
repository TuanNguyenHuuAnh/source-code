/*******************************************************************************
 * Class        ：FaqsTypeEditDto
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：hand
 * Change log   ：2017/03/19：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import javax.validation.constraints.Size;

import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * FaqsTypeEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class FaqsTypeEditDto {

    /** id */
    private Long id;

    /** code */

    private String code;

    /** name */
    @Size(min = 1, max = 255)
    private String name;

    /** description */
    private String description;

    /** note */
    private String note;

    /** sort */
    private Long sort;

    /** enabled */
    private boolean enabled;

    /** FaqsLanguageDtoList */
    private List<FaqsTypeLanguageDto> typeFaqsLanguageList;

    /** pageUrl */
    private String pageUrl;
    private String linkAlias;

    private Long customerTypeId;

    private List<SortOrderDto> sortOderList;

    private String approveBy;

    private String publishBy;

    private String createBy;

    private String status;

    private Long beforeId;

    private List<FaqsTypeEditDto> lstFaqsTypeToSort;

    private Integer indexLangActive;

    private String searchDto;

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
     * Get code
     * 
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * 
     * @param code
     *            type String
     * @return
     * @author hand
     */
    public void setCode(String code) {
        this.code = CmsUtils.toUppercase(code);
    }

    /**
     * Get name
     * 
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * 
     * @param name
     *            type String
     * @return
     * @author hand
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get description
     * 
     * @return String
     * @author hand
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * 
     * @param description
     *            type String
     * @return
     * @author hand
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get note
     * 
     * @return String
     * @author hand
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * 
     * @param note
     *            type String
     * @return
     * @author hand
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get sort
     * 
     * @return Long
     * @author hand
     */
    public Long getSort() {
        return sort;
    }

    /**
     * Set sort
     * 
     * @param sort
     *            type Long
     * @return
     * @author hand
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get enabled
     * 
     * @return boolean
     * @author hand
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * 
     * @param enabled
     *            type boolean
     * @return
     * @author hand
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get typeFaqsLanguageList
     * 
     * @return List<FaqsTypeLanguageDto>
     * @author hand
     */
    public List<FaqsTypeLanguageDto> getTypeFaqsLanguageList() {
        return typeFaqsLanguageList;
    }

    /**
     * Set typeFaqsLanguageList
     * 
     * @param typeFaqsLanguageList
     *            type List<FaqsTypeLanguageDto>
     * @return
     * @author hand
     */
    public void setTypeFaqsLanguageList(List<FaqsTypeLanguageDto> typeFaqsLanguageList) {
        this.typeFaqsLanguageList = typeFaqsLanguageList;
    }

    /**
     * Get pageUrl
     * 
     * @return String
     * @author hand
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * Set pageUrl
     * 
     * @param pageUrl
     *            type String
     * @return
     * @author hand
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * Get linkAlias
     * 
     * @return String
     * @author TranLTH
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
     * @author TranLTH
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public List<SortOrderDto> getSortOderList() {
        return sortOderList;
    }

    public void setSortOderList(List<SortOrderDto> sortOderList) {
        this.sortOderList = sortOderList;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    public String getPublishBy() {
        return publishBy;
    }

    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBeforeId() {
        return beforeId;
    }

    public void setBeforeId(Long beforeId) {
        this.beforeId = beforeId;
    }

    public List<FaqsTypeEditDto> getLstFaqsTypeToSort() {
        return lstFaqsTypeToSort;
    }

    public void setLstFaqsTypeToSort(List<FaqsTypeEditDto> lstFaqsTypeToSort) {
        this.lstFaqsTypeToSort = lstFaqsTypeToSort;
    }

    public Integer getIndexLangActive() {
        return indexLangActive;
    }

    public void setIndexLangActive(Integer indexLangActive) {
        this.indexLangActive = indexLangActive;
    }

    /**
     * Get searchDto
     * 
     * @return String
     * @author taitm
     */
    public String getSearchDto() {
        return searchDto;
    }

    /**
     * Set searchDto
     * 
     * @param searchDto
     *            type String
     * @return
     * @author taitm
     */
    public void setSearchDto(String searchDto) {
        this.searchDto = searchDto;
    }

}