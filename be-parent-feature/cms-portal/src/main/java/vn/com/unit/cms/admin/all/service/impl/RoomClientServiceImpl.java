/*******************************************************************************
 * Class        ：RoomClientImpl
 * Created date ：2017/05/12
 * Lasted date  ：2017/05/12
 * Author       ：phunghn
 * Change log   ：2017/05/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
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
import vn.com.unit.cms.admin.all.dto.SettingChatDto;
import vn.com.unit.cms.admin.all.dto.SettingChatEditDto;
import vn.com.unit.cms.admin.all.dto.SettingChatLanguageDto;
import vn.com.unit.cms.admin.all.dto.UserInfoSupportDto;
import vn.com.unit.cms.admin.all.entity.RoleForChat;
import vn.com.unit.cms.admin.all.enumdef.ChatUserProductSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.RoomClientEnum;
import vn.com.unit.cms.admin.all.enumdef.RoomClientOfflineEnum;
import vn.com.unit.cms.admin.all.enumdef.RoomClientOfflineSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.RoomClientSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.SettingChatEnumType;
import vn.com.unit.cms.admin.all.jcanary.common.util.ConstantImport;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.constant.RoleConstant;
import vn.com.unit.cms.admin.all.jcanary.dto.AccountAddDto;
import vn.com.unit.cms.admin.all.repository.RoleForChatRepository;
import vn.com.unit.cms.admin.all.repository.RoomClientRepository;
import vn.com.unit.cms.admin.all.service.RoleForChatService;
import vn.com.unit.cms.admin.all.service.RoomClientService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.LanguageDto;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.util.ConstantImport;
import vn.com.unit.ep2p.core.utils.Utility;
//import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
//import vn.com.unit.ep2p.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;

/**
 * RoomClientImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoomClientServiceImpl implements RoomClientService {

	@Autowired
	RoomClientRepository roomClientRespository;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private MessageSource msg;

	@Autowired
	private RoleForChatService roleForChatService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	ServletContext servletContext;

	@Autowired
	private RoleForChatRepository roleForChatRepository;

	private static final Logger logger = LoggerFactory.getLogger(RoomClientServiceImpl.class);

	/**
	 * findAllActive
	 *
	 * @param page
	 * @param recDto
	 * @return PageWrapper<RoomClientDto>
	 * @author phunghn
	 */
	@Override
	public List<RoomClientDto> findAllActive(int page, RoomClientSearchDto roomDto, Locale locale) {
		int isRoleAdmin = 0;

		String username = UserProfileUtils.getUserNameLogin();
		if (roleForChatService.isRoleAdminChat(username)) {
			isRoleAdmin = 1;
		}

		RoleForChat roleForChat = roleForChatRepository
				.findRoleForChatByRoleAndUsername(CmsRoleConstant.ROLE_ADMIN.toString(), username);

		if (roleForChat != null) {
			isRoleAdmin = 1;
		}

		String userName = UserProfileUtils.getUserNameLogin();
//        setSearchParam(roomDto);
		List<RoomClientDto> list = new ArrayList<RoomClientDto>();
		list = roomClientRespository.findAllActive(roomDto, locale.toString(), isRoleAdmin, userName);
		if (list != null) {
			for (RoomClientDto item : list) {
				String statusName = "";
				if (item.getStatus() == 0) { // finish
					statusName = msg.getMessage("chat.list.status.close", null, locale);
				} else if (item.getStatus() == -1) { // cancel
					statusName = msg.getMessage("chat.list.status.cancel", null, locale);
				} else if (item.getStatus() == 1) { // chating
					statusName = msg.getMessage("chat.list.status.chating", null, locale);
				} else if (item.getStatus() == 2) { // waiting
					statusName = msg.getMessage("chat.list.status.waiting", null, locale);
				}

				item.setStatusName(statusName);
			}
		}
		return list;
	}

	/**
	 * getListMessage
	 *
	 * @param clientid
	 * @return List<MessageDto>
	 * @author phunghn
	 */
	@Override
	public List<MessageDto> getListMessage(String clientid, Locale locale) {
		List<MessageDto> list = new ArrayList<MessageDto>();
		list = roomClientRespository.getListMessage(clientid);
		for (MessageDto item : list) {
			String typeName = "";
			if (item.getType() == 1) {
				typeName = msg.getMessage("chat.list.type.client", null, locale);
			}
			item.setTypeName(typeName);
		}
		return list;
	}

	/**
	 * getListClientOffline
	 *
	 * @param page
	 * @param recDto
	 * @param locale
	 * @return
	 * @author phunghn
	 */
	private void setSearchParamRoomClientOffline(RoomClientOfflineSearchDto roomDto) {

		if (roomDto.getFieldValues() != null && roomDto.getFieldValues().size() > 0) {
			for (String item : roomDto.getFieldValues()) {
				if (item.equals(RoomClientOfflineSearchEnum.EMAIL.name())) {
					roomDto.setEmail(roomDto.getFieldSearch().trim());
				} else if (item.equals(RoomClientOfflineSearchEnum.FULLNAME.name())) {
					roomDto.setFullname(roomDto.getFieldSearch().trim());
				} else if (item.equals(RoomClientOfflineSearchEnum.PHONE.name())) {
					roomDto.setPhone(roomDto.getFieldSearch().trim());
				}
			}
		} else if (roomDto.getFieldSearch() != null) {
			roomDto.setEmail(roomDto.getFieldSearch().trim());
			roomDto.setFullname(roomDto.getFieldSearch().trim());
			roomDto.setPhone(roomDto.getFieldSearch().trim());
		}
	}

	/**
	 * getListClientOffline
	 *
	 * @param page
	 * @param recDto
	 * @param locale
	 * @return
	 * @author phunghn
	 */
	@Override
	public PageWrapper<RoomClientOfflineDto> getListClientOffline(int page, RoomClientOfflineSearchDto roomDto,
			Locale locale) {

		int sizeOfPage = roomDto.getPageSize() != null ? roomDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

		PageWrapper<RoomClientOfflineDto> pageWrapper = new PageWrapper<RoomClientOfflineDto>(page, sizeOfPage);
		setSearchParamRoomClientOffline(roomDto);
		roomDto.setLanguage(locale.toString());

		int countRoomClient = roomClientRespository.countClientOffline(roomDto);

		if ((countRoomClient % sizeOfPage == 0 && page > countRoomClient / sizeOfPage)
				|| (countRoomClient % sizeOfPage > 0 && page - 1 > countRoomClient / sizeOfPage)) {
			page = 1;
		}

		List<RoomClientOfflineDto> list = new ArrayList<RoomClientOfflineDto>();
		if (countRoomClient > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			list = roomClientRespository.getListClientOffline(offsetSQL, sizeOfPage, roomDto);

		}
		pageWrapper.setDataAndCount(list, countRoomClient);
		return pageWrapper;
	}

	/**
	 * findOneClientOffline
	 *
	 * @param id
	 * @return
	 * @author phunghn
	 */
	@Override
	public RoomClientOfflineDto findOneClientOffline(Long id) {
		return roomClientRespository.findOneClientOffline(id);
	}

	/**
	 * UpdateClientOffline
	 *
	 * @param item
	 * @author phunghn
	 */
	@Override
	@Transactional
	public void updateClientOffline(RoomClientOfflineDto item) {
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
		item.setUpdatedDate(new Date());
		item.setUpdatedBy(UserProfileUtils.getUserNameLogin());
		roomClientRespository.updateClientOffline(item);
	}

	@Override
	public RoomClientDto findOneClient(String clientid) {
		RoomClientDto item = new RoomClientDto();
		item = roomClientRespository.findOneClient(clientid);
		return item;
	}

	@Override
	public List<SettingChatLanguageDto> getSettingFormOnline(int typeForm) {
		List<SettingChatDto> listSettingChat = roomClientRespository.findSettingForm(typeForm);
		// append data
		List<SettingChatDto> listMesgSettingChat = roomClientRespository
				.findSettingForm(Integer.parseInt(SettingChatEnumType.MESSAGE.toString()));
		listSettingChat.addAll(listMesgSettingChat);
		return buildForm(listSettingChat);
	}

	@Override
	public List<SettingChatLanguageDto> getSettingFormOffline(int typeForm) {
		List<SettingChatDto> listSettingChat = roomClientRespository.findSettingForm(typeForm);
		return buildForm(listSettingChat);
	}

	private List<SettingChatLanguageDto> buildForm(List<SettingChatDto> listSettingChat) {
		// list control
		List<ChatControlDto> controls = roomClientRespository.getListControl();
		//
		List<SettingChatLanguageDto> listSettingChatLanguage = new ArrayList<SettingChatLanguageDto>();
		List<LanguageDto> languageList = languageService.getLanguageDtoList();

		// order data
		List<SettingChatDto> listSettingChatTemp = new ArrayList<SettingChatDto>();
		for (SettingChatDto item : listSettingChat) {
			if (item.getIsVisible() == true) {
				listSettingChatTemp.add(item);
			}
		}
		for (SettingChatDto item : listSettingChat) {
			if (item.getIsVisible() == false) {
				listSettingChatTemp.add(item);
			}
		}
		listSettingChat = listSettingChatTemp;
		// set list
		for (LanguageDto itemLang : languageList) {
			// get form by language
			SettingChatLanguageDto itemSettingChatLanguageDto = new SettingChatLanguageDto();
			itemSettingChatLanguageDto.setmLanguageCode(itemLang.getCode());
			List<SettingChatDto> listSettingFormLanguage = new ArrayList<SettingChatDto>();
			for (SettingChatDto itemSetting : listSettingChat) {
				if (itemLang.getCode().trim().equals(itemSetting.getmLanguageCode())) {
					SettingChatDto itemSettingChatDto = new SettingChatDto();
					itemSettingChatDto.setFieldCode(itemSetting.getFieldCode());
					itemSettingChatDto.setFieldName(itemSetting.getFieldName());
					itemSettingChatDto.setSort(itemSetting.getSort());
					itemSettingChatDto.setType(itemSetting.getType());
					itemSettingChatDto.setIsDisplay(itemSetting.getIsDisplay());
					itemSettingChatDto.setIsVisible(itemSetting.getIsVisible());
					itemSettingChatDto.setLabelName(itemSetting.getLabelName());
					itemSettingChatDto.setPlaceholderName(itemSetting.getPlaceholderName());
					itemSettingChatDto.setControls(
							getValueOfControl(itemSetting.getFieldCode(), itemSetting.getFieldType(), controls));
					itemSettingChatDto.setFieldType(itemSetting.getFieldType());
					itemSettingChatDto.setIsSetData(checkIsSetDataControl(itemSetting.getFieldType(), controls));
					itemSettingChatDto
							.setIsDefault(itemSetting.getIsDefault() == null ? false : itemSetting.getIsDefault());
					listSettingFormLanguage.add(itemSettingChatDto);
				}
			}
			itemSettingChatLanguageDto.setListControls(listSettingFormLanguage);
			listSettingChatLanguage.add(itemSettingChatLanguageDto);
		}
		return listSettingChatLanguage;
	}

	private static int checkIsSetDataControl(String typeControl, List<ChatControlDto> controls) {
		for (ChatControlDto item : controls) {
			if (item.getCode().equals(typeControl) && item.getIsSetData() == 1) {
				return 1;
			}
		}
		return 0;
	}

	public List<ChatControlDto> getValueOfControl(String field, String fieldType, List<ChatControlDto> controls) {
		List<ChatControlDto> listControl = new ArrayList<ChatControlDto>();
		List<LanguageDto> languageList = languageService.getLanguageDtoList();
		List<ChatControlValueLanguageDto> listLanaguageControlValue = new ArrayList<ChatControlValueLanguageDto>();
		List<ChatControlValueDto> listControlValue = new ArrayList<ChatControlValueDto>();
		listControlValue = roomClientRespository.getListControlValue(field);
		for (LanguageDto itemLang : languageList) {
			ChatControlValueLanguageDto itemAdd = new ChatControlValueLanguageDto();
			itemAdd.setmLanguageCode(itemLang.getCode());
			List<ChatControlValueDto> controlValues = new ArrayList<ChatControlValueDto>();
			for (ChatControlValueDto itemValue : listControlValue) {
				if (itemValue.getmLanguageCode().equals(itemLang.getCode())) {
					ChatControlValueDto itemControlValueAdd = new ChatControlValueDto();
					itemControlValueAdd.setId(itemValue.getId());
					itemControlValueAdd.setIdField(itemValue.getIdField());
					itemControlValueAdd.setFieldCode(itemValue.getFieldCode());
					itemControlValueAdd.setValueField(itemValue.getValueField());
					itemControlValueAdd.setTextField(itemValue.getTextField());
					itemControlValueAdd.setmLanguageCode(itemLang.getCode());
					controlValues.add(itemControlValueAdd);
				}
			}
			itemAdd.setListControlValue(controlValues);
			listLanaguageControlValue.add(itemAdd);
		}
		// set list to controls
		for (ChatControlDto item : controls) {
			ChatControlDto itemAdd = new ChatControlDto();
			itemAdd.setCode(item.getCode());
			itemAdd.setControlName(item.getControlName());
			itemAdd.setIsSetData(item.getIsSetData());
			if (item.getCode().equals(fieldType)) {
				itemAdd.setControlValues(listLanaguageControlValue);
			}
			listControl.add(itemAdd);
		}
		return listControl;
	}

	@Override
	public void initScreenSettingForm(ModelAndView mav) {
		List<LanguageDto> languageList = languageService.getLanguageDtoList();
		mav.addObject("languageList", languageList);
		mav.addObject("controlList", roomClientRespository.getListControl());
	}

	@Override
	@Transactional
	public int EditConfig(SettingChatEditDto jsonSettingDto, SettingChatEditDto settingDto, int typeForm) {
		try {
			// list control
			List<ChatControlDto> controls = roomClientRespository.getListControl();

			String username = UserProfileUtils.getUserNameLogin();

			Map<Integer, String> mapField = new HashMap<Integer, String>();

			for (int i = 0; i < settingDto.getListSettingLanguageChat().size(); i++) {
				SettingChatLanguageDto iSettingLanguageDto = settingDto.getListSettingLanguageChat().get(i);
				for (int j = 0; j < iSettingLanguageDto.getListControls().size(); j++) {
					SettingChatDto iSettingCharDto = iSettingLanguageDto.getListControls().get(j);
					// update table m_setting_chat
					int sort = iSettingCharDto.getSort();
					Boolean isDisplay = iSettingCharDto.getIsDisplay() == null ? false : iSettingCharDto.getIsDisplay();
					String field = iSettingCharDto.getFieldCode();
					if (field == null || field.equals("")) {
						if (mapField.get(j) == null) {
							if (typeForm == 0) {
								field = "OCLTMP" + roomClientRespository.getNumber(typeForm, "OCLTMP");
							} else {
								field = "OFFCLTMP" + roomClientRespository.getNumber(typeForm, "OFFCLTMP");
							}
							mapField.put(j, field);
							roomClientRespository.insertSettingChat(field, typeForm, sort, isDisplay,
									iSettingCharDto.getFieldType(), username);
						} else {
							field = mapField.get(j);
						}
						// insert table m_setting_chat_language
						String lang = iSettingLanguageDto.getmLanguageCode();
						String labelName = iSettingCharDto.getLabelName();
						String placeHolderName = iSettingCharDto.getPlaceholderName();
						String fieldName = iSettingCharDto.getFieldName();
						roomClientRespository.insertSettingChatLanguage(field, lang, labelName, placeHolderName,
								fieldName, username);
					} else {
						roomClientRespository.updateSettingChat(field, sort, isDisplay, iSettingCharDto.getFieldType(),
								username);
						// update table m_setting_chat_language
						String lang = iSettingLanguageDto.getmLanguageCode();
						String labelName = iSettingCharDto.getLabelName();
						String placeHolderName = iSettingCharDto.getPlaceholderName();
						String fieldName = iSettingCharDto.getFieldName();
						roomClientRespository.updateSettingChatLanguage(field, lang, labelName, placeHolderName,
								fieldName, username);
					}
				}
			}
			SettingChatLanguageDto iSettingLanguageDto = settingDto.getListSettingLanguageChat().get(0);
			SettingChatLanguageDto jsonSettingLanguageDto = jsonSettingDto.getListSettingLanguageChat().get(0);
			// insert value of control
			for (SettingChatDto iSettingCharDto : iSettingLanguageDto.getListControls()) {
				// delete old value in table m_setting_chat_control_value
				roomClientRespository.deleteControlValueByField(iSettingCharDto.getFieldCode());
				// update data with field
				if (checkIsSetDataControl(iSettingCharDto.getFieldType(), controls) == 1) {
					// get data in jsonSettingDto
					for (SettingChatDto jsonSettingCharDto : jsonSettingLanguageDto.getListControls()) {

						if (jsonSettingCharDto != null
								&& iSettingCharDto.getFieldCode().equals(jsonSettingCharDto.getFieldCode())) {
							// insert data to m_setting_chat_control_value
							// check fieldType in jsonSettingDto
							for (ChatControlDto itemControlDto : jsonSettingCharDto.getControls()) {
								if (itemControlDto.getControlValues() != null) {
									if (itemControlDto.getCode().equals(iSettingCharDto.getFieldType())) {
										// update by control
										for (ChatControlValueLanguageDto itemControlValueLang : itemControlDto
												.getControlValues()) {
											for (ChatControlValueDto itemControlValueDto : itemControlValueLang
													.getListControlValue()) {
												String fieldCode = iSettingCharDto.getFieldCode();
												String mLangCode = itemControlValueLang.getmLanguageCode();
												String idField = itemControlValueDto.getIdField();
												String valueField = itemControlValueDto.getValueField();
												String idTextField = itemControlValueDto.getTextField();
												roomClientRespository.insertControlValue(fieldCode, mLangCode, idField,
														valueField, idTextField, username);
											}
										}
										break;
									}
								}

							}
							break;
						}
					}
				} // end
					// update status isvisible
				for (SettingChatDto jsonSettingCharDto : jsonSettingLanguageDto.getListControls()) {
					if (jsonSettingCharDto != null
							&& iSettingCharDto.getFieldCode().equals(jsonSettingCharDto.getFieldCode())) {
						String fieldCode = jsonSettingCharDto.getFieldCode();
						boolean isVisible = jsonSettingCharDto.getIsVisible();
						boolean isDisplay;
						if (isVisible == false) {
							isDisplay = false;
						} else {
							isDisplay = iSettingCharDto.getIsDisplay() == null ? false : iSettingCharDto.getIsDisplay();
						}
						roomClientRespository.updateVisibleByField(fieldCode, isVisible, isDisplay);
						break;
					}
				}

			}
			return 1;
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public List<ChatControlDto> getListControl() {
		return roomClientRespository.getListControl();
	}

	@Override
	public List<ChatControlValueLanguageDto> insertRowControlaValue(List<ChatControlValueLanguageDto> list,
			String field) {
		List<LanguageDto> languageList = languageService.getLanguageDtoList();
		if (list == null) {
			list = new ArrayList<ChatControlValueLanguageDto>();
		}
		for (LanguageDto itemLang : languageList) {
			for (ChatControlValueLanguageDto itemValue : list) {
				if (itemValue.getmLanguageCode().equals("")) {
					itemValue.setmLanguageCode(itemLang.getCode());
				}
				if (itemValue.getmLanguageCode().equals(itemLang.getCode())) {
					ChatControlValueDto itemControlValueAdd = new ChatControlValueDto();
					Date dateCurrent = new Date();
					@SuppressWarnings("deprecation")
					String id = String.valueOf(dateCurrent.getSeconds()) + String.valueOf(dateCurrent.getMinutes())
							+ String.valueOf(dateCurrent.getHours());
					itemControlValueAdd.setId(Integer.parseInt(id));
					itemControlValueAdd.setIdField("");
					itemControlValueAdd.setFieldCode("");
					itemControlValueAdd.setValueField("");
					itemControlValueAdd.setTextField("");
					itemControlValueAdd.setmLanguageCode(itemLang.getCode());
					if (itemValue.getListControlValue() == null) {
						itemValue.setListControlValue(new ArrayList<ChatControlValueDto>());
					}
					itemValue.getListControlValue().add(itemControlValueAdd);
				}
			}
		}
		return list;
	}

	@Override
	public List<ProductNodeDto> getListProduct(String id, String lang) {

		List<ProductNodeDto> listProduct = new ArrayList<ProductNodeDto>();
		listProduct = roomClientRespository.findCustomerType(lang);
		for (ProductNodeDto item : listProduct) {
			item.setId("CUS_" + item.getIdType());
			item.setIconCls(ConstantCore.OPEN);
			List<ProductNodeDto> listProductSub = new ArrayList<ProductNodeDto>();
			listProductSub = roomClientRespository.findProductCategory(lang, item.getIdType());
			for (ProductNodeDto item2 : listProductSub) {
				item2.setId("PDT_" + item2.getIdType());
				if (item2.getId().equals(id)) {
					item2.setChecked(true);
				}
			}
			item.setChildren(listProductSub);
		}
		return listProduct;
	}

	@Override
	public List<AccountAddDto> getListUser() {
		return roomClientRespository.getListUser();
	}

	@Override
	public List<ChatUserProductDto> getListUserByProductId(String id) {
		List<Long> list = new ArrayList<Long>();
		List<ChatUserProductDto> chatUserProductDtoList = new ArrayList<ChatUserProductDto>();
		if (!id.isEmpty()) {
			String[] arr = id.split(";");

			for (String item : arr) {
				list.add(Long.parseLong(item));
			}
			chatUserProductDtoList = roomClientRespository.getListUserByProductId(list);
		}
		return chatUserProductDtoList;
	}

	@Override
	@Transactional
	public int saveUserProduct(String id, String user) {
		int rs = 0;
		try {
			String userLogin = UserProfileUtils.getUserNameLogin();
			// delete all data old
			List<Long> list = new ArrayList<Long>();
			String[] arr = id.split(";");
			for (String item : arr) {
				list.add(Long.parseLong(item));
			}
			for (Long i : list) {
				roomClientRespository.deleteUserByProductId(i, userLogin);
				ObjectMapper mapper = new ObjectMapper();
				List<String> listUser = new ArrayList<String>();
				listUser = mapper.readValue(user, new TypeReference<List<String>>() {
				});
				for (String u : listUser) {
					roomClientRespository.inserUserProduct(i, u, userLogin);
				}
			}

			rs = 1;
		} catch (Exception ex) {
			rs = 0;
		}
		return rs;
	}

	private void setSearchParamForUserProduct(ChatUserProductSearchDto userProductdto) {
		if (null == userProductdto.getFieldValues()) {
			userProductdto.setFieldValues(new ArrayList<String>());
		}

		if (userProductdto.getFieldValues().isEmpty()) {
			userProductdto
					.setUser(userProductdto.getFieldSearch() == null ? null : userProductdto.getFieldSearch().trim());
			userProductdto.setProduct(
					userProductdto.getFieldSearch() == null ? null : userProductdto.getFieldSearch().trim());
		} else {
			for (String item : userProductdto.getFieldValues()) {
				if (item.equals(ChatUserProductSearchEnum.USER.name())) {
					userProductdto.setUser(userProductdto.getFieldSearch().trim());
				} else if (item.equals(ChatUserProductSearchEnum.ROLE.name())) {
					userProductdto.setProduct(userProductdto.getFieldSearch().trim());
				}
			}
		}
	}

	@Override
	public PageWrapper<ChatUserProductDto> getListUserProduct(int page, ChatUserProductSearchDto userProductDto,
			Locale locale) {
		int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<ChatUserProductDto> pageWrapper = new PageWrapper<ChatUserProductDto>(page, sizeOfPage);
		setSearchParamForUserProduct(userProductDto);

		int count = roomClientRespository.countUserProductAllActive(userProductDto, locale.toString());
		List<ChatUserProductDto> list = new ArrayList<ChatUserProductDto>();
		if (count > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			list = roomClientRespository.findAllUserProductActive(offsetSQL, sizeOfPage, userProductDto,
					locale.toString());
			for (ChatUserProductDto item : list) {
				List<AccountAddDto> listUser = roomClientRespository.getFullNameByProductId(item.getmProductTypeId());
				String fullname = "";
				if (listUser != null && listUser.size() > 0) {
					for (AccountAddDto acc : listUser) {
						fullname += acc.getFullname() + "; ";
					}
					fullname = fullname.substring(0, fullname.length() - 2);
				}
				item.setFullname(fullname);
			}
		}
		pageWrapper.setDataAndCount(list, count);
		return pageWrapper;
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
		String userLogin = UserProfileUtils.getUserNameLogin();
		// delete all data old
		roomClientRespository.deleteUserByProductId(id, userLogin);
	}

	private void setSearchParamHistory(RoomClientSearchDto roomDto) {
		if (roomDto.getFieldValues() != null && roomDto.getFieldValues().size() > 0) {
			for (String item : roomDto.getFieldValues()) {
				if (item.equals(RoomClientSearchEnum.AGENT.name())) {
					roomDto.setAgent(roomDto.getFieldSearch().trim());
				}

				if (item.equals(RoomClientSearchEnum.FULLNAME.name())) {
					roomDto.setFullname(roomDto.getFieldSearch().trim());
				}

				if (item.equals(RoomClientSearchEnum.SERVICE.name())) {
					roomDto.setService(roomDto.getFieldSearch().trim());
				}

				if (item.equals(RoomClientSearchEnum.PHONE.name())) {
					roomDto.setPhone(roomDto.getFieldSearch().trim());
				}
			}
		} else if (roomDto.getFieldSearch() != null) {
			roomDto.setAgent(roomDto.getFieldSearch().trim());
			roomDto.setFullname(roomDto.getFieldSearch().trim());
			roomDto.setService(roomDto.getFieldSearch().trim());
			roomDto.setPhone(roomDto.getFieldSearch().trim());
		}

		if (roomDto.getFromDate() == null) {
//	        	roomDto.setFromDate(new Date());
		}

		if (roomDto.getToDate() == null) {
			// roomDto.setToDate(new Date("01/01/2999"));
		}
	}

	@Override
	public PageWrapper<RoomClientDto> findHistoryAllActive(int page, RoomClientSearchDto recDto, Locale locale) {
		//
		int isRoleAdmin = 0;

		String username = UserProfileUtils.getUserNameLogin();
		if (roleForChatService.isRoleAdminChat(username)) {
			isRoleAdmin = 1;
		}

		recDto.setLang(locale.toString());

		String userName = UserProfileUtils.getUserNameLogin();
		//
		int sizeOfPage = recDto.getPageSize() != null ? recDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

		PageWrapper<RoomClientDto> pageWrapper = new PageWrapper<RoomClientDto>(page, sizeOfPage);
		setSearchParamHistory(recDto);

		int countRoomClient = roomClientRespository.countHistoryAllActive(recDto, isRoleAdmin, userName);

		List<RoomClientDto> list = new ArrayList<RoomClientDto>();

		if (countRoomClient > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			list = roomClientRespository.findHistoryAllActive(offsetSQL, sizeOfPage, recDto, isRoleAdmin, userName);

			for (RoomClientDto item : list) {
				String statusName = "";
				if (item.getStatus() == 0) { // finish
					statusName = msg.getMessage("chat.list.status.close", null, locale);
				} else if (item.getStatus() == 1) { // chating
					statusName = msg.getMessage("chat.list.status.chating", null, locale);
				} else if (item.getStatus() == 2) { // waiting
					statusName = msg.getMessage("chat.list.status.waiting", null, locale);
				}

				String typeDisconenctName = "";
				if (item.getTypeDisconnected() == 1) {
					typeDisconenctName = msg.getMessage("chat.list.type.disconnect.client", null, locale);
				} else if (item.getTypeDisconnected() == 2) {
					typeDisconenctName = msg.getMessage("chat.list.type.disconnect.user", null, locale);
				}

				item.setStatusName(statusName);
				item.setTypeDisconnectedName(typeDisconenctName);

				if (item.getDisconnectedDate() != null) {
					item.setTotalSupport(getTotalSupportTime(item.getCreatedDate(), item.getDisconnectedDate()));
				}

				List<MessageDto> lstMessage = roomClientRespository.getListMessage(item.getClientid());
				if (null == lstMessage) {
					item.setCountMessage(0);
				} else {
					item.setCountMessage(lstMessage.size());
				}
			}
		}
		pageWrapper.setDataAndCount(list, countRoomClient);
		return pageWrapper;
	}

	@Override
	public void exportHistoryToExcel(RoomClientSearchDto recDto, HttpServletResponse response, Locale locale)
			throws Exception {
		//
		int isRoleAdmin = 0;

		String username = UserProfileUtils.getUserNameLogin();
		if (roleForChatService.isRoleAdminChat(username)) {
			isRoleAdmin = 1;
		}

		recDto.setLang(locale.toString());

		String userName = UserProfileUtils.getUserNameLogin();
		// prepare data
		String templateName = CmsCommonConstant.TEMPLATE_CHAT_HISTORY;
		String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName;
		String datePattent = "dd/MM/yyyy  HH:mm:ss";
		// get data
		setSearchParamHistory(recDto);
		List<RoomClientDto> result = new ArrayList<RoomClientDto>();
		result = roomClientRespository.findHistoryAllActiveForExport(recDto, isRoleAdmin, userName);
		for (RoomClientDto item : result) {
			String statusName = "";
			if (item.getStatus() == 0) { // finish
				statusName = msg.getMessage("chat.list.status.close", null, locale);
			} else if (item.getStatus() == 1) { // chating
				statusName = msg.getMessage("chat.list.status.chating", null, locale);
			} else if (item.getStatus() == 2) { // waiting
				statusName = msg.getMessage("chat.list.status.waiting", null, locale);
			}

			String typeDisconenctName = "";
			if (item.getTypeDisconnected() == 1) {
				typeDisconenctName = msg.getMessage("chat.list.type.disconnect.client", null, locale);
			} else if (item.getTypeDisconnected() == 2) {
				typeDisconenctName = msg.getMessage("chat.list.type.disconnect.user", null, locale);
			}

			item.setStatusName(statusName);
			item.setTypeDisconnectedName(typeDisconenctName);

			if (item.getDisconnectedDate() != null) {
				item.setTotalSupport(getTotalSupportTime(item.getCreatedDate(), item.getDisconnectedDate()));
			}

			List<MessageDto> lstMessage = roomClientRespository.getListMessage(item.getClientid());
			if (null == lstMessage) {
				item.setCountMessage(0);
			} else {
				item.setCountMessage(lstMessage.size());
			}
		}
		//
		List<ItemColsExcelDto> cols = new ArrayList<ItemColsExcelDto>();
		ConstantImport.setListColumnExcel(RoomClientEnum.class, cols);
		ExportExcelUtil<RoomClientDto> exportExcel = new ExportExcelUtil<RoomClientDto>();
		try {
//            exportExcel.exportExcel(template, locale, result, RoomClientDto.class, cols, datePattent);
			exportExcel.exportExcelWithXSSFNonPass(template, locale, result, RoomClientDto.class, cols, datePattent,
					response, templateName);
		} catch (Exception e1) {
			logger.error("__exportHistoryToExcel__", e1);
		}
		SXSSFWorkbook workbook = exportExcel.getWorkbook();
		// end fill data to workbook
		try (ServletOutputStream outputStream = response.getOutputStream()) {
			response.setContentType(CmsCommonConstant.CONTENT_TYPE_EXCEL);
			response.addHeader(CmsCommonConstant.CONTENT_DISPOSITION,
					CmsCommonConstant.ATTCHMENT_FILENAME + templateName + "\"");
			workbook.write(outputStream);
			outputStream.flush();
			workbook.close();
		} catch (Exception e) {

		}
	}

	@Override
	public String getMessageDefault(String lang) {
		return roomClientRespository.getMessageDefault(lang);
	}

	@Override
	public int getSessionTimeout() {
		return systemConfig.getIntConfig(SystemConfig.SESSION_TIMEOUT_CHAT_SERVER);
	}

	@Override
	@Transactional
	public void updateInfoCustomer(RoomClientDto roomDto) {
		roomClientRespository.updateInfoCustomer(roomDto);
	}

	@Override
	public int countClientWaiting() {
		int isRoleAdmin = 0;

		if (UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN)) {
			isRoleAdmin = 1;
		}
		String userName = UserProfileUtils.getUserNameLogin();

		return roomClientRespository.countClientWaiting(isRoleAdmin, userName);
	}

	@Override
	public int getOnOffChat() {
		int onChat = systemConfig.getIntConfig(SystemConfig.ON_OFF_CHAT);
		return onChat;
	}

	@Override
	public List<RoomClientDto> getAllRoomClientByAgent(String agent, Date date) {
		return roomClientRespository.findAllRoomClientByAgent(agent, date);
	}

	@Override
	public List<ChatControlValueDto> getListValueControls(String field, String lang) {
		return roomClientRespository.findChatListValue(field, lang);
	}

	private String getTotalSupportTime(Date createDate, Date disconnectedDate) {
//			long hour = ChronoUnit.HOURS.between(createDate.toInstant(), disconnectedDate.toInstant()) % 60;
		long min = ChronoUnit.MINUTES.between(createDate.toInstant(), disconnectedDate.toInstant());
//			long sec = ChronoUnit.SECONDS.between(createDate.toInstant(), disconnectedDate.toInstant()) % 60;

		String totalSupport = min + "";
		return totalSupport;
	}

	@Override
	public void saveUnreadMessage(String clientid, Integer number) {
		roomClientRespository.saveUnreadMessage(clientid, number);
	}

	@Override
	public Integer getUnreadMessage(String clientid) {
		return roomClientRespository.findNumberUnreadMessage(clientid);
	}

	@Override
	public MessageDto getLastMessage(String clientid, Integer type) {
		return roomClientRespository.findLastMessage(clientid, type);
	}

	@Override
	public UserInfoSupportDto getUserInfoSupportDto(String clientid) {
		return roomClientRespository.findUserInfoSupportDto(clientid);
	}

	@Override
	public void exportListOfflineToExcel(RoomClientOfflineSearchDto typeSearch, HttpServletResponse response,
			Locale locale) {

		setSearchParamRoomClientOffline(typeSearch);
		typeSearch.setLanguage(locale.toString());
		List<RoomClientOfflineDto> result = roomClientRespository.getListClientOfflineExcel(typeSearch);

		String templateName = CmsCommonConstant.TEMPLATE_CHAT_LIST_OFFLINE;
		String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName;
		String datePattent = "dd/MM/yyyy  HH:mm:ss";

		List<ItemColsExcelDto> cols = new ArrayList<ItemColsExcelDto>();
		ConstantImport.setListColumnExcel(RoomClientOfflineEnum.class, cols);
		ExportExcelUtil<RoomClientOfflineDto> exportExcel = new ExportExcelUtil<RoomClientOfflineDto>();
		try {
			exportExcel.exportExcelWithXSSFNonPass(template, locale, result, RoomClientOfflineDto.class, cols,
					datePattent, response, templateName);
		} catch (Exception e1) {
			logger.error("__exportListOfflineToExcel__", e1);
		}
		SXSSFWorkbook workbook = exportExcel.getWorkbook();
		// end fill data to workbook
		try (ServletOutputStream outputStream = response.getOutputStream()) {
			response.setContentType(CmsCommonConstant.CONTENT_TYPE_EXCEL);
			response.addHeader(CmsCommonConstant.CONTENT_DISPOSITION,
					CmsCommonConstant.ATTCHMENT_FILENAME + templateName + "\"");
			workbook.write(outputStream);
			outputStream.flush();
			workbook.close();
		} catch (Exception e) {

		}
	}
}
