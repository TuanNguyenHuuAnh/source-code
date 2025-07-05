/*******************************************************************************
 * Class        ：SlaUserTypeRepository
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：TrieuVD
 * Change log   ：2021/01/13：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.repository;

import java.util.List;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaInvoledTypeDto;
import vn.com.unit.sla.entity.SlaInvoledType;

/**
 * <p>
 * SlaUserTypeRepository
 * </p>
 * 
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaInvoledTypeRepository extends DbRepository<SlaInvoledType, Long> {

    /**
     * <p>
     * Get sla involed type dto list by lang.
     * </p>
     *
     * @author TrieuVD
     * @param lang
     *            type {@link String}
     * @return {@link List<SlaInvoledTypeDto>}
     */
    public List<SlaInvoledTypeDto> getSlaInvoledTypeDtoListByLang(String lang);
}