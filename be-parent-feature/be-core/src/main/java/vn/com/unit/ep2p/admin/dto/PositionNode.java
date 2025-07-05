/*******************************************************************************
 * Class        OrgNode
 * Created date 2017/02/28
 * Lasted date  2017/02/28
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/02/2801-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

/**
 * OrgNode
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
public class PositionNode {
	private long id;
	private String text;
	private String state;
	private String iconCls;
	private String checked;
	private String attributes;
	private String target;
	private String code;
	
	private List<PositionNode> children;
	
	public PositionNode(){}
	
	public PositionNode(long id, String text, String state, String iconCls, String checked, String attributes,
			String target) {
		super();
		this.id = id;
		this.text = text;
		this.iconCls = iconCls;
		this.state = state;
		this.checked = checked;
		this.attributes = attributes;
		this.target = target;
	}
		
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<PositionNode> getChildren() {
		return children;
	}

	public void setChildren(List<PositionNode> children) {
		this.children = children;
	}

	/**
	 * Get code
	 * @return String
	 * @author vinhnht
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code
	 * @param code
	 * @author vinhnht
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
