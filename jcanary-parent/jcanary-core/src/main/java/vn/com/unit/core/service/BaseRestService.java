/*******************************************************************************
 * Class        ：BaseRestService
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：taitt
 * Change log   ：2020/12/08：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;

/**
 * <p>
 * BaseRestService
 * </p>
 * .
 *
 * @version 01-00
 * @param <T>
 *            the generic type
 * @param <E>
 *            the element type
 * @since 01-00
 * @author taitt
 */
public abstract interface BaseRestService <T extends ObjectDataRes<E>,E extends Object> {
    
    /**
     * <p>
     * Search.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link T}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    T search(MultiValueMap<String, String> commonSearch,Pageable pageable) throws DetailException;
    
    /**
     * <p>
     * Save.
     * </p>
     *
     * @param objectDto
     *            type {@link E}
     * @return {@link E}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    E save(E objectDto) throws DetailException;

    /**
     * <p>
     * Delete.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    void delete(Long id) throws DetailException;

    /**
     * <p>
     * Detail.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link E}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    E detail(Long id) throws DetailException;
}
