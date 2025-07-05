/*******************************************************************************
 * Class        ：DocumentCategoryDto
 * Created date ：2017/04/17
 * Lasted date  ：2017/04/17
 * Author       ：TaiTM
 * Change log   ：2017/04/17:01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.common.tree.TreeNode;

/**
 * 
 * DocumentCategoryDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class DocumentCategoryEditDto extends CmsCommonEditDto implements TreeNode {
    private Long parentId;
    private List<DocumentCategoryLanguageDto> languageList;
    private String itemFunctionCode;
    private Boolean forCandidate;
    private String categoryType;
    private String partner;

    @Override
    public Long getNodeId() {
        return super.getId();
    }

    @Override
    public Long getNodeParentId() {
        return parentId;
    }

    @Override
    public String getNodeName() {
        return super.getTitle();
    }

    @Override
    public Integer getNodeOrder() {
        return super.getSort().intValue();
    }

    @Override
    public boolean getSelected() {
        return false;
    }
}
