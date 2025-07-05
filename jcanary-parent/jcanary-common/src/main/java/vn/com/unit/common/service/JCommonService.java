/*******************************************************************************
 * Class        ：CommonService
 * Created date ：2020/11/12
 * Lasted date  ：2020/11/12
 * Author       ：KhoaNA
 * Change log   ：2020/11/12：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.service;

import java.util.Date;

import org.springframework.data.domain.Sort;

import vn.com.unit.dts.exception.DetailException;

/**
 * CommonService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface JCommonService {

    /**
     * Get system date.
     *
     * @return Date
     * @author KhoaNA
     */
    Date getSystemDate();

    /**
     * Generate code from id.
     *
     * @param id
     *            the id type Long
     * @return the string
     * @author tantm
     */
    String generateCodeFromId(Long id);

    /**
     * Generate code from id with format.
     *
     * @param id
     *            the id type Long
     * @param format
     *            the format type String
     * @return the string
     * @author tantm
     */
    String generateCodeFromIdWithFormat(Long id, String format);

    /**
     * <p>
     * Builds the sort alias.
     * </p>
     *
     * @param sort
     *            type {@link Sort}
     * @param clazz
     *            type {@link Class<?>}
     * @param alias
     *            type {@link String}
     * @return {@link Sort}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public Sort buildSortAlias(Sort sort, Class<?> clazz, String alias) throws DetailException;

    /**
     * buildSortAliasNotUseDefault.
     *
     * @param sort
     *            type {@link Sort}
     * @param clazz
     *            type {@link Class<?>}
     * @param alias
     *            type {@link String}
     * @return {@link Sort}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    Sort buildSortAliasNotUseDefault(Sort sort, Class<?> clazz, String alias) throws DetailException;

    /**
     * buildSortEnums
     * @param <T>
     * @param sort
     * @param enumsDatas
     * @return
     * @throws DetailException
     * @author taitt
     */
    <T extends Enum<T>> Sort buildSortEnums(Sort sort, T[] enumsDatas,Class<?> clazz) throws DetailException;
    
    
    <T extends Enum<T>> Sort buildSortEnums(Sort sort) throws DetailException;
}
