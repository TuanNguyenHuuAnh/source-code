/*******************************************************************************
 * Class        ：TreeObject
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.common.tree;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TreeObject
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class TreeObject<T> {
	private List<TreeObject<T>> children;

	private TreeObject<T> parent;

	private T dataObject;

	public TreeObject(T dataObject) {
		this.dataObject = dataObject;
		children = new ArrayList<>();
	}

	public TreeObject<T> addChild(final T dataObject) {
		final TreeObject<T> node = new TreeObject<>(dataObject);
		return children.add(node) ? node : null;
	}
}
