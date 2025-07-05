/*******************************************************************************
 * Class        ConditionNode
 * Created date 2018/07/13
 * Lasted date  2018/07/13
 * Author       VinhLT
 * Change log   2018/07/1301-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * ConditionNode
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class ConditionNode implements Serializable{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** id get from database */
	private String id;

	/** text use to display on screen */
	private String text;

	/**
	 * firstField on the left (a condition b) firstField is a, it can be value
	 * or attribute(ObjectDefinition)
	 */
	private String firstField;

	/**
	 * lastField on the right (a condition b) lastField is b, it can be value or
	 * attribute(ObjectDefinition)
	 */
	private String lastField;

	/**
	 * compareOperator it can be <, <=, >, >=, ==, !=. if field is String, it
	 * can be .contain() or something
	 */
	private String compareOperator;

	/** conditionOperator it can be and(&&), or(||) */
	private String conditionOperator;

	/** hasChild have node child or no */
	private boolean hasChild = false;

	/** status of node open, close, disable */
	private NoteState noteState = new NoteState();

	/** children List node child */
	private List<ConditionNode> children = new ArrayList<>();

	/** parentId the node has this child */
	private String parentId;

	/** rootId */
	private String rootId;

	/**
	 * @author VinhLT
	 */
	public ConditionNode() {

	}

	/**
	 * @param id
	 * @param text
	 * @param firstField
	 * @param lastField
	 * @param compareOperator
	 * @param conditionOperator
	 * @param hasChild
	 * @param children
	 * @param parentId
	 * @param rootId
	 * @author VinhLT
	 */
	public ConditionNode(String id, String text, String firstField, String lastField, String compareOperator,
			String conditionOperator, boolean hasChild, List<ConditionNode> children, String parentId, String rootId) {
		this.id = id;
		this.text = text;
		this.firstField = firstField;
		this.lastField = lastField;
		this.compareOperator = compareOperator;
		this.conditionOperator = conditionOperator;
		this.hasChild = hasChild;
		this.children = children == null ? new ArrayList<>() : new ArrayList<>(children);
		this.parentId = parentId;
		this.rootId = rootId;
	}

	/**
	 * Get id
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get text
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set text
	 * 
	 * @param text
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Get firstField
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getFirstField() {
		return firstField;
	}

	/**
	 * Set firstField
	 * 
	 * @param firstField
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setFirstField(String firstField) {
		this.firstField = firstField;
	}

	/**
	 * Get lastField
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getLastField() {
		return lastField;
	}

	/**
	 * Set lastField
	 * 
	 * @param lastField
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setLastField(String lastField) {
		this.lastField = lastField;
	}

	/**
	 * Get compareOperator
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getCompareOperator() {
		return compareOperator;
	}

	/**
	 * Set compareOperator
	 * 
	 * @param compareOperator
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setCompareOperator(String compareOperator) {
		this.compareOperator = compareOperator;
	}

	/**
	 * Get conditionOperator
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getConditionOperator() {
		return conditionOperator;
	}

	/**
	 * Set conditionOperator
	 * 
	 * @param conditionOperator
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setConditionOperator(String conditionOperator) {
		this.conditionOperator = conditionOperator;
	}

	/**
	 * Get hasChild
	 * 
	 * @return boolean
	 * @author VinhLT
	 */
	public boolean isHasChild() {
		return hasChild;
	}

	/**
	 * Set hasChild
	 * 
	 * @param hasChild
	 *            type boolean
	 * @return
	 * @author VinhLT
	 */
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	/**
	 * Get children
	 * 
	 * @return List<ConditionNode>
	 * @author VinhLT
	 */
	public List<ConditionNode> getChildren() {
		return children != null ? new ArrayList<>(children) : null;
	}

	/**
	 * Set children
	 * 
	 * @param children
	 *            type List<ConditionNode>
	 * @return
	 * @author VinhLT
	 */
	public void setChildren(List<ConditionNode> children) {
		this.children = children != null ? new ArrayList<>(children) : null;
	}

	/**
	 * Get parentId
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Set parentId
	 * 
	 * @param parentId
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Get rootId
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getRootId() {
		return rootId;
	}

	/**
	 * Set rootId
	 * 
	 * @param rootId
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	/**
	 * Get noteState
	 * 
	 * @return NoteState
	 * @author VinhLT
	 */
	public NoteState getNoteState() {
		return noteState;
	}

	/**
	 * Set noteState
	 * 
	 * @param noteState
	 *            type NoteState
	 * @return
	 * @author VinhLT
	 */
	public void setNoteState(NoteState noteState) {
		this.noteState = noteState;
	}

	public void setLastListNodeChild(List<ConditionNode> lstAll) {
		List<ConditionNode> lstChilds = lstAll.stream().filter(item -> StringUtils.equals(item.parentId, this.getId()))
				.collect(Collectors.toList());
		if (lstChilds != null && lstChilds.size() > 0) {
			this.hasChild = true;
			this.setChildren(lstChilds);
			for (ConditionNode conditionNode : lstChilds) {
				conditionNode.setLastListNodeChild(lstAll);
			}
		}
	}

}
