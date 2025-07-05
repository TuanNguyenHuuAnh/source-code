/*******************************************************************************
 * Class        Select2ResultDto
 * Created date 2017/11/03
 * Lasted date  2017/11/03
 * Author       trongcv
 * Change log   2017/09/1301-00 trongcv create a new
 ******************************************************************************/

package vn.com.unit.ep2p.admin.dto;
import java.util.List;

import vn.com.unit.common.dto.Select2Dto;

/**
 * JcaAccountDto
 * 
 * @version 01-00
 * @since 01-00
 * @author trongcv
 */
public class Select2ResultDto {

	private List<Select2Dto> results;
	private int Total;
	public List<Select2Dto> getResults() {
		return results;
	}
	public void setResults(List<Select2Dto> results) {
		this.results = results;
	}
	public int getTotal() {
		return Total;
	}
	public void setTotal(int total) {
		Total = total;
	}
}
