/*******************************************************************************
 * Class        ：JSTree
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.common.tree;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * JSTree
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class JSTree<T> {
	private List<JSTree<T>> children;

	private JSTree<T> parent;

	private T dataObject;
	
	private Long nodeId;
	
	private String nodeName;
	
	private String stateOpen;
	
	private boolean selected;
	
    
    private String title;
    private Long value;
    private Long key;

	public JSTree(T dataObject) {
		this.dataObject = dataObject;
		children = new ArrayList<>();
	}

	public JSTree(Long nodeId, String nodeName) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		
		this.value = nodeId;
		this.title = nodeName;
		this.key = nodeId;
	}

	public JSTree<T> addChild(final T dataObject) {
		final JSTree<T> node = new JSTree<>(dataObject);
		return children.add(node) ? node : null;
	}

	public void addState(String stateOpen, boolean selected) {
		this.stateOpen = stateOpen;
		this.selected = selected;
	}
}
