package vn.com.unit.cms.admin.all.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.admin.all.service.UserLoginAdminService;
import vn.com.unit.cms.core.module.usersLogin.dto.ChannelCountDto;
import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginDto;
import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginSearchDto;
import vn.com.unit.cms.core.module.usersLogin.repository.UserLoginRepository;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JwtTokenValidate;
import vn.com.unit.core.security.jwt.TokenProvider;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.enumdef.UserLoginSearchEnum;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserLoginAdminServiceImpl implements UserLoginAdminService, AbstractCommonService{

    @Autowired
    private UserLoginRepository userLoginRepository;
    
    @Autowired
    private SystemConfig systemConfig;
       
    @Autowired
    private ObjectMapper objectMapper;
    
	@Autowired
	private TokenProvider tokenProvider;

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(UserLoginAdminServiceImpl.class);
	
    @Override
    public void doSearch(PageWrapper<UserLoginDto> pageWrapper, List<ChannelCountDto> countChannelList, int page, UserLoginSearchDto searchDto, int pageSize, Locale locale) throws DetailException {
        // Get listPageSize, sizeOfPage
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
//        PageWrapper<UserLoginDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setCurrentPage(page);
        pageWrapper.setSizeOfPage(sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        if (null == searchDto)
            searchDto = new UserLoginSearchDto();
        /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        UserLoginSearchDto reqSearch = this.buildUserLoginSearchDto(commonSearch);
        try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date stDate = (searchDto.getStartTime() == null || StringUtils.isBlank(searchDto.getStartTime())) ? 
					null : dateFormat.parse(searchDto.getStartTime());
			Date endDate = (searchDto.getEndTime() == null || StringUtils.isBlank(searchDto.getEndTime())) ? 
					null : dateFormat.parse(searchDto.getEndTime());
	        reqSearch.setStartDate(stDate);
	        reqSearch.setEndDate(endDate);
		} catch(Exception e) {
			logger.error("Convert to Date error: ", e.getMessage());
		}
        int count = userLoginRepository.countUserLoginByConditon(reqSearch);
//        int count = 10;
        List<UserLoginDto> result = new ArrayList<>();
        List<ChannelCountDto> countChannelListTemp = new ArrayList<>();
        int countUserLogin = 0;
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            result = userLoginRepository.findAllUserLoginByCondition(startIndex, sizeOfPage, reqSearch);
            countChannelListTemp = userLoginRepository.countUserLoginByConditionAndChannel(reqSearch);
        }
        pageWrapper.setDataAndCount(result, count);
        countChannelList.clear();
        countChannelList.addAll(countChannelListTemp);
    }

    @Override
    public JCommonService getCommonService() {
        return null;
    }
    
	private UserLoginSearchDto buildUserLoginSearchDto(MultiValueMap<String, String> commonSearch) {
		UserLoginSearchDto reqSearch = new UserLoginSearchDto();
       
        String keySearch = CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldSearch")) ? commonSearch.getFirst("fieldSearch")
                : DtsConstant.EMPTY;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldValues"))
                ? java.util.Arrays.asList(CommonStringUtil.split(commonSearch.getFirst("fieldValues"), ","))
                : null;
                       
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (UserLoginSearchEnum.valueOf(enumValue)) {
                case USERNAME:
                    reqSearch.setUserName(keySearch);
                    break;
                case TYPE:
                    reqSearch.setLoginType(keySearch);
                    break;
                case STATUS:
                    reqSearch.setStatus(keySearch);
                    break;
                case DEVICE:
                    reqSearch.setDevice(keySearch);
                    break;
                case BROWSER: 
                	reqSearch.setBrowser(keySearch);
                	break;
                case OS: 
                	reqSearch.setOs(keySearch);
                	break;
                default:
                	reqSearch.setUserName(keySearch);
                	reqSearch.setLoginType(keySearch);
                	reqSearch.setStatus(keySearch);
                	reqSearch.setDevice(keySearch);
                	reqSearch.setBrowser(keySearch);
                	reqSearch.setOs(keySearch);
                    break;
                }
            }
        }else {
        	reqSearch.setUserName(keySearch);
        	reqSearch.setLoginType(keySearch);
        	reqSearch.setStatus(keySearch);
        	reqSearch.setDevice(keySearch);
        	reqSearch.setBrowser(keySearch);
        	reqSearch.setOs(keySearch);
        }
        
        return reqSearch;
  }
    @Override
    public int countAllAccessTokenValidate() {
    	List<String> accessTokenList = userLoginRepository.getAllAccessTokenInDay();
    	int count = 0;
    	JwtTokenValidate jwtToken = new JwtTokenValidate();
    	if (accessTokenList != null && !accessTokenList.isEmpty()) {
        	for (String token : accessTokenList) {
        		jwtToken = tokenProvider.validateToken(token);
        		if (jwtToken.isValid()) {
        			count++;
        		}
        	}	
    	}
    	return count;
    }
}
