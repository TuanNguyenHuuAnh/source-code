/*******************************************************************************
 * Class        TreeObject
 * Created date 2019/02/19
 * Lasted date  2019/02/19
 * Author       KhoaNA
 * Change log   2019/02/19 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.treebuilder.utils;

/**
 * TreeObject
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
import java.util.ArrayList;
import java.util.List;

public class TreeObject<T> {

	private List<TreeObject<T>> children;

	private TreeObject<T> parent;

	private T dataObject;

	public TreeObject(T dataObject) {
		this.dataObject = dataObject;
		children = new ArrayList<TreeObject<T>>();
	}

	public TreeObject<T> addChild(final T dataObject) {
		final TreeObject<T> node = new TreeObject<T>(dataObject);
		return children.add(node) ? node : null;
	}

	public List<TreeObject<T>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeObject<T>> children) {
		this.children = children;
	}

	public TreeObject<T> getParent() {
		return parent;
	}

	public void setParent(TreeObject<T> parent) {
		this.parent = parent;
	}

	public T getDataObject() {
		return dataObject;
	}

	public void setDataObject(T dataObject) {
		this.dataObject = dataObject;
	}

	/*@Override
	public int compare(TreeObject<T> o1, TreeObject<T> o2) {
		return ((Comparable) o1).compareTo(o2);
	}*/
}