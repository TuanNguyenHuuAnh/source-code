package vn.com.unit.ep2p.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import com.google.zxing.WriterException;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestSearchDto;
import vn.com.unit.cms.core.module.events.dto.EventsMasterDataDto;
import vn.com.unit.cms.core.module.events.dto.EventsSearchDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayDto;
import vn.com.unit.ep2p.admin.dto.CustomerChargeDto;
import vn.com.unit.ep2p.admin.dto.EventDetailDto;

public interface EventsService {

	public EventsDto editOrAddEvents(EventsDto dto) throws WriterException, IOException, SQLException;

    CmsCommonPagination<EventsDto> getListEventByDate(Date eventDate, Integer page, Integer pageSize, CommonSearchWithPagingDto search);

    CmsCommonPagination<CustomerBirthdayDto> getListCustomerBirthDay(Date eventDate, Integer page, Integer pageSize, String customerRole, CommonSearchWithPagingDto searchDto);

    CmsCommonPagination<CustomerChargeDto> getListCustomerChargeByDate(Date eventDate, Integer page, Integer pageSize, String status, CommonSearchWithPagingDto searchDto);

    List<EventDetailDto> getAllEventsInMonth(Date eventDate);

    CustomerInformationDetailDto getDetailCustomer(String customerId);
    
    List<EventsMasterDataDto> getListMasterData(String type, String parentId, String code);
    
    CmsCommonPagination<EventsDto> getListEventsByCondition(EventsSearchDto searchDto);
    
    EventsDto getDetailEvent(String eventCode, String createBy);
    
    EventsDto getEventByCode(String eventCode);
    
    void updateEvent(EventsDto dto) throws DetailException, SQLException;
    
    void deleteEventById(Long id, String updateBy) throws DetailException;
    
    byte[] readQrCode(String fileName) throws IOException;
    
    EventsDto getParticipantInfo(String eventCode);
    
    CmsCommonPagination<EventsGuestDetailDto> getListGuestsOfEvent(EventsGuestSearchDto searchDto);
    
    EventsGuestDetailDto addGuest(EventsGuestDetailDto dto);
    
    int updateGuest(EventsGuestDetailDto dto);
    
    boolean checkProcessing(Long id);
    
    ResponseEntity exportListEvents(EventsSearchDto searchDto, HttpServletResponse response, Locale locale);
    
    ResponseEntity exportListGuestsOfEvent(EventsGuestSearchDto searchDto, HttpServletResponse response, Locale locale);
}
