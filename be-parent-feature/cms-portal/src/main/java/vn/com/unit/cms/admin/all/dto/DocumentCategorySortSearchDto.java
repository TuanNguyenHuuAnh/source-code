/*******************************************************************************
 * Class        ：DocumentCategorySearchDto
 * Created date ：2017/04/20
 * Lasted date  ：2017/04/20
 * Author       ：thuydtn
 * Change log   ：2017/04/20：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * IntroductionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentCategorySortSearchDto{
    private Long typeId;
    private Long parentId;
    
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
