package vn.com.unit.ep2p.service;

import java.util.List;
import java.util.Locale;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteResult;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteSearchDto;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteSearchResultDto;
import vn.com.unit.cms.core.module.favorite.entity.Favorite;
import vn.com.unit.dts.exception.DetailException;

public interface ApiFavoriteService {
	public List<FavoriteSearchResultDto> getListByAgentCode(String agentCode);
	public FavoriteResult addFavoriteByAgentCode(FavoriteSearchDto dto,String agentCode) throws Exception;
	public void deleteFavoriteById(Long id) throws Exception;
	public Favorite detailFavorite(String itemId, String agentCode);
	public CmsCommonPagination<FavoriteSearchResultDto> doGetListFavoriteByCondition(String stringJsonParam);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportFavorite(Locale locale);
	void deleteFavoriteByIds(List<Long> id) throws DetailException;
	public boolean addFavoritesByAgentCode(List<FavoriteSearchDto> dto, String agentCode) throws DetailException;
	public List<FavoriteSearchResultDto> getListFavoriteByAgentCode(String agentCode);
}
