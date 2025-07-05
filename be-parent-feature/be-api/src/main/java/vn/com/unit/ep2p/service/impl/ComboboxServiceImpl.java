/*******************************************************************************
 * Class        ：ComboboxServiceImpl
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：tantm
 * Change log   ：2021/01/28：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.dto.MultiSelectDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaCompanyService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.exception.HandlerCastException;
import vn.com.unit.ep2p.service.AccountOrgService;
import vn.com.unit.ep2p.service.CategoryService;
import vn.com.unit.ep2p.service.ComboboxService;
import vn.com.unit.ep2p.service.ConstantService;
import vn.com.unit.ep2p.service.FormService;
import vn.com.unit.ep2p.service.ProcessDeployService;
import vn.com.unit.ep2p.service.TeamService;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.service.JpmStatusDeployService;

/**
 * ComboboxServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ComboboxServiceImpl implements ComboboxService {

    @Autowired
    protected HandlerCastException handlerCastException;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private FormService formService;

    @Autowired
    private AccountOrgService accountOrgService;

    @Autowired
    private JpmStatusDeployService jpmStatusDeployService;

    @Autowired
    private ProcessDeployService processDeployService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private JcaConstantService jcaConstantService;

    @Override
    public ObjectDataRes<Select2Dto> getListSelect2DtoOrg(MultiValueMap<String, String> requestParams, Pageable pageable) throws Exception {
        ObjectDataRes<Select2Dto> res = new ObjectDataRes<Select2Dto>();
        ObjectDataRes<JcaAccountOrgDto> listTemp = accountOrgService.search(requestParams, pageable);
        List<Select2Dto> sl2 = listTemp.getDatas().stream().map(p -> {
            Select2Dto dto = new Select2Dto();
            dto.setId(p.getOrgId().toString());
            dto.setName(p.getOrgCode());
            dto.setText(p.getOrgName());
            return dto;
        }).collect(Collectors.toList());

        res.setTotalData(listTemp.getTotalData());
        res.setDatas(sl2);
        return res;
    }

    @Override
    public ObjectDataRes<Select2Dto> getListSelect2DtoStatus(Long processDeployId, Long formId , MultiValueMap<String, String> requestParams,
            Pageable pageable) throws DetailException {
        ObjectDataRes<Select2Dto> res = new ObjectDataRes<Select2Dto>();
               
        List<Select2Dto> sl2 = new ArrayList<>();
        if (null == formId) {
            List<JcaConstantDto> constantStatusCommon = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(AppCoreConstant.JCA_APP_PROFILE_STATUS, AppCoreConstant.PROFILE_STATUS_TYPE,UserProfileUtils.getLanguage());
            
            sl2 = constantStatusCommon.stream().map(p -> {
                Select2Dto dto = new Select2Dto();
                dto.setId(p.getCode());
                dto.setName(p.getCode());
                dto.setText(p.getName());
                return dto;
            }).collect(Collectors.toList());
            res.setTotalData(constantStatusCommon.size());
            res.setDatas(sl2);
        }else {
            if (null == processDeployId) {
                List<JpmProcessDeployDto> processDto = processDeployService.getJpmProcessDeployDtoByFormId(formId);
                processDeployId = processDto.get(0).getProcessDeployId();
            }
            
            List<JpmStatusDeployDto> listTemp = jpmStatusDeployService.getStatusDeployDtosByProcessDeployId(processDeployId);
            int count = jpmStatusDeployService.countStatusDeployDtosByProcessDeployId(processDeployId);

            sl2 = listTemp.stream().map(p -> {
                Select2Dto dto = new Select2Dto();
                dto.setId(p.getStatusCode());
                dto.setName(p.getStatusCode());
                dto.setText(p.getStatusName());
                return dto;
            }).collect(Collectors.toList());
            res.setTotalData(count);
            res.setDatas(sl2);
        }
        return res;
    }

    @Override
    public ObjectDataRes<Select2Dto> getListSelect2DtoProcess(MultiValueMap<String, String> requestParams, Pageable pageable)
            throws Exception {
        ObjectDataRes<Select2Dto> res = new ObjectDataRes<Select2Dto>();

        ObjectDataRes<JpmProcessDeployDto> listTemp = processDeployService.search(requestParams, pageable);
        List<Select2Dto> sl2 = listTemp.getDatas().stream().map(p -> {
            Select2Dto dto = new Select2Dto();
            dto.setId(p.getProcessDeployId().toString());
            dto.setName(p.getProcessCode());
            dto.setText(p.getProcessName());
            return dto;
        }).collect(Collectors.toList());

        res.setTotalData(listTemp.getTotalData());
        res.setDatas(sl2);

        return res;
    }

    @Override
    public ObjectDataRes<Select2Dto> getListSelect2DtoForm(MultiValueMap<String, String> requestParams, Pageable pageable)
            throws Exception {
        ObjectDataRes<Select2Dto> res = new ObjectDataRes<Select2Dto>();

        ObjectDataRes<EfoFormDto> listTemp = formService.search(requestParams, pageable);
        List<Select2Dto> sl2 = listTemp.getDatas().stream().map(p -> {
            Select2Dto dto = new Select2Dto();
            dto.setId(p.getFormId().toString());
            dto.setName(p.getFormId().toString());
            dto.setText(p.getFormName());
            return dto;
        }).collect(Collectors.toList());

        res.setTotalData(listTemp.getTotalData());
        res.setDatas(sl2);

        return res;
    }

    @Override
    public ObjectDataRes<Select2Dto> getListSelect2DtoConstantDisplay(MultiValueMap<String, String> requestParams, Pageable pageable)
            throws Exception {
        ObjectDataRes<Select2Dto> res = new ObjectDataRes<Select2Dto>();
        requestParams.add("langCode", UserProfileUtils.getLanguage());       
        // get list constant
        ObjectDataRes<JcaConstantDto> listTemp = constantService.search(requestParams, pageable);
        // convert list constant to select2dto
        List<Select2Dto> sl2 = listTemp.getDatas().stream().map(temp -> {
            Select2Dto obj = new Select2Dto();
            obj.setId(temp.getCode());
            obj.setName(temp.getCode());
//            if (temp.getLanguages().size() > 0) {
//                obj.setText(temp.getLanguages().get(0).getName());
//            }
//            obj.setClazz(temp.getNote());
            obj.setText(temp.getName());
            return obj;

        }).collect(Collectors.toList());

        res.setTotalData(listTemp.getTotalData());
        res.setDatas(sl2);

        return res;
    }

    @Override
    public ObjectDataRes<Select2Dto> getListSelect2DtoCategory(MultiValueMap<String, String> requestParams, Pageable pageable)
            throws Exception {
        ObjectDataRes<Select2Dto> res = new ObjectDataRes<Select2Dto>();

        ObjectDataRes<EfoCategoryDto> listTemp = categoryService.search(requestParams, pageable);
        List<Select2Dto> sl2 = listTemp.getDatas().stream().map(p -> {
            Select2Dto dto = new Select2Dto();
            dto.setId(p.getCategoryId().toString());
            dto.setName(p.getCategoryId().toString());
            dto.setText(p.getCategoryName());
            return dto;
        }).collect(Collectors.toList());

        res.setTotalData(listTemp.getTotalData());
        res.setDatas(sl2);

        return res;
    }

    @Override
    public ObjectDataRes<MultiSelectDto> getListMultiSelectDtoTeam(MultiValueMap<String, String> requestParams, Pageable pageable)
            throws DetailException {
        ObjectDataRes<MultiSelectDto> res = new ObjectDataRes<MultiSelectDto>();
        // get list
        ObjectDataRes<JcaTeamDto> listTemp = teamService.search(requestParams, pageable);
        // convert list to MultiSelectDto
        List<MultiSelectDto> sl2 = listTemp.getDatas().stream().map(temp -> {
            MultiSelectDto obj = new MultiSelectDto();
            obj.setValue(temp.getTeamId().toString());
            obj.setLabel(temp.getName());
            return obj;

        }).collect(Collectors.toList());

        res.setTotalData(listTemp.getTotalData());
        res.setDatas(sl2);

        return res;
    }

}
