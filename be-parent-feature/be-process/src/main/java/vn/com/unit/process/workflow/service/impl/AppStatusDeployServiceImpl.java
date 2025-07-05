/*******************************************************************************
 * Class        AppStatusDeployServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       KhuongTH
 * Change log   2016/06/21 01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.process.workflow.dto.AppStatusDeployDto;
import vn.com.unit.process.workflow.repository.AppStatusDeployRepository;
import vn.com.unit.process.workflow.service.AppStatusDeployService;
//import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
//import vn.com.unit.ep2p.workflow.dto.AppStatusDeployDto;
//import vn.com.unit.ep2p.workflow.repository.AppStatusDeployRepository;
//import vn.com.unit.ep2p.workflow.service.AppStatusDeployService;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.entity.JpmStatusDeploy;
import vn.com.unit.workflow.service.JpmStatusDeployService;

/**
 * AppStatusDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppStatusDeployServiceImpl implements AppStatusDeployService {

    @Autowired
    AppStatusDeployRepository appStatusDeployRepository;

    @Autowired
    private ConstantDisplayService constantDisplayService;

    @Autowired
    private JpmStatusDeployService jpmStatusDeployService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
	public AppStatusDeployDto[] getListStatusDeployDtoByProcessId(Long processId){
        List<JpmStatusDeployDto> statusDeployDtos = jpmStatusDeployService.getStatusDeployDtosByProcessDeployId(processId);
        if (CommonCollectionUtil.isEmpty(statusDeployDtos))
            return null;
        return statusDeployDtos.stream().map(this::convertJpmStatusDtoToAppStatusDto).collect(Collectors.toList())
                .toArray(new AppStatusDeployDto[0]);
    }

	@Override
	public AppStatusDeployDto getById(Long id){
	    JpmStatusDeployDto statusDeployDto = jpmStatusDeployService.getJpmStatusDeployDtoById(id);
		return this.convertJpmStatusDtoToAppStatusDto(statusDeployDto);
	}
	
	@Override
	public JpmStatusDeploy getByBusinessCodeAndStatusCode(Long processId) {
		
		return appStatusDeployRepository.getStatusByProcessId(processId);
	}
	
	@Override
	public List<Select2Dto> getStatusListCommon(Locale locale) {
		return this.getStatusListCommon(locale, ConstantDisplayType.J_PRP_STATUS_001.toString());
	}
	
	@Override
	public List<Select2Dto> getStatusListCommon(Locale locale, String constantDisplayType) {
		List<Select2Dto> lst = new ArrayList<>();
		
		List<JcaConstantDto> statusList = constantDisplayService.getListJcaConstantDtoByKind(constantDisplayType, locale.getLanguage());
		
		if( statusList != null && !statusList.isEmpty() ) {
			for (JcaConstantDto constantDisplay : statusList) {
				String id = constantDisplay.getCode();
				String name = constantDisplay.getName();
				String text = constantDisplay.getName();
				Select2Dto item = new Select2Dto(id, name, text);
				lst.add(item);
			}
		}
		
		return lst;
	}

	@Override
	public List<Select2Dto> getStatusDeployListByProcessDeployId(Long processDeployId, String lang) {
		List<Select2Dto> lst = new ArrayList<>();
		
		List<JpmStatusDeploy> jpmStatusDeployList = appStatusDeployRepository.findJpmStatusDeployListByProcessDeployId(processDeployId, lang);
		
		if( jpmStatusDeployList != null && !jpmStatusDeployList.isEmpty() ) {
			for (JpmStatusDeploy jpmStatusDeploy : jpmStatusDeployList) {
				String id = jpmStatusDeploy.getStatusCode();
				String name = jpmStatusDeploy.getStatusName();
				String text = jpmStatusDeploy.getStatusName();
				Select2Dto item = new Select2Dto(id, name, text);
				lst.add(item);
			}
		}
		
		return lst;
	}
	
    private AppStatusDeployDto convertJpmStatusDtoToAppStatusDto(JpmStatusDeployDto statusDto) {
        if (null == statusDto)
            return null;
        AppStatusDeployDto appStatusDto = objectMapper.convertValue(statusDto, AppStatusDeployDto.class);
        appStatusDto.setId(statusDto.getStatusDeployId());
        appStatusDto.setListJpmStatusLang(statusDto.getStatusLangs());
        return appStatusDto;
    }
}
