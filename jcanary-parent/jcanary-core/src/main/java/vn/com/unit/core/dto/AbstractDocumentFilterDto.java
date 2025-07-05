/*******************************************************************************
 * Class        ：AbstractDocumentFilterDto
 * Created date ：2021/01/14
 * Lasted date  ：2021/01/14
 * Author       ：taitt
 * Change log   ：2021/01/14：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * AbstractDocumentFilterDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public abstract class AbstractDocumentFilterDto {
    private Long taskId;
    private Long docId;
    
    
	private Long submittedId;
	private Long submittedOrgId;
	private Long submittedPositionId;

	private Long ownerId;
	private Long ownerOrgId;
	private Long ownerPositionId;

	private Long assigneeId;
	private Long assigneeOrgId;
	private Long assigneePositionId;
	
	
	private Date submittedDate;
	
	private String processStatusCode;
}
