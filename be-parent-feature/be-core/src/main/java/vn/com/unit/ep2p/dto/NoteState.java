/*******************************************************************************
 * Class        NoteState
 * Created date 2018/07/13
 * Lasted date  2018/07/13
 * Author       VinhLT
 * Change log   2018/07/1301-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.io.Serializable;

/**
 * NoteState
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class NoteState implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private boolean opened = false;

	private boolean disabled = false;

	private boolean selected = false;

	private boolean loaded = false;

	/**
	 * Get opened
	 * 
	 * @return boolean
	 * @author VinhLT
	 */
	public boolean isOpened() {
		return opened;
	}

	/**
	 * Set opened
	 * 
	 * @param opened
	 *            type boolean
	 * @return
	 * @author VinhLT
	 */
	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	/**
	 * Get disabled
	 * 
	 * @return boolean
	 * @author VinhLT
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Set disabled
	 * 
	 * @param disabled
	 *            type boolean
	 * @return
	 * @author VinhLT
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * Get selected
	 * 
	 * @return boolean
	 * @author VinhLT
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Set selected
	 * 
	 * @param selected
	 *            type boolean
	 * @return
	 * @author VinhLT
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Get loaded
	 * 
	 * @return boolean
	 * @author VinhLT
	 */
	public boolean isLoaded() {
		return loaded;
	}

	/**
	 * Set loaded
	 * 
	 * @param loaded
	 *            type boolean
	 * @return
	 * @author VinhLT
	 */
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

}
