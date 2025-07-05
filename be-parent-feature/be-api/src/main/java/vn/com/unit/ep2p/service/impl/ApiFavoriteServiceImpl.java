package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.favorite.dto.FavoritePagingParam;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteResult;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteSearchDto;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteSearchResultDto;
import vn.com.unit.cms.core.module.favorite.entity.Favorite;
import vn.com.unit.cms.core.module.favorite.repository.FavoriteRepository;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.enumdef.FavoriteEnum;
import vn.com.unit.ep2p.service.ApiFavoriteService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiFavoriteServiceImpl implements ApiFavoriteService {

	private static final String SP_SEARCH_FAVORITE = "SP_SEARCH_FAVORITE";
	@Autowired
	@Qualifier("sqlManagerServicePr")
	private SqlManagerServiceImpl sqlManagerService;
	
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	@Autowired
	private SystemConfig systemConfig;
	
	@Autowired
	private ServletContext servletContext;

	@Override
	public List<FavoriteSearchResultDto> getListByAgentCode(String agentCode) {
		if(agentCode == null) {
			return new ArrayList<FavoriteSearchResultDto>();
		}
		return favoriteRepository.getListByAgentCode(agentCode);
	}

	@Override
	public FavoriteResult addFavoriteByAgentCode(FavoriteSearchDto dto, String agentCode) throws DetailException {
		int checkLinkExist = favoriteRepository.getFavoriteByLinkAndAgentCode(dto, agentCode);	
		if (checkLinkExist > 0) {
			throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
		}
		int totalByAgentCode = favoriteRepository.getFavoriteByLinkAndAgentCode(null, agentCode); 
		if (totalByAgentCode >= 8) {
			throw new DetailException(AppApiExceptionCodeConstant.E4027102_APPAPI_LESS_THAN_NUMBER_RECORD);
		}
		try {
			Favorite entity = new Favorite();
			entity.setAgentCode(agentCode);
			if (dto.getType() == null) {
				entity.setType("WEB");
			} else {
				entity.setType(dto.getType());
			}
			entity.setLink(dto.getLink());
			entity.setTitle(dto.getTitle());
			entity.setNamed(dto.getNamed());
			entity.setIcon(dto.getIcon());
			entity.setFunctionCode(dto.getItemId());
			favoriteRepository.save(entity);
			Long id=entity.getId();
			FavoriteResult data = new FavoriteResult();
			data.setId(id);
			return data;
		} catch (Exception e) {
			throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
		}
	}
	
	@Override
	public void deleteFavoriteById(Long id) throws DetailException {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		favoriteRepository.deleteFavoriteByIds(UserProfileUtils.getFaceMask(), ids);
	}

	@Override
	public void deleteFavoriteByIds(List<Long> id) throws DetailException {	
		favoriteRepository.deleteFavoriteByIds(UserProfileUtils.getFaceMask(), id);
	}

	@Override
	public Favorite detailFavorite(String itemId, String agentCode) {
		Favorite entity = favoriteRepository.detailFavorite(itemId, agentCode);
		return entity;
	}

	@Override
	public CmsCommonPagination<FavoriteSearchResultDto> doGetListFavoriteByCondition(String stringJsonParam) {
		FavoritePagingParam param = new FavoritePagingParam();
 		CmsCommonPagination<FavoriteSearchResultDto> resultData = new CmsCommonPagination<FavoriteSearchResultDto>();
 		try {
 			param.stringJsonParam = stringJsonParam;
 			sqlManagerService.call(SP_SEARCH_FAVORITE, param);
 			List<FavoriteSearchResultDto> datas = param.data;
 	 		resultData.setData(datas);
 	 		resultData.setTotalData(param.totalData);
 		} catch(Exception e) {
 			System.out.print(e);
 		}
 		return resultData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportFavorite(Locale locale) {
		ResponseEntity res = null;
		try {
			FavoritePagingParam param = new FavoritePagingParam();
			sqlManagerService.call(SP_SEARCH_FAVORITE, param);
			
			List<FavoriteSearchResultDto> lstdata = new ArrayList();
			if(CommonCollectionUtil.isNotEmpty(param.data)) {
				lstdata=param.data;
			}
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = "Favorite.xlsx";
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A3";
			
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(FavoriteEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
						FavoriteSearchResultDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				throw new Exception("Error export farovite: "+e.getMessage());
			}
		} catch (Exception e) {
		}
		return res;
	}
	
	@Override
	public boolean addFavoritesByAgentCode(List<FavoriteSearchDto> dto, String agentCode) throws DetailException {	
		try {
			deleteFavorites(agentCode);
			List<String> keys = new ArrayList<String>();
			for(FavoriteSearchDto ls: dto) {
				Favorite entity = new Favorite();
				entity.setAgentCode(agentCode);
				if (ls.getType() == null) {
					entity.setType("WEB");
				} else {
					entity.setType(ls.getType());
				}
				entity.setTitle(ls.getTitle());
				entity.setNamed(ls.getNamed());
				entity.setFunctionCode(ls.getItemId());
				entity.setLink(ls.getLink());
				if (ls.getItemId() != null) {
					if (keys.contains(ls.getItemId())) {
						throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
					}
				} else if (ls.getLink() != null) {
					if (keys.contains(ls.getLink())) {
						throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
					}
				}

				int totalByAgentCode = favoriteRepository.getFavoriteByLinkAndAgentCode(null, agentCode); 
				if (totalByAgentCode >= 8) {
					throw new DetailException(AppApiExceptionCodeConstant.E4027102_APPAPI_LESS_THAN_NUMBER_RECORD);
				}
				entity.setIcon(ls.getIcon());
				favoriteRepository.save(entity);
				if (ls.getItemId() != null) {
					keys.add(ls.getItemId());
				} else if (ls.getLink() != null) {
					keys.add(ls.getLink());
				}
			}
		} catch(Exception e) {
			throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);  
		}	
		return true;	
	}
	
	public void deleteFavorites(String agentCode) throws Exception {
		favoriteRepository.deleteFavoriteByAgentCode(agentCode);
	}
	
	@Override
	public List<FavoriteSearchResultDto> getListFavoriteByAgentCode(String agentCode) {
		if(agentCode == null) {
			return new ArrayList<FavoriteSearchResultDto>();
		}
		List<FavoriteSearchResultDto> lstFavorite = favoriteRepository.getListFavoriteByAgentCode(agentCode, UserProfileUtils.getChannel());
		for(FavoriteSearchResultDto item : lstFavorite) {
			if ("/".equals(item.getLink())) {
				List<FavoriteSearchResultDto> lstSubItem = favoriteRepository.getListSubItemByAgentCode(UserProfileUtils.getAccountId(), item.getMenuId());
				item.setSubMenuList(lstSubItem);
			}
		}
		return lstFavorite;
	}
}
