/*******************************************************************************
 * Class        ：ResMytaskDetailListDto
 * Created date ：2019/08/14
 * Lasted date  ：2019/08/14
 * Author       ：taitt
 * Change log   ：2019/08/14：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * ResMytaskDetailListDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class ResMytaskDetailListDto {

	/** data */
	List<ResMytaskDetailDto> data;
	
	private Long totalData;

	/**
	 * Get data
	 * @return List<ResMytaskDetailDto>
	 * @author taitt
	 */
	public List<ResMytaskDetailDto> getData() {
		return data;
	}

	/**
	 * Set data
	 * @param   data
	 *          type List<ResMytaskDetailDto>
	 * @return
	 * @author  taitt
	 */
	public void setData(List<ResMytaskDetailDto> data) {
		this.data = data;
	}

	/**
	 * Get totalData
	 * @return Long
	 * @author taitt
	 */
	public Long getTotalData() {
		return totalData;
	}

	/**
	 * Set totalData
	 * @param   totalData
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setTotalData(Long totalData) {
		this.totalData = totalData;
	}

	
}
