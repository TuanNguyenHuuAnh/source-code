/*******************************************************************************
 * Class        ：TreeNode
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.common.tree;

/**
 * TreeNode
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface TreeNode {
	public Long getNodeId();
	
	public Long getNodeParentId();
	
	public String getNodeName();
	
	public Integer getNodeOrder();
	
	public boolean getSelected();
}
