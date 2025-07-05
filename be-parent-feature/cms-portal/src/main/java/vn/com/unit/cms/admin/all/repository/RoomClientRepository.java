/*******************************************************************************
 * Class        ：RoomClientRepository
 * Created date ：2017/05/12
 * Lasted date  ：2017/05/12
 * Author       ：phunghn
 * Change log   ：2017/05/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ChatControlDto;
import vn.com.unit.cms.admin.all.dto.ChatControlValueDto;
import vn.com.unit.cms.admin.all.dto.ChatUserProductDto;
import vn.com.unit.cms.admin.all.dto.ChatUserProductSearchDto;
import vn.com.unit.cms.admin.all.dto.MessageDto;
import vn.com.unit.cms.admin.all.dto.ProductNodeDto;
import vn.com.unit.cms.admin.all.dto.RoomClientDto;
import vn.com.unit.cms.admin.all.dto.RoomClientOfflineDto;
import vn.com.unit.cms.admin.all.dto.RoomClientOfflineSearchDto;
import vn.com.unit.cms.admin.all.dto.RoomClientSearchDto;
import vn.com.unit.cms.admin.all.dto.SettingChatDto;
import vn.com.unit.cms.admin.all.dto.UserInfoSupportDto;
import vn.com.unit.cms.admin.all.entity.RoomClient;
import vn.com.unit.cms.admin.all.jcanary.dto.AccountAddDto;
import vn.com.unit.db.repository.DbRepository;

/**
 * RoomClientRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface RoomClientRepository extends DbRepository<RoomClient, Long> {

	@Modifying
	void insertSettingChat(@Param("field") String field, @Param("type") int type, @Param("sort") int sort,
			@Param("isDisplay") Boolean isDisplay, @Param("fieldType") String fieldType,
			@Param("username") String username);

	@Modifying
	void insertSettingChatLanguage(@Param("field") String field, @Param("language") String language,
			@Param("label") String label, @Param("placeHolder") String placeHolder,
			@Param("field_name") String fieldName, @Param("username") String username);

	int getNumber(@Param("type") int typeForm, @Param("typeString") String typeString);

	/**
	 * findAllActive
	 *
	 * @param offset
	 * @param sizeOfPage
	 * @param roomDto
	 * @return List<RoomClientDto>
	 * @author phunghn
	 */
	List<RoomClientDto> findAllActive(@Param("roomDto") RoomClientSearchDto roomDto, @Param("lang") String lang,
			@Param("isRoleAdmin") int isRoleAdmin, @Param("username") String username);

	/**
	 * countAllActive
	 *
	 * @param roomDto
	 * @return int
	 * @author phunghn
	 */
	int countAllActive(@Param("roomDto") RoomClientSearchDto roomDto);

	/**
	 * getListMessage
	 *
	 * @param clientid
	 * @return List<MessageDto>
	 * @author phunghn
	 */
	List<MessageDto> getListMessage(@Param("clientid") String clientid);

	/**
	 * getListClientOffline
	 *
	 * @param offset
	 * @param sizeOfPage
	 * @param roomDto
	 * @return List<RoomClientOfflineDto>
	 * @author phunghn
	 */
	List<RoomClientOfflineDto> getListClientOffline(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("roomDto") RoomClientOfflineSearchDto roomDto);

	/**
	 * countClientOffline
	 *
	 * @param roomDto
	 * @return
	 * @author phunghn
	 */
	int countClientOffline(@Param("roomDto") RoomClientOfflineSearchDto roomDto);

	/**
	 * findOneClientOffline
	 *
	 * @param id
	 * @return
	 * @author phunghn
	 */
	RoomClientOfflineDto findOneClientOffline(@Param("id") Long id);

	/**
	 * updateClientOffline
	 *
	 * @param item
	 * @author phunghn
	 */
	@Modifying
	void updateClientOffline(@Param("item") RoomClientOfflineDto item);

	/**
	 * 
	 * @param clientid
	 * @author phunghn
	 * @return
	 */
	RoomClientDto findOneClient(@Param("clientid") String clientid);

	/**
	 * 
	 * @return
	 */
	public List<SettingChatDto> findSettingForm(@Param("type") int type);

	@Modifying
	public void updateSettingChat(@Param("field") String field, @Param("sort") int sort,
			@Param("isDisplay") Boolean isDisplay, @Param("fieldType") String fieldType,
			@Param("username") String username);

	@Modifying
	public void updateSettingChatLanguage(@Param("field") String field, @Param("language") String language,
			@Param("label") String label, @Param("placeHolder") String placeHolder,
			@Param("field_name") String fieldName, @Param("username") String username);

	@Modifying
	public void deleteControlValueByField(@Param("field") String field);

	@Modifying
	public void insertControlValue(@Param("field") String field, @Param("language") String language,
			@Param("idField") String idField, @Param("valueField") String valueField,
			@Param("textField") String textField, @Param("username") String username);

	@Modifying
	public void updateVisibleByField(@Param("field") String field, @Param("isvisible") boolean isvisible,
			@Param("isDisplay") boolean isDisplay);

	public List<ChatControlDto> getListControl();

	public List<ChatControlValueDto> getListControlValue(@Param("field") String field);

	/**
	 * 
	 * @param lang
	 * @return
	 */
	public List<ProductNodeDto> findCustomerType(@Param("lang") String lang);

	/**
	 * 
	 * @param lang
	 * @return
	 */
	public List<ProductNodeDto> findProductCategory(@Param("lang") String lang, @Param("id") Long id);

	/***
	 * get list user
	 * 
	 * @return
	 */
	List<AccountAddDto> getListUser();

	/**
	 * get list
	 * 
	 * @param id
	 * @return
	 */
	public List<ChatUserProductDto> getListUserByProductId(@Param("list") List<Long> list);

	/**
	 * delete user by product-id
	 * 
	 * @param id
	 * @param user
	 */
	@Modifying
	void deleteUserByProductId(@Param("id") Long id, @Param("user") String user);

	/**
	 * insert user for reporting product
	 * 
	 * @param id
	 * @param user
	 * @param username
	 */
	@Modifying
	void inserUserProduct(@Param("id") Long id, @Param("user") String user, @Param("username") String username);

	/**
	 * count user for for reporting
	 * 
	 * @param userDto
	 * @param lang
	 * @return
	 */

	int countUserProductAllActive(@Param("userDto") ChatUserProductSearchDto userDto, @Param("lang") String lang);

	/**
	 * get user for for reporting
	 * 
	 * @param offset
	 * @param sizeOfPage
	 * @param userDto
	 * @param lang
	 * @return
	 */
	List<ChatUserProductDto> findAllUserProductActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("userDto") ChatUserProductSearchDto userDto, @Param("lang") String lang);

	/**
	 * get full name of user for reporting
	 * 
	 * @param id
	 * @return
	 */
	List<AccountAddDto> getFullNameByProductId(@Param("id") Long id);

	/**
	 * count history
	 * 
	 * @param roomDto
	 * @param isRoleAdmin
	 * @param userName
	 * @return
	 */
	int countHistoryAllActive(@Param("roomDto") RoomClientSearchDto roomDto, @Param("isRoleAdmin") int isRoleAdmin,
			@Param("userName") String userName);

	/**
	 * get list history
	 * 
	 * @param offset
	 * @param sizeOfPage
	 * @param roomDto
	 * @param isRoleAdmin
	 * @param userName
	 * @return
	 */
	List<RoomClientDto> findHistoryAllActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("roomDto") RoomClientSearchDto roomDto, @Param("isRoleAdmin") int isRoleAdmin,
			@Param("userName") String userName);

	/**
	 * get list for export
	 * 
	 * @param roomDto
	 * @param isRoleAdmin
	 * @param userName
	 * @return
	 */
	List<RoomClientDto> findHistoryAllActiveForExport(@Param("roomDto") RoomClientSearchDto roomDto,
			@Param("isRoleAdmin") int isRoleAdmin, @Param("userName") String userName);

	/**
	 * get message default
	 * 
	 * @param lang
	 * @return
	 */
	String getMessageDefault(@Param("lang") String lang);

	/**
	 * update info customer
	 * 
	 * @param roomDto
	 */
	@Modifying
	void updateInfoCustomer(@Param("roomDto") RoomClientDto roomDto);

	/**
	 * count client of waiting
	 * 
	 * @param isRoleAdmin
	 * @param username
	 * @return
	 */
	int countClientWaiting(@Param("isRoleAdmin") int isRoleAdmin, @Param("username") String username);

	List<RoomClientDto> findAllRoomClientByAgent(@Param("agent") String agent, @Param("dateSearch") Date date);

	List<ChatControlValueDto> findChatListValue(@Param("field") String field, @Param("lang") String language);

	@Modifying
	public void saveUnreadMessage(@Param("clientid") String clientid, @Param("number") Integer number);

	public Integer findNumberUnreadMessage(@Param("clientid") String clientid);

	public MessageDto findLastMessage(@Param("clientid") String clientid, @Param("type") Integer type);

	public UserInfoSupportDto findUserInfoSupportDto(@Param("clientid") String clientid);

	List<RoomClientOfflineDto> getListClientOfflineExcel(@Param("roomDto") RoomClientOfflineSearchDto roomDto);

}
