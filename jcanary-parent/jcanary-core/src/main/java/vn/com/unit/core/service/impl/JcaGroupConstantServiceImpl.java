/*******************************************************************************
 * Class        ：JcaGroupConstantServiceImpl
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：tantm
 * Change log   ：2020/12/23：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaGroupConstantDto;
import vn.com.unit.core.dto.JcaGroupConstantLangDto;
import vn.com.unit.core.dto.JcaGroupConstantSearchDto;
import vn.com.unit.core.entity.JcaConstant;
import vn.com.unit.core.entity.JcaGroupConstant;
import vn.com.unit.core.repository.JcaGroupConstantRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.JcaGroupConstantLangService;
import vn.com.unit.core.service.JcaGroupConstantService;

/**
 * JcaGroupConstantServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaGroupConstantServiceImpl implements JcaGroupConstantService {

	@Autowired
    private JcaGroupConstantRepository jcaGroupConstantRepository;

    @Autowired
    private JcaGroupConstantLangService jcaGroupConstantLanguageService;

    @Autowired
    private JcaConstantService jcaConstantService;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaGroupConstant saveJcaGroupConstantDto(JcaGroupConstant objectDto) {

    	 Date sysDate = CommonDateUtil.getSystemDateTime();
         Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
         JcaGroupConstantDto obj = jcaConstantService.getJcaGroupConstantDtoByGroupCode(objectDto.getCode());

		CommonObjectUtil.copyPropertiesNonNull(objectDto, obj);
    	if (ObjectUtils.isNotEmpty(obj)) {
    		objectDto.setUpdatedDate(sysDate);
    		objectDto.setUpdatedId(userId);
            jcaGroupConstantRepository.update(objectDto);
        	return objectDto;
        } else {
        	objectDto.setCreatedDate(sysDate);
        	objectDto.setCreatedId(userId);
            jcaGroupConstantRepository.create(objectDto);
            return objectDto;
        }

    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public JcaGroupConstant saveJcaGroupConstant(JcaGroupConstant jcaGroupConstant) {
//
//        Date sysDate = CommonDateUtil.getSystemDateTime();
//        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
//        Long id = jcaGroupConstant.getId();
//        if (null != id) {
//            JcaGroupConstant oldGroupConstant = this.getJcaGroupConstantById(id);
//            CommonObjectUtil.copyPropertiesNonNull(jcaGroupConstant, oldGroupConstant);
//            oldGroupConstant.setUpdatedDate(sysDate);
//            oldGroupConstant.setUpdatedId(userId);
//            jcaGroupConstantRepository.update(oldGroupConstant);
//            return oldGroupConstant;
//        } else {
//            jcaGroupConstant.setCreatedDate(sysDate);
//            jcaGroupConstant.setCreatedId(userId);
//            jcaGroupConstant.setUpdatedDate(sysDate);
//            jcaGroupConstant.setUpdatedId(userId);
//            jcaGroupConstantRepository.create(jcaGroupConstant);
//            return jcaGroupConstant;
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public JcaGroupConstantDto saveJcaGroupConstantDto(JcaGroupConstantDto objectDto) {
//
//        JcaGroupConstant jcaGroupConstant = objectMapper.convertValue(objectDto, JcaGroupConstant.class);
//        // save JcaGroupConstant
//        this.saveJcaGroupConstant(jcaGroupConstant);
//        objectDto.setId(jcaGroupConstant.getId());
//
//        // save JcaGroupConstantLanguage
//        List<JcaGroupConstantLangDto> listJcaGroupConstantLanguageDto = jcaGroupConstantLanguageService
//                .saveListJcaGroupConstantLanguage(jcaGroupConstant.getId(), objectDto.getLanguages());
//        objectDto.setLanguages(listJcaGroupConstantLanguageDto);
//
//        // save JcaConstant
//        objectDto.getConstants().forEach(temp -> {
//            temp.setGroupCode(jcaGroupConstant.getCode());
//            jcaConstantService.saveJcaConstantDto(temp);
//        });
//
//        return objectDto;
//
//    }
//
//    @Override
//    public JcaGroupConstant getJcaGroupConstantById(Long id) {
//        return jcaGroupConstantRepository.findOne(id);
//    }
//
//    @Override
//    public JcaGroupConstantDto getJcaGroupConstantDtoById(Long id) {
//        List<JcaGroupConstantDto> listJcaGroupConstantDto = jcaGroupConstantRepository.getJcaGroupConstantDtoById(id);
//        JcaGroupConstantDto jcaGroupConstantDto = new JcaGroupConstantDto();
//        if (CommonCollectionUtil.isNotEmpty(listJcaGroupConstantDto)) {
//            jcaGroupConstantDto = buildJcaGroupConstantDto(listJcaGroupConstantDto).get(0);
//            List<JcaConstantDto> constantTemp = jcaConstantService.getListJcaConstantDtoByGroupCode(jcaGroupConstantDto.getCode(),
//                    jcaGroupConstantDto.getCompanyId());
//            if (constantTemp != null) {
//                jcaGroupConstantDto.setConstants(constantTemp);
//            }
//        }
//        return jcaGroupConstantDto;
//    }
//
//    @Override
//    public int countJcaGroupConstantByCondition(JcaGroupConstantSearchDto jcaGroupConstantSearchDto) {
//        Integer count = jcaGroupConstantRepository.countJcaGroupConstantByCondition(jcaGroupConstantSearchDto);
//        return count == null ? 0 : count;
//    }
//
//    @Override
//    public List<JcaGroupConstantDto> getListJcaGroupConstantDtoByCondition(JcaGroupConstantSearchDto jcaGroupConstantSearchDto,
//            Pageable pageable) {
//        List<JcaGroupConstantDto> listJcaGroupConstantDto = jcaGroupConstantRepository
//                .getListJcaGroupConstantDtoByCondition(jcaGroupConstantSearchDto, pageable).getContent();
//
//        if (CommonCollectionUtil.isNotEmpty(listJcaGroupConstantDto)) {
//            return buildJcaGroupConstantDto(listJcaGroupConstantDto);
//        }
//        return null;
//    }
//
//    private List<JcaGroupConstantDto> buildJcaGroupConstantDto(List<JcaGroupConstantDto> listJcaGroupConstantDto) {
//        List<JcaGroupConstantDto> groupTemp = listJcaGroupConstantDto.stream().map(g -> {
//            JcaGroupConstantDto gc = new JcaGroupConstantDto();
//            gc.setId(g.getId());
//            gc.setCode(g.getCode());
//            gc.setCompanyId(g.getCompanyId());
//            gc.setDisplayOrder(g.getDisplayOrder());
//            gc.getLanguages().add(new JcaGroupConstantLangDto(g.getLanguageId(), g.getId(), g.getLanguageCode(), g.getText()));
//            return gc;
//        }).collect(Collectors.collectingAndThen(Collectors.toMap(JcaGroupConstantDto::getId, Function.identity(), (gc1, gc2) -> {
//            gc1.getLanguages().addAll(gc2.getLanguages());
//            return gc1;
//        }), m -> new ArrayList<JcaGroupConstantDto>(m.values())));
//
//        return groupTemp;
//    }
//
//    @Override
//    public List<JcaGroupConstantDto> getListJcaGroupConstantDtoHaveConstantByCondition(JcaGroupConstantSearchDto jcaGroupConstantSearchDto,
//            Pageable pageable) {
//        List<JcaGroupConstantDto> groupTemp = this.getListJcaGroupConstantDtoByCondition(jcaGroupConstantSearchDto, pageable);
//        List<JcaConstantDto> constantTemp = jcaConstantService.getListJcaConstantDtoByGroup(jcaGroupConstantSearchDto, pageable);
//        groupTemp.stream().forEach(g -> {
//            List<JcaConstantDto> constants = constantTemp.stream().filter(c -> c.getGroupCode().equals(g.getCode()))
//                    .collect(Collectors.toList());
//            g.setConstants(constants);
//        });
//        return groupTemp.stream().sorted(Comparator.comparing(JcaGroupConstantDto::getCode)).collect(Collectors.toList());
//    }
//
//    @Override
//    public boolean checkGroupCodeExists(String code, Long companyId) {
//        return jcaGroupConstantRepository.countJcaGroupConstantByGroupCode(code, companyId) > 0;
//    }
}
