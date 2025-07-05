package vn.com.unit.cms.core.module.favorite.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.agent.dto.CmsCommonPagingParamDto;

public class FavoritePagingParam{

	@In
	public String stringJsonParam;
	@ResultSet
	public List<FavoriteSearchResultDto> data;
	@Out
	public Integer totalData;

}
