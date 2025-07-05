/*******************************************************************************
 * Class        ：MenuServiceImpl
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.tree.TreeObject;
import vn.com.unit.core.dto.JcaMenuDto;
import vn.com.unit.core.dto.JcaMenuLangDto;
import vn.com.unit.core.entity.JcaMenu;
//import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaMenuService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.enumdef.MenuStatusEnum;
import vn.com.unit.ep2p.core.req.dto.MenuLangInfoReq;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.MenuAddReq;
import vn.com.unit.ep2p.dto.req.MenuUpdateReq;
import vn.com.unit.ep2p.dto.res.ItemInfoRes;
import vn.com.unit.ep2p.dto.res.MenuInfoRes;
import vn.com.unit.ep2p.service.ItemService;
import vn.com.unit.ep2p.service.MenuPathService;
import vn.com.unit.ep2p.service.MenuService;

/**
 * MenuServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiMenuServiceImpl extends AbstractCommonService implements MenuService {

	@Autowired
	@Qualifier("jcaMenuServiceImpl")
	private JcaMenuService jcaMenuService;
	
	@Autowired
	private MenuPathService menuPathService;
	
	@Autowired
	private ItemService itemService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<TreeObject<JcaMenuDto>> getListMenu(Long companyId) throws DetailException {
		List<JcaMenuDto> datas = new ArrayList<>();
		List<TreeObject<JcaMenuDto>> listTreeObject = new ArrayList<>();
		try {
			datas = jcaMenuService.getListJcaMenuDto(companyId);
			TreeBuilder<JcaMenuDto> builder = new TreeBuilder(datas);
			builder.sortWithTree();
			listTreeObject = builder.buildTree();
		} catch (Exception e) {
			handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021604_APPAPI_MENU_LIST_ERROR);
		}
		return listTreeObject;
	}
	
	private void setItemId(Long itemId, JcaMenuDto jcaMenuDto) throws DetailException {
	    ItemInfoRes existed = itemService.getItemInfoById(itemId);
	    if(null != existed ) {
	        jcaMenuDto.setItemId(itemId);
	    }
	}
	
	private void setParentId(Long parentId, JcaMenuDto jcaMenuDto) throws DetailException {
	    JcaMenuDto existed =  this.getDetailDto(parentId);
	    if(null != existed) {
	        jcaMenuDto.setParentId(parentId);
	    }
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MenuInfoRes create(MenuAddReq menuAddReq) throws DetailException {
		JcaMenuDto jcaMenuDto = new JcaMenuDto();
		try {
			jcaMenuDto.setCode(menuAddReq.getCode());
			jcaMenuDto.setUrl(menuAddReq.getUrl());
			jcaMenuDto.setClassName(menuAddReq.getIcon());
			jcaMenuDto.setDisplayOrder(menuAddReq.getMenuOrder());
			jcaMenuDto.setStatus(MenuStatusEnum.NEW.getValue());
			jcaMenuDto.setActived(menuAddReq.isActived());
			if(null != menuAddReq.getItemId()) {
			    setItemId(menuAddReq.getItemId(), jcaMenuDto);
			}
			jcaMenuDto.setItemId(menuAddReq.getItemId());
			jcaMenuDto.setCompanyId(menuAddReq.getCompanyId());
			jcaMenuDto.setGroupFlag(menuAddReq.isHeaderFlag());
			if (null != menuAddReq.getParentId() && menuAddReq.getParentId() != CommonConstant.NUMBER_ZERO_L) {
			    setParentId(menuAddReq.getParentId(), jcaMenuDto);
	        }
	        if(menuAddReq.getParentId() == CommonConstant.NUMBER_ZERO_L) {
	            jcaMenuDto.setParentId(menuAddReq.getParentId());
	        }
	        
	        List<MenuLangInfoReq> menuInfo = menuAddReq.getMenuInfoLang();
	        jcaMenuDto.setLanguages(this.setMapLanguageMenu(menuInfo));
	        	        
			this.save(jcaMenuDto);
			
		} catch (Exception e) {
			handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021605_APPAPI_MENU_ADD_ERROR);
		}
		return this.getMenuInfoResById(jcaMenuDto.getId());
	}
	
	

	@Override
	public JcaMenuDto save(JcaMenuDto jcaMenuDto) {
		 JcaMenu jcaMenu = jcaMenuService.saveJcaMenuDto(jcaMenuDto);
		 jcaMenuDto.setId(jcaMenu.getId());
		 return jcaMenuDto;
	}


	@Override
	public MenuInfoRes getMenuInfoResById(Long menuId) throws DetailException {
		JcaMenuDto jcaMenuDto = this.getDetailDto(menuId);
		return objectMapper.convertValue(jcaMenuDto, MenuInfoRes.class);
	}

	@Override
	public JcaMenuDto getDetailDto(Long menuId) throws DetailException {
		JcaMenuDto jcaMenuDto = jcaMenuService.getJcaMenuDtoById(menuId);
		if (null != jcaMenuDto) {
			return jcaMenuDto;
		}else {
			throw new DetailException(AppApiExceptionCodeConstant.E4021602_APPAPI_MENU_NOT_FOUND,true);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(MenuUpdateReq menuUpdateReq) throws DetailException {
		Long id = menuUpdateReq.getMenuId();
		JcaMenuDto jcaMenuDto  = jcaMenuService.getJcaMenuDtoById(id);
		if (null != jcaMenuDto) {
			try {
				// save menu
				jcaMenuDto.setUrl(menuUpdateReq.getUrl());
				jcaMenuDto.setClassName(menuUpdateReq.getIcon());
				jcaMenuDto.setDisplayOrder(menuUpdateReq.getMenuOrder());
				jcaMenuDto.setActived(menuUpdateReq.isActived());
				if(null != menuUpdateReq.getItemId()) {
	                setItemId(menuUpdateReq.getItemId(), jcaMenuDto);
	            }
				jcaMenuDto.setCompanyId(menuUpdateReq.getCompanyId());
				jcaMenuDto.setClassName(menuUpdateReq.getIcon());
				jcaMenuDto.setGroupFlag(menuUpdateReq.isHeaderFlag());
				if (null != menuUpdateReq.getParentId() && menuUpdateReq.getParentId() != CommonConstant.NUMBER_ZERO_L) {
	                setParentId(menuUpdateReq.getParentId(), jcaMenuDto);
	            }
				if(menuUpdateReq.getParentId() == CommonConstant.NUMBER_ZERO_L) {
		            jcaMenuDto.setParentId(menuUpdateReq.getParentId());
		        }
				
	            List<MenuLangInfoReq> menuInfo = menuUpdateReq.getMenuInfoLang();
	            jcaMenuDto.setLanguages(this.setMapLanguageMenu(menuInfo));
				
				this.save(jcaMenuDto);
				
			}catch (Exception e) {
				handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021606_APPAPI_MENU_UPDATE);
			}
		}else {
			throw new DetailException(AppApiExceptionCodeConstant.E4021602_APPAPI_MENU_NOT_FOUND,true);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long menuId) throws DetailException {
		JcaMenuDto jcaMenuDto  = jcaMenuService.getJcaMenuDtoById(menuId);
	        if (null != jcaMenuDto) {
	            try {
	                jcaMenuService.deleteJcaMenuById(menuId);
	            	// save menu path
					Long descendantId = menuId;
					menuPathService.deleteMenuPathByDescendantId(descendantId);
	            } catch (Exception e) {
	                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021608_APPAPI_MENU_DELETE_ERROR);
	            }
	        } else {
	            throw new DetailException(AppApiExceptionCodeConstant.E4021602_APPAPI_MENU_NOT_FOUND);
	        }
	}
	
	private List<JcaMenuLangDto>  setMapLanguageMenu(List<MenuLangInfoReq> menuInfo) {
        List<JcaMenuLangDto> languages = new ArrayList<>();
        languages = menuInfo.stream().map(p -> {
            JcaMenuLangDto language = new JcaMenuLangDto();
            language.setNameAbv(p.getAlias());
            language.setLangCode(p.getLanguageCode());
            language.setName(p.getName());
            return language;
        }).collect(Collectors.toList());
        
        return languages;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<TreeObject<JcaMenuDto>> getTreeMenuByUser(Long companyId, String langCode, Long userId) throws DetailException {
		List<JcaMenuDto> datas = new ArrayList<>();
		List<TreeObject<JcaMenuDto>> listTreeObject = new ArrayList<>();
		try {
			datas = jcaMenuService.getTreeMenuByUser(companyId, langCode, userId);
			TreeBuilder<JcaMenuDto> builder = new TreeBuilder(datas);
			builder.sortWithTree();
			listTreeObject = builder.buildTree();
		} catch (Exception e) {
			handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021604_APPAPI_MENU_LIST_ERROR);
		}
		return listTreeObject;
	}

	@Override
	public List<JcaMenuDto> getListMenuByUser(Long companyId, String langCode, Long userId) {
		return jcaMenuService.getListMenuByUser(companyId, langCode, userId);
	}
}
