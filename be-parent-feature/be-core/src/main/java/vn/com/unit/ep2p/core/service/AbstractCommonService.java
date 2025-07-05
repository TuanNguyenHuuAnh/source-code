/*******************************************************************************
 * Class        ：AbstractCommonService
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：taitt
 * Change log   ：2020/12/09：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service;

import org.modelmapper.ModelMapper;
//import org.modelmapper.config.Configuration.AccessLevel;
//import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.ResObjectService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.exception.HandlerCastException;

/**
 * AbstractCommonService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public abstract class AbstractCommonService {

	@Autowired
	protected HandlerCastException handlerCastException;

	@Autowired
	protected ResObjectService resObjectService;

	@Autowired
	protected JCommonService commonService;

	@Autowired
	protected ObjectMapper objectMapper;

	public <T extends Object, C extends Object> C mapper(T t, Class<C> c) {
		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//	    modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE);
//	    modelMapper.getConfiguration().setFieldMatchingEnabled(true);

//		modelMapper.map(t, c);
//		modelMapper.validate();

		return (C) modelMapper.map(t, c);
	}

	public Integer getOffset(Integer page, Integer size) {
		return null != page && null != size ? (page - 1) * size : null;
	}

	// temp for mockup no authentication
	public String getUsernameAction() {
		String username = UserProfileUtils.getUserNameLogin();
		if (null == username) {
			throw new BusinessException("Wrong authentication");
		}
		return username != null ? username : "";
	}
	
	public String getUserActionId() {
		String userActionId = UserProfileUtils.getActUserId();
		if (CommonStringUtil.isBlank(userActionId)) {
			throw new BusinessException("Wrong authentication");
		}
		return userActionId != null ? userActionId : "";
	}

	public Pageable buildPageable(Pageable pageable, Class<?> clazz, String alias) throws DetailException {
		Sort sort = commonService.buildSortAlias(pageable.getSort(), clazz, alias);
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	}

	public <T extends Enum<T>> Pageable buildPageableByEnums(Pageable pageable, Class<?> clazz, String alias,
			T[] enumsDatas) throws DetailException {
//        Sort sortForClazz = commonService.buildSortAliasNotUseDefault(pageable.getSort(),clazz, alias);
		Sort sortForEumns = commonService.buildSortEnums(pageable.getSort(), enumsDatas, clazz);

		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortForEumns);
	}
}
