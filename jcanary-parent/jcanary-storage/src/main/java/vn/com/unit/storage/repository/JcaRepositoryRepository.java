/*******************************************************************************
 * Class        ：JcaRepositoryRepository
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.JcaRepositorySearchDto;
import vn.com.unit.storage.entity.JcaRepository;

/**
 * JcaRepositoryRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaRepositoryRepository extends DbRepository<JcaRepository, Long> {

    /**
     * Count list jca repository by condition.
     *
     * @param jcaRepositorySearchDto
     *            the jca repository search dto type JcaRepositorySearchDto
     * @return the int
     * @author tantm
     */
    int countListJcaRepositoryByCondition(@Param("jcaRepositorySearchDto") JcaRepositorySearchDto jcaRepositorySearchDto);

    /**
     * Gets the list jca repository dto by condition.
     *
     * @param jcaRepositorySearchDto
     *            the jca repository search dto type JcaRepositorySearchDto
     * @param offset
     *            the offset type int
     * @param pageSize
     *            the size of page type int
     * @return the list jca repository dto by condition
     * @author tantm
     */
    List<JcaRepositoryDto> getListJcaRepositoryDtoByCondition(
            @Param("jcaRepositorySearchDto") JcaRepositorySearchDto jcaRepositorySearchDto, @Param("offset") long offset,
            @Param("pageSize") int pageSize);

    /**
     * <p>
     * Count jca repository by code.
     * </p>
     *
     * @param code
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link int}
     * @author tantm
     */
    int countJcaRepositoryByCode(@Param("code") String code, @Param("companyId") Long companyId);
    
    public JcaRepositoryDto findJcaRepositoryByCode(@Param("code") String code, @Param("companyId") Long companyId);

    /**
     * @author VuNT
     * @param searchDto
     * @return
     */
	List<JcaRepositoryDto> getListJcaRepositoryDtoByParam(@Param("jcaRepositorySearchDto")JcaRepositorySearchDto searchDto);

}
