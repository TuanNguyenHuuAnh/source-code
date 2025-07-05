/*******************************************************************************
 * Class        ：FormService
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.ep2p.core.dto.EfoFormRegisterRes;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormSearchDto;
import vn.com.unit.ep2p.dto.req.FormRegisterReq;
import vn.com.unit.ep2p.dto.req.FormUpdateReq;
import vn.com.unit.ep2p.dto.res.FormInfoRes;
import vn.com.unit.ep2p.dto.res.FormRegisterResultRes;

/**
 * FormService.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface FormService extends BaseRestService<ObjectDataRes<EfoFormDto>, EfoFormDto>{

    /**
     * getEfoFormDtoByCondition.
     *
     * @param efoFormSearchDto
     *            type {@link EfoFormSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return {@link List<EfoFormDto>}
     * @author taitt
     */
    List<EfoFormDto> getEfoFormDtoByCondition(EfoFormSearchDto efoFormSearchDto, Pageable pagable);

    /**
     * countEfoFormByCondition.
     *
     * @param efoFormSearchDto
     *            type {@link EfoFormSearchDto}
     * @return {@link int}
     * @author taitt
     */
    int countEfoFormByCondition(EfoFormSearchDto efoFormSearchDto);

    /**
     * getFormInfoResById.
     *
     * @param id
     *            type {@link Long}
     * @return {@link FormInfoRes}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    FormInfoRes getFormInfoResById(Long id) throws Exception;
    
    /**
     * update.
     *
     * @param formUpdateReq
     *            type {@link FormUpdateReq}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    void update(FormUpdateReq formUpdateReq) throws Exception;

    /**
     * getFormRegisterDtoByCondition
     * @param commonSearch
     * @return
     * @throws Exception
     * @author taitt
     */
    EfoFormRegisterRes getFormRegisterDtoByCondition(MultiValueMap<String, String> commonSearch) throws Exception;

    /**
     * registerForm
     * @param formRegisterReq
     * @return
     * @throws Exception
     * @author taitt
     */
    FormRegisterResultRes registerForm(FormRegisterReq formRegisterReq) throws Exception;

}
