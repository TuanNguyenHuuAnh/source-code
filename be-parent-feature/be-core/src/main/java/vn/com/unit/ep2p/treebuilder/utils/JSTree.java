/*******************************************************************************
 * Class        JSTree
 * Created date 2019/02/19
 * Lasted date  2019/02/19
 * Author       KhoaNA
 * Change log   2019/02/19 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.treebuilder.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * JSTree
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@JsonInclude(Include.NON_NULL)
public class JSTree {

	private Long id;
	
	private String text;
	
	private String icon;
	
	private Map<String, Boolean> state;
	
	private List<JSTree> children;
	
	public JSTree(Long id, String text) {
		this.id = id;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Map<String, Boolean> getState() {
		return state;
	}

	public void setState(Map<String, Boolean> state) {
		this.state = state;
	}

	public List<JSTree> getChildren() {
		return children;
	}

	public void setChildren(List<JSTree> children) {
		this.children = children;
	}
	
	public void addState(String stateName, boolean stateBoolean) {
		if( this.state == null ) {
			this.state = new HashMap<>();
		}
		
		this.state.put(stateName, stateBoolean);
	}
	
	public void addChild(JSTree child) {
		if( this.children == null ) {
			this.children = new ArrayList<>();
		}
		
		this.children.add(child);
	}
}
