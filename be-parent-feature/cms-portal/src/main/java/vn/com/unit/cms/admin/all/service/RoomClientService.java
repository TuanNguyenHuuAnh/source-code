/*******************************************************************************
 * Class        ：RoomClientService
 * Created date ：2017/05/12
 * Lasted date  ：2017/05/12
 * Author       ：phunghn
 * Change log   ：2017/05/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.repository.query.Param;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.ChatControlDto;
import vn.com.unit.cms.admin.all.dto.ChatControlValueDto;
import vn.com.unit.cms.admin.all.dto.ChatControlValueLanguageDto;
import vn.com.unit.cms.admin.all.dto.ChatUserProductDto;
import vn.com.unit.cms.admin.all.dto.ChatUserProductSearchDto;
import vn.com.unit.cms.admin.all.dto.MessageDto;
import vn.com.unit.cms.admin.all.dto.ProductNodeDto;
import vn.com.unit.cms.admin.all.dto.RoomClientDto;
import vn.com.unit.cms.admin.all.dto.RoomClientOfflineDto;
import vn.com.unit.cms.admin.all.dto.RoomClientOfflineSearchDto;
import vn.com.unit.cms.admin.all.dto.RoomClientSearchDto;
import vn.com.unit.cms.admin.all.dto.SettingChatEditDto;
import vn.com.unit.cms.admin.all.dto.SettingChatLanguageDto;
import vn.com.unit.cms.admin.all.dto.UserInfoSupportDto;
import vn.com.unit.cms.admin.all.jcanary.dto.AccountAddDto;
import vn.com.unit.common.dto.PageWrapper;

/**
 * RoomClientService
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface RoomClientService {
    /**
     * findAllActive
     *
     * @param page
     * @param recDto
     * @return PageWrapper<RoomClientDto>
     * @author phunghn
     */
    public List<RoomClientDto> findAllActive(int page, RoomClientSearchDto recDto,Locale locale);
    
    /**
     * getListMessage
     *
     * @param clientid
     * @return List<MessageDto>
     * @author phunghn
     */
    public  List<MessageDto> getListMessage(@Param("clientid") String clientid,Locale locale);
    
    /**
     * getListClientOffline
     *
     * @param page
     * @param recDto
     * @param locale
     * @return
     * @author phunghn
     */
    public PageWrapper<RoomClientOfflineDto> getListClientOffline(int page, RoomClientOfflineSearchDto roomDto,Locale locale);
    
    /**
     * findOneClientOffline
     *
     * @param id
     * @return
     * @author phunghn
     */
    public RoomClientOfflineDto findOneClientOffline(Long id);
    
    /**
     * UpdateClientOffline
     *
     * @param item
     * @author phunghn
     */
    public void updateClientOffline(RoomClientOfflineDto item);
    
    /**
     * 
     * @param clientid
     * @return
     */
    public RoomClientDto findOneClient(String clientid);
    
    /**
     * get list control of online form     *
     * 
     * @param 
     * @author phunghn
     * @return
     */
    public List<SettingChatLanguageDto> getSettingFormOnline(int typeForm);
    
    public List<SettingChatLanguageDto> getSettingFormOffline(int typeForm);
    
    /**
     * get set init for chat setting form
     * @param mav
     * @author phunghn
     */
    public void initScreenSettingForm(ModelAndView mav);
    
    /**
     * edit config
     * @param settingDto
     * @return
     */
    public int EditConfig(SettingChatEditDto jsonSettingDto, SettingChatEditDto settingDto, int typeForm);
    
    public List<ChatControlDto> getListControl();
    
    
    public List<ChatControlValueLanguageDto> insertRowControlaValue(List<ChatControlValueLanguageDto> list, String field);
    
    List<ProductNodeDto> getListProduct(String id,String lang);
    
    List<AccountAddDto> getListUser();
    
    public List<ChatUserProductDto> getListUserByProductId(String id);
    
    public int saveUserProduct(String id, String user);
    
    public PageWrapper<ChatUserProductDto> getListUserProduct(int page, ChatUserProductSearchDto userProductDto,Locale locale);    
    
    public void deleteProduct(Long id);
    
    public PageWrapper<RoomClientDto> findHistoryAllActive(int page, RoomClientSearchDto recDto,Locale locale);
    
    
    public void exportHistoryToExcel(RoomClientSearchDto recDto, HttpServletResponse response,Locale locale) throws Exception;
    
    public String getMessageDefault(String lang);
    
    public int getSessionTimeout();
    
    public void updateInfoCustomer(RoomClientDto roomDto);
    
    public int countClientWaiting();
    
    public int getOnOffChat();
    
    public List<RoomClientDto> getAllRoomClientByAgent(String agent, Date date);
    
    public List<ChatControlValueDto> getListValueControls(String field, String lang);
    
    public void saveUnreadMessage(String clientid, Integer number);
    
    public Integer getUnreadMessage(String clientid);
    
    public MessageDto getLastMessage(String clientid, Integer type);
    
    public UserInfoSupportDto getUserInfoSupportDto(String clientid);
    
    public void exportListOfflineToExcel(RoomClientOfflineSearchDto typeSearch, HttpServletResponse response, Locale locale);
}
