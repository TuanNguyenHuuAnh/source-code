/*******************************************************************************
 * Class        ：MenuNode
 * Created date ：2017/03/09
 * Author       ：vinhnht
 * Change log   ：2017/03/09 vinhnht create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.core;

import java.util.List;

/**
 * IntroductionCategoryNode
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentCategoryNode {

	 /** id */
	private Long id;
	
	/** text */
	private String text;
	
	/** state */
	private String state;
	
	/** iconCls */
	private String iconCls;
	
	/** checked */
	private String checked;
	
	/** attributes */
	private String attributes;
	
	/** target */
	private String target;
	
	/** children */
	private List<DocumentCategoryNode> children;
	
	/**
	 * Constructor default
	 * 
	 * @author thuydtn
	 */
	public DocumentCategoryNode(){}
	
	/**
	 * Constructor with params
	 * 
	 * @param id
	 * @param text
	 * @param state
	 * @param iconCls
	 * @param checked
	 * @param attributes
	 * @param target
	 * @author vinhnht
	 */
	public DocumentCategoryNode(Long id, String text, String state, String iconCls, String checked, String attributes,
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public List<DocumentCategoryNode> getChildren() {
		return children;
	}

	public void setChildren(List<DocumentCategoryNode> children) {
		this.children = children;
	}	
}
