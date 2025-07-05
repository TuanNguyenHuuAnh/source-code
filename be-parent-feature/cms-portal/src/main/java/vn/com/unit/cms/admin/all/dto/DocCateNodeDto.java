package vn.com.unit.cms.admin.all.dto;

import java.util.List;

public class DocCateNodeDto {
	private Long id;
	private String name;
	private boolean active;
	private String text;
	private boolean selected;
	
	private List<DocCateNodeDto> children;
	
	public DocCateNodeDto(){}
	
	public DocCateNodeDto(long id, String name, boolean active) {
		super();
		this.id = id;
		this.setName(name);
		this.setActive(active);
		this.setText(name);
	}
		
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public List<DocCateNodeDto> getChildren() {
		return children;
	}

	public void setChildren(List<DocCateNodeDto> children) {
		this.children = children;
	}
    /**
     * Get name
     * @return String
     * @author thuydtn
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setName(String name) {
        this.name = name;
        this.text = name;
    }

    /**
     * Get active
     * @return boolean
     * @author thuydtn
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set active
     * @param   active
     *          type boolean
     * @return
     * @author  thuydtn
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get text
     * @return String
     * @author thuydtn
     */
    public String getText() {
        return text;
    }

    /**
     * Set text
     * @param   text
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get selected
     * @return boolean
     * @author thuydtn
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Set selected
     * @param   selected
     *          type boolean
     * @return
     * @author  thuydtn
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
