/*******************************************************************************
 * Class        BranchSearchDto
 * Created date 2017/03/22
 * Lasted date  2017/03/22
 * Author       BaoVV
 * Change log   2017/03/2201-00 BaoVV create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

/**
 * BranchSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author BaoVV
 */
@Getter
@Setter
public class BranchSearchDto extends CmsCommonSearchFilterDto {

	private static final long serialVersionUID = 1L;
	
    private String cityName;

	private String city;

	private String district;

	private String region;
	
	private String districtName;
	
	private String type;
	
	private String address;
	
	private String typeName;
}
