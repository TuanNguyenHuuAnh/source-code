/*******************************************************************************
 * Class        ：AbstractCommonService
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：taitt
 * Change log   ：2020/12/09：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.dts.exception.DetailException;

/**
 * AbstractCommonService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface AbstractCommonService {

    JCommonService getCommonService();

    public default Pageable buildPageable(Pageable pageable, Class<?> clazz, String alias) throws DetailException {
        Sort sort = getCommonService().buildSortAlias(pageable.getSort(), clazz, alias);
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    public default <T extends Enum<T>> Pageable buildPageableByEnums(Pageable pageable, Class<?> clazz, String alias,
            T[] enumsDatas) throws DetailException {
        // Sort sortForClazz =
        // commonService.buildSortAliasNotUseDefault(pageable.getSort(),clazz, alias);
        Sort sortForEumns = getCommonService().buildSortEnums(pageable.getSort(), enumsDatas, clazz);

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortForEumns);
    }
}
