/*******************************************************************************
 * Class        ：JcaMenuDto
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：SonND
 * Change log   ：2020/12/09：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.common.tree.TreeNode;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaMenuDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaMenuDto extends AbstractTracking implements TreeNode {

    private Long id;

    private String code;
    
    private String parentMenuCode;

    private Integer displayOrder;

    private String url;

    private Long itemId;

    private String className;

    private Integer menuType;

    private boolean groupFlag;

    private Integer status;

    private boolean actived;

    private Long companyId;

    private Long parentId;

    List<JcaMenuLangDto> languages;

    private String name;

    private String nameAbv;

    private String langCode;

    private Long langId;
    
    private Long menuId;

    private String menuModule;
    
    @Override
    public Long getNodeId() {
        return id;
    }

    @Override
    public Long getNodeParentId() {
        return parentId;
    }

    @Override
    public String getNodeName() {
        return name;
    }

    @Override
    public Integer getNodeOrder() {
        return displayOrder;
    }

    @Override
    public boolean getSelected() {
        return false;
    }

    public boolean getActived() {
        return actived;
    }
}
