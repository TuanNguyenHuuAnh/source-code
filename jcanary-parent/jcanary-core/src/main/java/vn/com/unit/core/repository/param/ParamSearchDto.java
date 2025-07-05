/*******************************************************************************
 * Class        ：ParamSearchDto
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：taitt
 * Change log   ：2020/12/08：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.repository.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ParamSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@NoArgsConstructor
public class ParamSearchDto<T> {

    /** free text - search basic */
    private String keySearch;
    
    /** object conditionsSearch */
    private T conditionsSearch;

    /** @param conditionsSearch
     * @author KhuongTH
     */
    public ParamSearchDto(T conditionsSearch) {
        super();
        this.conditionsSearch = conditionsSearch;
    }
}
