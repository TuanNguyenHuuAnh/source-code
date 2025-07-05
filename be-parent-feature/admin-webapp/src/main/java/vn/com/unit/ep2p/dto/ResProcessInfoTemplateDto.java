/*******************************************************************************
 * Class        ：ResProcessInfoTemplateDto
 * Created date ：2019/08/21
 * Lasted date  ：2019/08/21
 * Author       ：taitt
 * Change log   ：2019/08/21：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * ResProcessInfoTemplateDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class ResProcessInfoTemplateDto {

	private Long processId;
	
	private List<ResDocumentButtonDto> bpmButtons;

	/**
	 * Get processId
	 * @return Long
	 * @author taitt
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * Set processId
	 * @param   processId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	/**
	 * Get bpmButtons
	 * @return List<ResDocumentButtonDto>
	 * @author taitt
	 */
	public List<ResDocumentButtonDto> getBpmButtons() {
		return bpmButtons;
	}

	/**
	 * Set bpmButtons
	 * @param   bpmButtons
	 *          type List<ResDocumentButtonDto>
	 * @return
	 * @author  taitt
	 */
	public void setBpmButtons(List<ResDocumentButtonDto> bpmButtons) {
		this.bpmButtons = bpmButtons;
	}
}
