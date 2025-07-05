/*******************************************************************************
 * Class        ：DayOffServiceImpl
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：TrieuVD
 * Change log   ：2020/12/23：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.Calendar;
import java.util.Date;
//import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.DayOffUpdateReq;
import vn.com.unit.ep2p.dto.res.DayOffInfoRes;
import vn.com.unit.ep2p.dto.res.DayOffListObjectRes;
import vn.com.unit.ep2p.service.DayOffService;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaCalendarSearchDto;
//import vn.com.unit.sla.service.SlaCalendarService;
import vn.com.unit.sla.utils.SlaDateUtils;

/**
 * DayOffServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DayOffServiceImpl extends AbstractCommonService implements DayOffService {

//    @Autowired
//    private SlaCalendarService slaDayOffService;

    public static final String SEARCH_YEAR = "year";
    public static final String SEARCH_CALENDAR_TYPE_ID = "calendarTypeId";

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.DayOffService#getListByCoditionDayOffSearchReq(org.springframework.util.MultiValueMap)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DayOffListObjectRes getDayOffListBySearchReq(MultiValueMap<String, String> requestParams) throws DetailException {
        DayOffListObjectRes listObjectRes = new DayOffListObjectRes();
        try {
            /** init param search repository */
            @SuppressWarnings("unused")
			SlaCalendarSearchDto searchDto = this.buildSlaDayOffSearchDto(requestParams);
//            List<SlaCalendarDto> dayOffList = slaDayOffService.getSlaDayOffListBySearchDto(searchDto);
//            listObjectRes.setTotalData(dayOffList.size());
//            listObjectRes.setDatas(dayOffList);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023402_APPAPI_DAY_OFF_LIST_ERROR);
        }
        return listObjectRes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.DayOffService#getSlaDayOffDtoById(java.lang.Long)
     */
    @Override
    public DayOffInfoRes getSlaDayOffDtoById(Long dayOffId) throws Exception {
        SlaCalendarDto slaDayOffDto = this.detail(dayOffId);
        return objectMapper.convertValue(slaDayOffDto, DayOffInfoRes.class);
    }

    /**
     * detail
     * @param dayOffId
     * @return
     * @author ngannh
     * @throws DetailException 
     */
    private SlaCalendarDto detail(Long dayOffId) throws Exception {
//        SlaCalendarDto slaDayOffDto = slaDayOffService.getSlaDayOffDtoById(dayOffId);;
//        if (null == slaDayOffDto) {
//            throw new DetailException(AppApiExceptionCodeConstant.E4023401_APPAPI_DAY_OFF_NOT_FOUND_ERROR, true);
//        }
//        return slaDayOffDto;
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.DayOffService#updateDateOff(vn.com.unit.mbal.api.req.dto.DayOffUpdateReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDateOff(DayOffUpdateReq reqDayOffUpdateDto) throws Exception {
        
//        SlaCalendarDto dayOffDto = objectMapper.convertValue(reqDayOffUpdateDto, SlaCalendarDto.class);
//        try {
//            slaDayOffService.saveSlaDayOffDto(dayOffDto);
//
//        }catch(Exception e) {
//            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023403_APPAPI_DAY_OFF_UPDATE_INFO_ERROR);
//        }
    }

    /**
     * <p>
     * Builds the sla day off search dto.
     * </p>
     *
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @return {@link SlaCalendarSearchDto}
     * @author TrieuVD
     */
    private SlaCalendarSearchDto buildSlaDayOffSearchDto(MultiValueMap<String, String> requestParams) {
        Calendar calendar = Calendar.getInstance();
        int year = null != requestParams.getFirst(SEARCH_YEAR) ? Integer.parseInt(requestParams.getFirst(SEARCH_YEAR))
                : calendar.get(Calendar.YEAR);
        Long calendarTypeId = null != requestParams.getFirst(SEARCH_CALENDAR_TYPE_ID)
                ? Long.valueOf(requestParams.getFirst(SEARCH_CALENDAR_TYPE_ID))
                : AppApiConstant.NUMBER_ZERO_L;
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date fromDate = SlaDateUtils.truncate(calendar.getTime(), Calendar.DATE);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        Date toDate = SlaDateUtils.truncate(calendar.getTime(), Calendar.DATE);
        
        SlaCalendarSearchDto searchDto = new SlaCalendarSearchDto();
        searchDto.setFromDate(fromDate);
        searchDto.setToDate(toDate);
        searchDto.setCalendarTypeId(calendarTypeId);
        return searchDto;
    }

}
