/*******************************************************************************
 * Class        ：JcaGroupConstantLanguageServiceImpl
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：tantm
 * Change log   ：2020/12/24：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.dto.JcaGroupConstantLangDto;
import vn.com.unit.core.entity.JcaGroupConstantLang;
import vn.com.unit.core.repository.JcaGroupConstantLangRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaGroupConstantLangService;

/**
 * JcaGroupConstantLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaGroupConstantLangServiceImpl implements JcaGroupConstantLangService {

    @Autowired
    private JcaGroupConstantLangRepository jcaGroupConstantLangRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JCommonService commonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<JcaGroupConstantLangDto> saveListJcaGroupConstantLanguage(Long id, List<JcaGroupConstantLangDto> languages) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();

        languages.stream().forEach(item -> {
            item.setGroupId(id);
            JcaGroupConstantLang entity = objectMapper.convertValue(item, JcaGroupConstantLang.class);
            JcaGroupConstantLang oldEntity = jcaGroupConstantLangRepository.getJcaGroupConstantLangByPK(entity.getGroupId(), entity.getLangId());
            if (null == oldEntity) {
                entity.setCreatedDate(sysDate);
                entity.setCreatedId(userId);
                entity.setUpdatedDate(sysDate);
                entity.setUpdatedId(userId);
                jcaGroupConstantLangRepository.create(entity);
                CommonObjectUtil.copyPropertiesNonNull(entity, item);
            } else {
                CommonObjectUtil.copyPropertiesNonNull(entity, oldEntity);
                oldEntity.setUpdatedDate(sysDate);
                oldEntity.setUpdatedId(userId);
                jcaGroupConstantLangRepository.update(oldEntity);
                CommonObjectUtil.copyPropertiesNonNull(oldEntity, item);
            }
        });
        return languages;
    }

}
