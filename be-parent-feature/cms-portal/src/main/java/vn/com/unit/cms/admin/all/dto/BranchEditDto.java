package vn.com.unit.cms.admin.all.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.common.tree.TreeNode;


@Getter
@Setter
public class BranchEditDto extends CmsCommonEditDto implements TreeNode{
	
	private String name;
	
	private String type;
	
	private String typeName;
	
	private Long region;
	
	private String city;
	
	private String district;

	private String cityName;

	private String districtName;
	
	private String address;

    private String latitude;
       
    private String longtitude;
   
    private String phone;
    
    private String fax;
       
    private String email;
    
    private String note;
    
    private Integer activeFlag;
    
    private String workingHours;
    
	@Override
	public Long getNodeId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNodeParentId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getNodeOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getSelected() {
		// TODO Auto-generated method stub
		return false;
	}

}
