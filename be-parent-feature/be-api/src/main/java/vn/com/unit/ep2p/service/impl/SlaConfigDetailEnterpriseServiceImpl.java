/*******************************************************************************
 * Class        ：SlaConfigDetailEnterpriseServiceImpl
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：TrieuVD
 * Change log   ：2020/12/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.SlaConfigDetailAddReq;
import vn.com.unit.ep2p.dto.req.SlaConfigDetailUpdateReq;
import vn.com.unit.ep2p.service.SlaConfigDetailEnterpriseService;
import vn.com.unit.sla.dto.SlaConfigDetailDto;
import vn.com.unit.sla.dto.SlaConfigDetailSearchDto;
import vn.com.unit.sla.entity.SlaCalendarType;
import vn.com.unit.sla.service.SlaConfigDetailService;


@Service
@Transactional(rollbackFor = Exception.class)
public class SlaConfigDetailEnterpriseServiceImpl extends AbstractCommonService implements SlaConfigDetailEnterpriseService {

    @Autowired
    private SlaConfigDetailService slaConfigDetailService;

    @Override
    public SlaConfigDetailDto getSlaConfigDetailDtoById(Long id) throws DetailException {
        return slaConfigDetailService.getSlaConfigDetailDtoById(id);
    }

    @Override
    public ObjectDataRes<SlaConfigDetailDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<SlaConfigDetailDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, SlaCalendarType.class,
                    SlaConfigDetailEnterpriseService.TABLE_ALIAS_SLA_CONFIG_DETAIL);
            /** init param search repository */
            SlaConfigDetailSearchDto searchDto = this.buildSlaConfigDetailSearchDto(commonSearch);

            int totalData = slaConfigDetailService.countByCondition(searchDto);
            List<SlaConfigDetailDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = slaConfigDetailService.getSlaConfigDetailDtoListByCondition(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024101_APPAPI_SLA_CONFIG_DETAIL_LIST_ERROR);
        }
        return resObj;
    }

    @Override
    public SlaConfigDetailDto save(SlaConfigDetailDto objectDto) throws DetailException {
        return slaConfigDetailService.saveSlaConfigDetailDto(objectDto);
    }

    @Override
    public void delete(Long id) throws DetailException {
        boolean delete = slaConfigDetailService.deleteById(id);
        if (!delete) {
            throw new DetailException(AppApiExceptionCodeConstant.E4024102_APPAPI_SLA_CONFIG_DETAIL_DELETE_ERROR);
        }
    }

    @Override
    public SlaConfigDetailDto detail(Long id) throws DetailException {
        return this.getSlaConfigDetailDtoById(id);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.SlaConfigDetailEnterpriseService#createSlaConfigDetail(vn.com.unit.mbal.api.req.dto.SlaConfigDetailReq)
     */
    @Override
    public SlaConfigDetailDto createSlaConfigDetail(SlaConfigDetailAddReq slaConfigDetailReq) throws DetailException {
        SlaConfigDetailDto slaConfigDetailDto = objectMapper.convertValue(slaConfigDetailReq, SlaConfigDetailDto.class);
        return this.save(slaConfigDetailDto);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.SlaConfigDetailEnterpriseService#updateSlaConfigDetail(vn.com.unit.mbal.api.req.dto.SlaConfigDetailReq)
     */
    @Override
    public SlaConfigDetailDto updateSlaConfigDetail(SlaConfigDetailUpdateReq slaConfigDetailReq) throws DetailException {
        SlaConfigDetailDto slaConfigDetailDto = objectMapper.convertValue(slaConfigDetailReq, SlaConfigDetailDto.class);
        return this.save(slaConfigDetailDto);
    }

    /**
     * <p>
     * Builds the sla config detail search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link SlaConfigDetailSearchDto}
     * @author TrieuVD
     */
    private SlaConfigDetailSearchDto buildSlaConfigDetailSearchDto(MultiValueMap<String, String> commonSearch) {
        SlaConfigDetailSearchDto slaCalendarTypeSearchDto = new SlaConfigDetailSearchDto();
        Long slaConfigId = null != commonSearch.getFirst(SlaConfigDetailEnterpriseService.SEARCH_SLA_CONFIG_ID) ? Long.valueOf(commonSearch.getFirst(SlaConfigDetailEnterpriseService.SEARCH_SLA_CONFIG_ID)) : null;
        String alertType = null != commonSearch.getFirst(SlaConfigDetailEnterpriseService.SEARCH_ALERT_TYPE) ? commonSearch.getFirst(SlaConfigDetailEnterpriseService.SEARCH_ALERT_TYPE) : AppApiConstant.EMPTY;
        slaCalendarTypeSearchDto.setSlaConfigId(slaConfigId);
        slaCalendarTypeSearchDto.setAlertType(alertType);
        return slaCalendarTypeSearchDto;
    }
}
