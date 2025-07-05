/*******************************************************************************
 * Class        ：JcaMenuLanguageServiceImpl
 * Created date ：2021/02/24
 * Lasted date  ：2021/02/24
 * Author       ：taitt
 * Change log   ：2021/02/24：01-00 taitt create a new
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
import vn.com.unit.core.dto.JcaMenuLangDto;
import vn.com.unit.core.entity.JcaMenuLang;
import vn.com.unit.core.repository.JcaMenuLangRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaMenuLangService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaMenuLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaMenuLangServiceImpl implements JcaMenuLangService{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JCommonService commonService;
    
    @Autowired
    private JcaMenuLangRepository jcaMenuLangRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<JcaMenuLangDto> saveListJcaMenuLanguage(Long menuId, List<JcaMenuLangDto> languages) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();

        languages.stream().forEach(item -> {
            item.setMenuId(menuId);
            JcaMenuLang entity = objectMapper.convertValue(item, JcaMenuLang.class);
            JcaMenuLang oldEntity = jcaMenuLangRepository.findOneByPK(entity.getMenuId(), entity.getLangId());
            if (null == oldEntity) {
                entity.setCreatedDate(sysDate);
                entity.setCreatedId(userId);
                entity.setUpdatedDate(sysDate);
                entity.setUpdatedId(userId);
                jcaMenuLangRepository.create(entity);
                CommonObjectUtil.copyPropertiesNonNull(entity, item);
            } else {
                CommonObjectUtil.copyPropertiesNonNull(entity, oldEntity);
                entity.setUpdatedDate(sysDate);
                entity.setUpdatedId(userId);
                jcaMenuLangRepository.update(entity);
                CommonObjectUtil.copyPropertiesNonNull(oldEntity, item);
            }
        });
        return languages;
    }

    @Override
    public DbRepository<JcaMenuLang, Long> initRepo() {
        return jcaMenuLangRepository;
    }

    @Override
    public int deleteJcaMenuLangByMenuId(Long id) {
        return jcaMenuLangRepository.deleteJcaMenuLangByMenuId(id);
    }

    @Override
    public List<JcaMenuLangDto> getJcaMenuLangDtoListDefault() {
        return jcaMenuLangRepository.getJcaMenuLangDtoListDefault();
    }

    @Override
    public JcaMenuLang saveJcaMenuLangDto(JcaMenuLangDto jcaMenuLangDto) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        JcaMenuLang jcaMenuLang = objectMapper.convertValue(jcaMenuLangDto, JcaMenuLang.class);
        JcaMenuLang jcaMenuLangOld = jcaMenuLangRepository.findOneByPK(jcaMenuLangDto.getMenuId(), jcaMenuLangDto.getLangId());
        if (null == jcaMenuLangOld) {
            jcaMenuLang.setCreatedDate(sysDate);
            jcaMenuLang.setCreatedId(userId);
            jcaMenuLang.setUpdatedDate(sysDate);
            jcaMenuLang.setUpdatedId(userId);
            return jcaMenuLangRepository.create(jcaMenuLang);
        } else {
            CommonObjectUtil.copyPropertiesNonNull(jcaMenuLang, jcaMenuLangOld);
            jcaMenuLangOld.setUpdatedDate(sysDate);
            jcaMenuLangOld.setUpdatedId(userId);
            return jcaMenuLangRepository.update(jcaMenuLangOld);
        }
    }

}
