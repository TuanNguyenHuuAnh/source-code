/*******************************************************************************
 * Class        ：EmailTemplateService
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：SonND
 * Change log   ：2020/12/23：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.dto.JcaEmailTemplateSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.EmailTemplateAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.EmailTemplateUpdateInfoReq;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.res.EmailTemplateInfoRes;

/**
 * EmailTemplateService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface EmailTemplateService extends BaseRestService<ObjectDataRes<JcaEmailTemplateDto>, JcaEmailTemplateDto>{
    

    /**
     * <p>
     * Count jca email dto by condition.
     * </p>
     *
     * @param jcaEmailTemplateSearchDto
     *            type {@link JcaEmailTemplateSearchDto}
     * @return {@link int}
     * @author SonND
     */
    int countJcaEmailDtoByCondition(JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto);

    /**
     * <p>
     * Gets the jca email template dto by condition.
     * </p>
     *
     * @param jcaEmailTemplateSearchDto
     *            type {@link JcaEmailTemplateSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the jca email template dto by condition
     * @author SonND
     */
    List<JcaEmailTemplateDto> getJcaEmailTemplateDtoByCondition(JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto, Pageable pageable);

    /**
     * <p>
     * Update.
     * </p>
     *
     * @param emailTemplateUpdateInfoReq
     *            type {@link EmailTemplateUpdateInfoReq}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    void update(EmailTemplateUpdateInfoReq emailTemplateUpdateInfoReq) throws DetailException;

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param emailTemplateAddInfoReq
     *            type {@link EmailTemplateAddInfoReq}
     * @return {@link EmailTemplateInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    EmailTemplateInfoRes create(EmailTemplateAddInfoReq emailTemplateAddInfoReq) throws DetailException;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    List<EnumsParamSearchRes> getListEnumSearch();

   

}
