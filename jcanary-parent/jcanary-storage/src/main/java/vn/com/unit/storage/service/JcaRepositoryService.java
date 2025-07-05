/*******************************************************************************
 * Class        ：JcaRepositoryService
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.JcaRepositorySearchDto;
import vn.com.unit.storage.entity.JcaRepository;

/**
 * JcaRepositoryService.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaRepositoryService {

    /** The Constant TABLE_ALIAS_JCA_REPOSITORY. */
    static final String TABLE_ALIAS_JCA_REPOSITORY = "repo";

    /**
     * Save jca repository dto.
     *
     * @param objectDto
     *            the object dto type JcaRepositoryDto
     * @return the jca repository
     * @throws DetailException
     *             the detail exception
     * @author tantm
     */
    JcaRepository saveJcaRepositoryDto(JcaRepositoryDto objectDto) throws DetailException;

    /**
     * Save jca repository.
     *
     * @param objectEntity
     *            the object entity type JcaRepository
     * @return the jca repository
     * @author tantm
     */
    JcaRepository saveJcaRepository(JcaRepository objectEntity);

    /**
     * Gets the jca repository by id.
     *
     * @param id
     *            the id type Long
     * @return the jca repository by id
     * @author tantm
     */
    JcaRepository getJcaRepositoryById(Long id);

    /**
     * Gets the jca repository dto by id.
     *
     * @param id
     *            the id type Long
     * @return the jca repository dto by id
     * @author tantm
     */
    JcaRepositoryDto getJcaRepositoryDtoById(Long id);

    /**
     * Count list jca repository by condition.
     *
     * @param jcaRepositorySearchDto
     *            the jca repository search dto type JcaRepositorySearchDto
     * @return the int
     * @author tantm
     */
    int countListJcaRepositoryByCondition(JcaRepositorySearchDto jcaRepositorySearchDto);

    /**
     * Gets the list jca repository dto by condition.
     *
     * @param jcaRepositorySearchDto
     *            the jca repository search dto type JcaRepositorySearchDto
     * @param pageable
     *            the pageable type Pageable
     * @return the list jca repository dto by condition
     * @author tantm
     */
    List<JcaRepositoryDto> getListJcaRepositoryDtoByCondition(JcaRepositorySearchDto jcaRepositorySearchDto, Pageable pageable);

    /**
     * <p>
     * Check group code exists.
     * </p>
     *
     * @param code
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return true, if successful
     * @author tantm
     */
    boolean checkGroupCodeExists(String code, Long companyId);
    
    public JcaRepositoryDto getJcaRepositoryDto(String code, Long companyId);

    /**
     * @author VuNT
     * @param searchDto
     * @return
     */
	List<JcaRepositoryDto> getListJcaRepositoryDtoByParam(JcaRepositorySearchDto searchDto);

}
