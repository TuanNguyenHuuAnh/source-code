package vn.com.unit.ep2p.admin.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.ep2p.admin.enumdef.MenuTypeEnum.MenuTypeCatEnum;

public class MenuDto extends AbstractCompanyDto {
	/**menuId*/
	private Long menuId;
	
	/**menuCode*/	
    @Size(min=1,max=30)
	private String menuCode;
	
	/**menuName*/
	private String menuName;
	
	/**url*/	
    @Size(min=1,max=255)
	private String url;
	
	/**parentId*/
	private Long parentId;
	
	/**parentName*/
	private String parentName;
	
	/**status*/
	private Integer status;
	
	/**listSubMenu*/
	private List<MenuDto> listSubMenu;
	
	/**listMenuLanguage*/
	private List<MenuLanguageDto> listMenuLanguage;
	 
	/**viName*/
	private String viName;
	
	/**enName*/
	private String enName;
	
	/**viMenuId*/
	private Long viMenuId;
	
	/**enMenuId*/
	private Long enMenuId;
	
	/**alias*/
	private String alias;
	
	/**viAlias*/
	private String viAlias;
	
	/**enAlias*/
	private String enAlias;
	
	/**sort*/
	private Integer sort;
	
	/**CREATED_DATE*/
	private Date createdDate;
	
	/**icon*/
	private String icon;
	
	/**menutype*/
	private String menuType;
	
	/**checkOpen*/
	private boolean checkOpen;

	/**checkActive*/
	private boolean activeMenu;
	
	private boolean root;
	
	private boolean deleteFlag;
	
	private Date updatedDate;
	
	private String menuModule;

	public boolean getActiveMenu() {
		return activeMenu;
	}

	public void setActiveMenu(boolean activeMenu) {
		this.activeMenu = activeMenu;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	// Properties for item
	private Long itemId;
	private String itemName;
	private List<ItemDto> listItemDto;
	private String functionCode;	
	
    /** sortOderList */
    private List<SortOrderDto> sortOderList;
	
	/**
     * Get functionCode
     * @return String
     * @author phunghn
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * Set functionCode
     * @param   functionCode
     *          type String
     * @return
     * @author  phunghn
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    /** fieldSearch */
    private String fieldSearch;
    
    /** fieldValues */
    private List<String> fieldValues;
    
    /** languageCode */
    private String languageCode;
    
    /** url */
    private String urlHidden;
    
    private Long ownerId;
    
    private Long ownerBranchId;
    
    private Long ownerSectionId;
    
    private Long assignerId;
    
    private Long assignerBranchId;
    
    private Long assignerSectionId;
    
    private String statusCode;
    
    private Long processId;   
    
    private String comment;
    
    private boolean action;
    
    private String statusCodeDisp;
    
    private String referenceType;
    
    private Long referenceId;
    
    /** menuTypeList */
    private List<String> menuTypeList;
    
    /** customerTypeId */
    private Long customerTypeId;
    
    /** menuTypeName */
    private String menuTypeName;
    
    /** icon name*/
    private String iconImg;
    
    /** physical icon*/
    private String physicalIcon;
    
    /** customerTypeName */
    private String customerTypeName;
    
    private String menuTypePostion;
    
	public MenuDto() {
	    
	}
	
	public Long getMenuId() {
		return menuId;
	}
	
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	public String getMenuCode() {
		return menuCode;
	}
	
	public void setMenuCode(String menuCode) {
		this.menuCode = CommonStringUtil.upperCase(menuCode);
	}
	
	public String getMenuName() {
		return menuName;
	}
	
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public List<MenuDto> getListSubMenu() {
		return listSubMenu;
	}
	
	public void setListSubMenu(List<MenuDto> listSubMenu) {
		this.listSubMenu = listSubMenu;
	}

	public String getViName() {
		return viName;
	}

	public void setViName(String viName) {
		this.viName = viName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getViMenuId() {
		return viMenuId;
	}

	public void setViMenuId(Long viMenuId) {
		this.viMenuId = viMenuId;
	}

	public Long getEnMenuId() {
		return enMenuId;
	}

	public void setEnMenuId(Long enMenuId) {
		this.enMenuId = enMenuId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getViAlias() {
		return viAlias;
	}

	public void setViAlias(String viAlias) {
		this.viAlias = viAlias;
	}

	public String getEnAlias() {
		return enAlias;
	}

	public void setEnAlias(String enAlias) {
		this.enAlias = enAlias;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}	
	
    /**
     * Get createdDate
     * @return Date
     * @author TranLTH
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     * @param   createdDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<MenuLanguageDto> getListMenuLanguage() {
		return listMenuLanguage;
	}

	public void setListMenuLanguage(List<MenuLanguageDto> listMenuLanguage) {
		this.listMenuLanguage = listMenuLanguage;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public boolean getCheckOpen() {
		return checkOpen;
	}

	public void setCheckOpen(boolean checkOpen) {
		this.checkOpen = checkOpen;
	}

   

    /**
     * Get itemName
     * @return String
     * @author trieunh <trieunh@unit.com.vn>
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Set itemName
     * @param   itemName
     *          type String
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Get listItemDto
     * @return List<ItemDto>
     * @author trieunh <trieunh@unit.com.vn>
     */
    public List<ItemDto> getListItemDto() {
        return listItemDto;
    }

    /**
     * Set listItemDto
     * @param   listItemDto
     *          type List<ItemDto>
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setListItemDto(List<ItemDto> listItemDto) {
        this.listItemDto = listItemDto;
    }

    /**
     * Get fieldSearch
     * @return String
     * @author TranLTH
     */
    public String getFieldSearch() {
        return fieldSearch;
    }

    /**
     * Set fieldSearch
     * @param   fieldSearch
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
    }

    /**
     * Get fieldValues
     * @return List<String>
     * @author TranLTH
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }

    /**
     * Set fieldValues
     * @param   fieldValues
     *          type List<String>
     * @return
     * @author  TranLTH
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
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
     * Get urlHidden
     * @return String
     * @author TranLTH
     */
    public String getUrlHidden() {
        return urlHidden;
    }

    /**
     * Set urlHidden
     * @param   urlHidden
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setUrlHidden(String urlHidden) {
        this.urlHidden = urlHidden;
    }

    /**
     * Get ownerId
     * @return Long
     * @author TranLTH
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * Set ownerId
     * @param   ownerId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Get ownerBranchId
     * @return Long
     * @author TranLTH
     */
    public Long getOwnerBranchId() {
        return ownerBranchId;
    }

    /**
     * Set ownerBranchId
     * @param   ownerBranchId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setOwnerBranchId(Long ownerBranchId) {
        this.ownerBranchId = ownerBranchId;
    }

    /**
     * Get ownerSectionId
     * @return Long
     * @author TranLTH
     */
    public Long getOwnerSectionId() {
        return ownerSectionId;
    }

    /**
     * Set ownerSectionId
     * @param   ownerSectionId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setOwnerSectionId(Long ownerSectionId) {
        this.ownerSectionId = ownerSectionId;
    }

    /**
     * Get assignerId
     * @return Long
     * @author TranLTH
     */
    public Long getAssignerId() {
        return assignerId;
    }

    /**
     * Set assignerId
     * @param   assignerId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setAssignerId(Long assignerId) {
        this.assignerId = assignerId;
    }

    /**
     * Get assignerBranchId
     * @return Long
     * @author TranLTH
     */
    public Long getAssignerBranchId() {
        return assignerBranchId;
    }

    /**
     * Set assignerBranchId
     * @param   assignerBranchId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setAssignerBranchId(Long assignerBranchId) {
        this.assignerBranchId = assignerBranchId;
    }

    /**
     * Get assignerSectionId
     * @return Long
     * @author TranLTH
     */
    public Long getAssignerSectionId() {
        return assignerSectionId;
    }

    /**
     * Set assignerSectionId
     * @param   assignerSectionId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setAssignerSectionId(Long assignerSectionId) {
        this.assignerSectionId = assignerSectionId;
    }

    /**
     * Get statusCode
     * @return String
     * @author TranLTH
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode
     * @param   statusCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Get processId
     * @return Long
     * @author TranLTH
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * @param   processId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get comment
     * @return String
     * @author TranLTH
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment
     * @param   comment
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setComment(String comment) {
        this.comment = comment;
    }   

    /**
     * Get action
     * @return boolean
     * @author TranLTH
     */
    public boolean isAction() {
        return action;
    }

    /**
     * Set action
     * @param   action
     *          type boolean
     * @return
     * @author  TranLTH
     */
    public void setAction(boolean action) {
        this.action = action;
    }

    /**
     * Get statusCodeDisp
     * @return String
     * @author TranLTH
     */
    public String getStatusCodeDisp() {
        return statusCodeDisp;
    }

    /**
     * Set statusCodeDisp
     * @param   statusCodeDisp
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setStatusCodeDisp(String statusCodeDisp) {
        this.statusCodeDisp = statusCodeDisp;
    }

    /**
     * Get referenceType
     * @return String
     * @author TranLTH
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * Set referenceType
     * @param   referenceType
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * Get referenceId
     * @return Long
     * @author TranLTH
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Set referenceId
     * @param   referenceId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Get menuTypeList
     * @return List<String>
     * @author hand
     */
    public List<String> getMenuTypeList() {
        return menuTypeList;
    }

    /**
     * Set menuTypeList
     * @param   menuTypeList
     *          type List<String>
     * @return
     * @author  hand
     */
    public void setMenuTypeList(List<String> menuTypeList) {
        this.menuTypeList = menuTypeList;
    }

    /**
     * Get customerTypeId
     * @return Long
     * @author hand
     */
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    /**
     * Set customerTypeId
     * @param   customerTypeId
     *          type Long
     * @return
     * @author  hand
     */
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    /**
     * Get menuTypeName
     * @return String
     * @author hand
     */
    public String getMenuTypeName() {
        for (MenuTypeCatEnum menuTypeCat : MenuTypeCatEnum.values()) {
            if (StringUtils.equals(this.menuType, menuTypeCat.toString())) {
                menuTypeName = menuTypeCat.getName();
                break;
            }
        }

        return menuTypeName;
    }

    /**
     * Set menuTypeName
     * @param   menuTypeName
     *          type String
     * @return
     * @author  hand
     */
    public void setMenuTypeName(String menuTypeName) {
        this.menuTypeName = menuTypeName;
    }

    /**
     * Get iconImg
     * @return String
     * @author hand
     */
    public String getIconImg() {
        return iconImg;
    }

    /**
     * Get physicalIcon
     * @return String
     * @author hand
     */
    public String getPhysicalIcon() {
        return physicalIcon;
    }

    /**
     * Set iconImg
     * @param   iconImg
     *          type String
     * @return
     * @author  hand
     */
    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    /**
     * Set physicalIcon
     * @param   physicalIcon
     *          type String
     * @return
     * @author  hand
     */
    public void setPhysicalIcon(String physicalIcon) {
        this.physicalIcon = physicalIcon;
    }

    /**
     * Get customerTypeName
     * @return String
     * @author hand
     */
    public String getCustomerTypeName() {
        return customerTypeName;
    }

    /**
     * Set customerTypeName
     * @param   customerTypeName
     *          type String
     * @return
     * @author  hand
     */
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    /**
     * Get menuTypePostion
     * @return String
     * @author hand
     */
    public String getMenuTypePostion() {
        return menuTypePostion;
    }

    /**
     * Set menuTypePostion
     * @param   menuTypePostion
     *          type String
     * @return
     * @author  hand
     */
    public void setMenuTypePostion(String menuTypePostion) {
        this.menuTypePostion = menuTypePostion;
    }

    /**
     * Get itemId
     * @return Long
     * @author phunghn
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * Set itemId
     * @param   itemId
     *          type Long
     * @return
     * @author  phunghn
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * Get sortOderList
     * @return List<SortOrderDto>
     * @author hand
     */
    public List<SortOrderDto> getSortOderList() {
        return sortOderList;
    }

    /**
     * Set sortOderList
     * @param   sortOderList
     *          type List<SortOrderDto>
     * @return
     * @author  hand
     */
    public void setSortOderList(List<SortOrderDto> sortOderList) {
        this.sortOderList = sortOderList;
    }

    
    /**
     * Get root
     * @return boolean
     * @author HungHT
     */
    public boolean isRoot() {
        return root;
    }

    
    /**
     * Set root
     * @param   root
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setRoot(boolean root) {
        this.root = root;
    }

    
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

	/**
	 * @return the menuModule
	 */
	public String getMenuModule() {
		return menuModule;
	}

	/**
	 * @param menuModule the menuModule to set
	 */
	public void setMenuModule(String menuModule) {
		this.menuModule = menuModule;
	}

}