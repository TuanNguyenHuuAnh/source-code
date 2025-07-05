/*******************************************************************************
 * Class        ：SlaInvoledTypeService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.List;

import vn.com.unit.common.dto.Select2Dto;

/**
 * <p>
 * SlaInvoledTypeService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface SlaInvoledTypeService {
    
    /**
     * <p>
     * Get select 2 dto list by lang.
     * </p>
     *
     * @author TrieuVD
     * @param lang
     *            type {@link String}
     * @return {@link List<Select2Dto>}
     */
    public List<Select2Dto> getSelect2DtoListByLang(String lang);
}
