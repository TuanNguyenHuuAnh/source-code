/*******************************************************************************
* Class        EfoDocDto
* Created date 2021/03/03
* Lasted date  2021/03/03
* Author       TaiTT
* Change log   2021/03/03 01-00 TaiTT create a new
******************************************************************************/

package vn.com.unit.ep2p.core.efo.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.AbstractDocumentDto;

/**
 * EfoDocDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTT
 */

@Getter
@Setter
public class EfoDocDto extends AbstractDocumentDto{

	private Long summittedId;
	private Date submittedDate;
	private Long submittedOrgId;
	private Long submittedPositionId;

	private Long ownerId;
	private Long ownerOrgId;
	private Long ownerPositionId;


    private String formName;
    private boolean isMajor;
    private Long mainFileId;
    private List<String> fileStream;
    
    private String note;
    
    private String docInputJson;
    
    private String inputJsonEmail;
    
    private Long updatedId;

}