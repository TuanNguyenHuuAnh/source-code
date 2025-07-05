/*******************************************************************************
 * Class        ：FaqsCategoryEdit
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.common.tree.TreeNode;

//import vn.com.unit.util.Util;

/**
 * FaqsCategoryEdit
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Getter
@Setter
public class FaqsCategoryEditDto extends CmsCommonEditDto implements TreeNode {

    @Valid
    private List<FaqsCategoryLanguageDto> categoryLanguageList;

    private List<FaqsCategoryDto> lstFaqsCategoryToSort;

    private Long faqsCategoryParentId;

    private String itemFunctionCode;

    @Override
    public Long getNodeId() {
        return super.getId();
    }

    @Override
    public Long getNodeParentId() {
        return faqsCategoryParentId;
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
