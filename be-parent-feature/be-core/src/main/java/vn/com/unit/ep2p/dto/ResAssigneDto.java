/*******************************************************************************
 * Class        ：ResAccountDto
 * Created date ：2019/08/29
 * Lasted date  ：2019/08/29
 * Author       ：taitt
 * Change log   ：2019/08/29：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * ResAccountDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class ResAssigneDto {

	private Long assignId;
	
	private String assignName;
	
	private String assignText;

	/**
	 * Get assignId
	 * @return Long
	 * @author taitt
	 */
	public Long getAssignId() {
		return assignId;
	}

	/**
	 * Set assignId
	 * @param   assignId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	/**
	 * Get assignName
	 * @return String
	 * @author taitt
	 */
	public String getAssignName() {
		return assignName;
	}

	/**
	 * Set assignName
	 * @param   assignName
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setAssignName(String assignName) {
		this.assignName = assignName;
	}

	/**
	 * Get assignText
	 * @return String
	 * @author taitt
	 */
	public String getAssignText() {
		return assignText;
	}

	/**
	 * Set assignText
	 * @param   assignText
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setAssignText(String assignText) {
		this.assignText = assignText;
	}
}
