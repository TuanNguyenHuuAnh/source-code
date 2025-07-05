/*******************************************************************************
 * Class        ：ComboboxService
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：tantm
 * Change log   ：2021/01/28：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.dto.MultiSelectDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;

/**
 * <p>
 * ComboboxService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface ComboboxService {

    /**
     * <p>
     * Get list select 2 dto org.
     * </p>
     *
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<Select2Dto>}
     * @throws Exception
     *             the exception
     * @author tantm
     */
    ObjectDataRes<Select2Dto> getListSelect2DtoOrg(MultiValueMap<String, String> requestParams, Pageable pageable) throws Exception;

    /**
     * <p>
     * Get list select 2 dto status.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @param formId
     *            type {@link Long}
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<Select2Dto>}
     * @author tantm
     * @throws DetailException 
     */
    ObjectDataRes<Select2Dto> getListSelect2DtoStatus(Long processDeployId, Long formId , MultiValueMap<String, String> requestParams, Pageable pageable) throws DetailException;

    /**
     * <p>
     * Get list select 2 dto process.
     * </p>
     *
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<Select2Dto>}
     * @throws Exception
     *             the exception
     * @author tantm
     */
    ObjectDataRes<Select2Dto> getListSelect2DtoProcess(MultiValueMap<String, String> requestParams, Pageable pageable) throws Exception;

    /**
     * <p>
     * Get list select 2 dto form.
     * </p>
     *
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<Select2Dto>}
     * @throws Exception
     *             the exception
     * @author tantm
     */
    ObjectDataRes<Select2Dto> getListSelect2DtoForm(MultiValueMap<String, String> requestParams, Pageable pageable) throws Exception;

    /**
     * <p>
     * Get list select 2 dto constant display.
     * </p>
     *
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<Select2Dto>}
     * @throws Exception
     *             the exception
     * @author tantm
     */
    ObjectDataRes<Select2Dto> getListSelect2DtoConstantDisplay(MultiValueMap<String, String> requestParams, Pageable pageable)
            throws Exception;

    /**
     * getListSelect2DtoCategory.
     *
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<Select2Dto>}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    ObjectDataRes<Select2Dto> getListSelect2DtoCategory(MultiValueMap<String, String> requestParams, Pageable pageable) throws Exception;

    /**
     * <p>
     * Get list multi select dto team.
     * </p>
     *
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<MultiSelectDto>}
     * @throws DetailException
     *             the detail exception
     * @author tantm
     */
    ObjectDataRes<MultiSelectDto> getListMultiSelectDtoTeam(MultiValueMap<String, String> requestParams, Pageable pageable) throws DetailException;


}
