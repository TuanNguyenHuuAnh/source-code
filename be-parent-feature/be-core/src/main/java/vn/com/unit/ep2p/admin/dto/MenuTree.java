/*******************************************************************************
 * Class        ：MenuTree
 * Created date ：2019/06/17
 * Lasted date  ：2019/06/17
 * Author       ：HungHT
 * Change log   ：2019/06/17：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import vn.com.unit.ep2p.treebuilder.utils.MenuNode;


/**
 * MenuTree
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class MenuTree {

    private List<MenuNode> menuTree;
    private Long rootId;

    /**
     * Get menuTree
     * 
     * @return List<MenuNode>
     * @author HungHT
     */
    public List<MenuNode> getMenuTree() {
        return menuTree;
    }

    /**
     * Set menuTree
     * 
     * @param menuTree
     *            type List<MenuNode>
     * @return
     * @author HungHT
     */
    public void setMenuTree(List<MenuNode> menuTree) {
        this.menuTree = menuTree;
    }

    /**
     * Get rootId
     * 
     * @return Long
     * @author HungHT
     */
    public Long getRootId() {
        return rootId;
    }

    /**
     * Set rootId
     * 
     * @param rootId
     *            type Long
     * @return
     * @author HungHT
     */
    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }
}
