/*******************************************************************************
 * Class        TreeNode
 * Created date 2019/02/19
 * Lasted date  2019/02/19
 * Author       KhoaNA
 * Change log   2019/02/19 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.treebuilder.utils;

/**
 * TreeNode
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface TreeNode {
	public Long getNodeId();
	
	public Long getNodeParentId();
	
	public String getNodeName();
	
	public int getNodeOrder();
	
	public boolean getSelected();
}
