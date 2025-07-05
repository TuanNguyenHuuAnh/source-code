/*******************************************************************************
 * Class        ：SlaSearchDto
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlaSearchDto {

	/** The business id. */
	private String businessId;
	
	/** The process deploy id. */
	private String processDeployId;
	
	/** The name. */
	private String name;
	
	private Long companyId;
    private String companyName;
    private boolean companyAdmin;


}
