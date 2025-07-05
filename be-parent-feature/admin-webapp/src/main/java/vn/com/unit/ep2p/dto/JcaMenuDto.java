/*******************************************************************************
 * Class        ：JcaMenuDto
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：SonND
 * Change log   ：2020/12/09：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.ep2p.dto;

import vn.com.unit.common.tree.TreeNode;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaMenuDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@SuppressWarnings("unused")
public class JcaMenuDto extends AbstractTracking implements TreeNode {

    private Long id;
	private String code;
    private String url;
    private Long parentId;
    private Integer menuOrder;
    private Integer menuLevel;
    private String menuType;
    private Long companyId;
    private Long itemId;
    private String icon;
    private String status;
    private boolean actived;
    private String parentMenuCode;
    private String parentMenuName;
    private boolean headerFlag;
    
    private String languageCode;
    private Long languageId;
    private Long menuLanguageId;
    private String alias;
    private String name;
    

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
        return menuOrder;
    }

    @Override
    public boolean getSelected() {
        return false;
    }
    
    public boolean getActived() {
        return actived;
    }
}
