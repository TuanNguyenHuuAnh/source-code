/*******************************************************************************
 * Class        OrgNode
 * Created date 2017/02/22
 * Lasted date  2017/02/22
 * Author       KhoaNA
 * Change log   2017/02/2201-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import lombok.Data;

/**
 * OrgNode
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Data
public class OrgNode {
    
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
	private List<OrgNode> children;
	
	/*org code*/
	private String code;
	

}
