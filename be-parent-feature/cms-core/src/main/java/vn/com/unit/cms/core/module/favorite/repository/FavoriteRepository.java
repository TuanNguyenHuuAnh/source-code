package vn.com.unit.cms.core.module.favorite.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteSearchDto;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteSearchResultDto;
import vn.com.unit.cms.core.module.favorite.entity.Favorite;
import vn.com.unit.db.repository.DbRepository;

public interface FavoriteRepository extends DbRepository<Favorite, Long> {
	public List<FavoriteSearchResultDto> getListByAgentCode(@Param("agentCode")String agentCode);

	public Favorite detailFavoriteByLink(@Param("link")String link, @Param("agentCode")String agentCode);

	public int getFavoriteByLinkAndAgentCode(@Param("favorite") FavoriteSearchDto favorite, @Param("agentCode")String agentCode);
	@Modifying
	public void deleteFavoriteByAgentCode(@Param("agentCode")String agentCode);
	
	@Modifying
	public void deleteFavoriteByIds(@Param("agentCode")String agentCode, List<Long> ids);
	
	public List<FavoriteSearchResultDto> getListSubItemByAgentCode(@Param("accountId")Long accountId, @Param("menuId")Long menuId);
	
	public Favorite detailFavorite(@Param("itemId")String itemId, @Param("agentCode")String agentCode);
	
	public List<FavoriteSearchResultDto> getListFavoriteByAgentCode(@Param("agentCode")String agentCode, @Param("channel")String channel);
}
