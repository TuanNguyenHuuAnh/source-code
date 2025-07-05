/*******************************************************************************
 * Class        ：Select2ResultInfoDto
 * Created date ：2020/09/07
 * Lasted date  ：2020/09/07
 * Author       ：DaiTrieu
 * Change log   ：2020/09/07：01-00 DaiTrieu create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import vn.com.unit.common.dto.Select2Dto;



/**
 * Select2ResultInfoDto
 * 
 * @version 01-00
 * @since 01-00
 * @author DaiTrieu
 */
public class Select2ResultInfoDto<T extends Select2Dto> {

    private List<T> results;

    private int total;

    /**
     * Get results
     * 
     * @return List<T>
     * @author DaiTrieu
     */
    public List<T> getResults() {
        return results;
    }

    /**
     * Set results
     * 
     * @param results
     *            type List<T>
     * @return
     * @author DaiTrieu
     */
    public void setResults(List<T> results) {
        this.results = results;
    }

    /**
     * Get total
     * 
     * @return int
     * @author DaiTrieu
     */
    public int getTotal() {
        return total;
    }

    /**
     * Set total
     * 
     * @param total
     *            type int
     * @return
     * @author DaiTrieu
     */
    public void setTotal(int total) {
        this.total = total;
    }

}
