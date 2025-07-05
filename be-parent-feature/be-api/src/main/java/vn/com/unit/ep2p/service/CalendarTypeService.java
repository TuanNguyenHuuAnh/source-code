/*******************************************************************************
 * Class        ：CalendarTypeService
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：TrieuVD
 * Change log   ：2020/12/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.CalendarTypeAddReq;
import vn.com.unit.ep2p.dto.req.CalendarTypeUpdateReq;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;

/**
 * CalendarTypeService.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface CalendarTypeService extends BaseRestService<ObjectDataRes<SlaCalendarTypeDto>, SlaCalendarTypeDto> {

    /**
     * <p>
     * Creates the calendar type.
     * </p>
     *
     * @param reqCalendarTypeAddDto
     *            type {@link CalendarTypeAddReq}
     * @return {@link SlaCalendarTypeDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaCalendarTypeDto createCalendarType(CalendarTypeAddReq reqCalendarTypeAddDto) throws DetailException;

    /**
     * <p>
     * Update calendar type.
     * </p>
     *
     * @param reqCalendarTypeUpdateDto
     *            type {@link CalendarTypeUpdateReq}
     * @return {@link SlaCalendarTypeDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaCalendarTypeDto updateCalendarType(CalendarTypeUpdateReq reqCalendarTypeUpdateDto) throws DetailException;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    public List<EnumsParamSearchRes> getListEnumSearch();
    
}
