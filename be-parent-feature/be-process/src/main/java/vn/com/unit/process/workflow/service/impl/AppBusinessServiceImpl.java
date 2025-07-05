/*******************************************************************************
 * Class        JpmBusinessServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       KhoaNA
 * Change log   2016/06/21 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.enumdef.FormType;
import vn.com.unit.core.workflow.enumdef.ProcessType;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.Utils;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.process.workflow.dto.AppBusinessDto;
import vn.com.unit.process.workflow.dto.AppBusinessSearchDto;
import vn.com.unit.process.workflow.enumdef.JpmBusinessSearchEnum;
import vn.com.unit.process.workflow.repository.AppBusinessRepository;
import vn.com.unit.process.workflow.service.AppBusinessService;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmBusinessSearchDto;
import vn.com.unit.workflow.entity.JpmBusiness;
import vn.com.unit.workflow.service.JpmBusinessService;

/**
 * JpmBusinessServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppBusinessServiceImpl implements AppBusinessService, AbstractCommonService {

    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    private JCommonService commonService;

    @Autowired
    private AppBusinessRepository appBusinessRepository;
    
    @Autowired
    private JpmBusinessService jpmBusinessService; 
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PageWrapper<AppBusinessDto> search(int page, int pageSize, AppBusinessSearchDto searchDto) throws DetailException {
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<AppBusinessDto> pageWrapper = new PageWrapper<>(page, sizeOfPage, listPageSize);
        if (null == searchDto) {
            searchDto = new AppBusinessSearchDto();
        }
        /** init pageable */
        Pageable pageable = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JpmBusiness.class, JpmBusinessService.TABLE_ALIAS_JPM_BUSINESS);

        /** init param search repository */
        JpmBusinessSearchDto reqSearch = this.buildJpmBusinessSearchDto(searchDto);
        
        //searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
        //searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);
        int count = jpmBusinessService.countBySearchCondition(reqSearch);
        List<AppBusinessDto> result = new ArrayList<>();
        if (count > 0) {
            List<JpmBusinessDto> businessDtos = jpmBusinessService.getBusinessDtosByCondition(reqSearch, pageable);
            result = businessDtos.stream().map(this::convertJpmBusinessDtoToAppBusinessDto).collect(Collectors.toList());
        }

        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    private JpmBusinessSearchDto buildJpmBusinessSearchDto(AppBusinessSearchDto searchDto) {
        JpmBusinessSearchDto businessSearchDto = new JpmBusinessSearchDto();

        String keySearch = searchDto.getFieldSearch();
        Long companyId = Long.valueOf(0).equals(searchDto.getCompanyId()) ? null : searchDto.getCompanyId();
        String processType = null;
        List<String> fieldValues = searchDto.getFieldValues();
        
        businessSearchDto.setCompanyId(companyId);
        businessSearchDto.setKeySearch(keySearch);
        businessSearchDto.setProcessType(processType);
        if(CommonCollectionUtil.isNotEmpty(fieldValues)) {
            for(String fieldValue : fieldValues) {
                switch (JpmBusinessSearchEnum.valueOf(fieldValue)) {
                case CODE:
                    businessSearchDto.setBusinessCode(CommonStringUtil.trimToNull(keySearch));
                    break;
                case NAME:
                    businessSearchDto.setBusinessName(CommonStringUtil.trimToNull(keySearch));
                    break;
                default:
                    businessSearchDto.setBusinessCode(CommonStringUtil.trimToNull(keySearch));
                    businessSearchDto.setBusinessName(CommonStringUtil.trimToNull(keySearch));
                    break;
                }
            }
        } else {
            businessSearchDto.setBusinessCode(CommonStringUtil.trimToNull(keySearch));
            businessSearchDto.setBusinessName(CommonStringUtil.trimToNull(keySearch));
        }

        return businessSearchDto;
    }

    @Override
    public AppBusinessDto getAppBusinessDtoById(Long id) {
//    	return appBusinessRepository.findOneJpmBusinessById(id);
        JpmBusinessDto businessDto = jpmBusinessService.getJpmBusinessDtoById(id);
        return this.convertJpmBusinessDtoToAppBusinessDto(businessDto);
    }
    
    @Transactional
	@Override
	public JpmBusiness saveJpmBusinessDto(AppBusinessDto objectDto) {
		JpmBusinessDto businessDto = this.convertAppBusinessDtoToJpmBusinessDto(objectDto);
		JpmBusiness jpmBusiness = jpmBusinessService.saveJpmBusinessDto(businessDto);
		objectDto.setId(jpmBusiness.getId());
        return jpmBusiness;
	}

    @Override
    public AppBusinessDto getJpmBusinessByCodeAndCompanyId(String code, Long companyId) {
        JpmBusinessDto businessDto = jpmBusinessService.getBusinessDtoByCodeAndCompanyId(code, companyId);
        return this.convertJpmBusinessDtoToAppBusinessDto(businessDto);
    }

    @Override
    public boolean deletedById(Long id) {
        return jpmBusinessService.deleteById(id);
    }

    @Override
	public List<Select2Dto> getSelect2DtoListByTermAndCompanyId(String term, Long companyId, 
			boolean companyAdmin, boolean isPaging) {
		List<AppBusinessDto> jpmBusinessDtoList = appBusinessRepository.findJpmBusinessByKeySearchAndCompanyId(term, companyId, companyAdmin, isPaging);
		
		List<Select2Dto> result = new ArrayList<>();
		if( jpmBusinessDtoList != null && !jpmBusinessDtoList.isEmpty() ) {
			for (AppBusinessDto jpmBusinessDto : jpmBusinessDtoList) {
				Long id = jpmBusinessDto.getId();
				String name = jpmBusinessDto.getName();
				String code = jpmBusinessDto.getCode();
				
				Select2Dto item = Utils.createSelect2Dto(id.toString(), name, new String[] {code, name});
				result.add(item);
			}
		}
		return result;
	}

	@Override
	public List<AppBusinessDto> findJpmBusinessDtoListByCompanyId(Long companyId) {
		return appBusinessRepository.findJpmBusinessDtoListByCompanyId(companyId);
	}

	@Override
	public List<Select2Dto> getSelect2DtoListCompanyId(Long companyId) {
		List<AppBusinessDto> list = appBusinessRepository.findJpmBusinessDtoListByCompanyId(companyId);
		List<Select2Dto> resList = new ArrayList<>();
		if( list != null && !list.isEmpty() ) {
			for (AppBusinessDto jpmBusinessDto : list) {
				Long id = jpmBusinessDto.getId();
				String name = jpmBusinessDto.getName();
				String code = jpmBusinessDto.getCode();
				
				Select2Dto item = Utils.createSelect2Dto(id.toString(), name, new String[] {code, name});
				resList.add(item);
			}
		}
		return resList;
	}

    @Override
    public boolean updateHasParamById(Long id, boolean hasParam) {
        boolean res = false;
        
//        JpmBusiness jpmBusiness = appBusinessRepository.findOne(id);
//        if(null != jpmBusiness){
//            jpmBusiness.set(hasParam);
//            appBusinessRepository.save(jpmBusiness);
//            res = true;
//        }
        return res;
    }
    
    @Override
    public List<Select2Dto> findSelect2DtoUnregisteredByCompanyId(String keySearch, Long companyId, int formType, boolean isPaging) {
        List<Integer> processTypeList = new ArrayList<>();
        if (FormType.FREE_FORM_TYPE.toString().equalsIgnoreCase(String.valueOf(formType))) {
            processTypeList.add(ProcessType.FREE.getValue());
            processTypeList.add(ProcessType.INTEGRATE.getValue());
        } else {
            processTypeList.add(ProcessType.BPMN.getValue());
        }
        return appBusinessRepository.findSelect2DtoUnregisteredCompanyId(keySearch, companyId, processTypeList, isPaging);
    }

    /**
     * @author KhuongTH
     */
    @Override
    public List<Select2Dto> getSelect2DtoListHasAuthorityByCompanyId(Long companyId) {
        return appBusinessRepository.findSelect2DtoHasAuthorityByCompanyId(companyId);
    }

    @Override
    public JCommonService getCommonService() {
        return commonService;
    }

    private AppBusinessDto convertJpmBusinessDtoToAppBusinessDto(JpmBusinessDto businessDto) {
        if (null == businessDto)
            return null;
        AppBusinessDto appBusinessDto = objectMapper.convertValue(businessDto, AppBusinessDto.class);
        appBusinessDto.setId(businessDto.getBusinessId());
        appBusinessDto.setName(businessDto.getBusinessName());
        appBusinessDto.setCode(businessDto.getBusinessCode());
        appBusinessDto.setIsActive(businessDto.isActived());
        appBusinessDto.setIsAuthority(businessDto.isAuthority());
        return appBusinessDto;
    }

    private JpmBusinessDto convertAppBusinessDtoToJpmBusinessDto(AppBusinessDto appBusinessDto) {
        if (null == appBusinessDto)
            return null;
        JpmBusinessDto businessDto = objectMapper.convertValue(appBusinessDto, JpmBusinessDto.class);
        businessDto.setBusinessId(appBusinessDto.getId());
        businessDto.setBusinessName(appBusinessDto.getName());
        businessDto.setBusinessCode(appBusinessDto.getCode());
        businessDto.setActived(appBusinessDto.getIsActive());
        businessDto.setAuthority(appBusinessDto.getIsAuthority());
        return businessDto;
    }
}
