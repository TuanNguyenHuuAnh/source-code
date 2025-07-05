/*******************************************************************************
 * Class        ：CalendarTypeService
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：TrieuVD
 * Change log   ：2020/12/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import org.springframework.util.MultiValueMap;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.DayOffUpdateReq;
import vn.com.unit.ep2p.dto.res.DayOffListObjectRes;
import vn.com.unit.sla.dto.SlaCalendarDto;

/**
 * CalendarTypeService.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface DayOffService {

    /**
     * <p>
     * Get day off list by search req.
     * </p>
     *
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @return {@link DayOffListObjectRes}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public DayOffListObjectRes getDayOffListBySearchReq(MultiValueMap<String, String> requestParams) throws DetailException;
    
    /**
     * <p>
     * Get sla day off dto by id.
     * </p>
     *
     * @param dayOffId
     *            type {@link Long}
     * @return {@link SlaMDayOffDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaCalendarDto getSlaDayOffDtoById(Long dayOffId) throws Exception;
    
    /**
     * <p>
     * Update date off.
     * </p>
     *
     * @param reqDayOffUpdateDto
     *            type {@link DayOffUpdateReq}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     * @throws Exception 
     */
    public void updateDateOff(DayOffUpdateReq reqDayOffUpdateDto) throws Exception;
    
}
