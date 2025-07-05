/*******************************************************************************
 * Class        ：AppNotiTemplateRepository
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.repository.JcaNotiTemplateRepository;

/**
 * <p>
 * AppNotiTemplateRepository
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface AppNotiTemplateRepository extends JcaNotiTemplateRepository{

    /**
     * <p>
     * Get select 2 dto list.
     * </p>
     *
     * @author TrieuVD
     * @param keySearch
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @param isPaging
     *            type {@link boolean}
     * @return {@link List<Select2Dto>}
     */
    public List<Select2Dto> getSelect2DtoList(@Param("keySearch") String keySearch, @Param("companyId") Long companyId, @Param("isPaging") boolean isPaging);
}
