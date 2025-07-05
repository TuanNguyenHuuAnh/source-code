/*******************************************************************************
 * Class        ：JpmLanguageServiceImpl
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：KhuongTH
 * Change log   ：2021/03/04：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.workflow.dto.JpmLanguageDto;
import vn.com.unit.workflow.repository.JpmLanguageRepository;
import vn.com.unit.workflow.service.JpmLanguageService;

/**
 * <p>
 * JpmLanguageServiceImpl
 * </p>
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class JpmLanguageServiceImpl implements JpmLanguageService {

    @Autowired
    private JpmLanguageRepository jpmLanguageRepository;

    @Override
    public Map<String, Long> getLanguageIdConverter() {
        List<JpmLanguageDto> languages = jpmLanguageRepository.getDefaultLanguages();
        if (CommonCollectionUtil.isNotEmpty(languages))
            return languages.stream().collect(Collectors.toMap(JpmLanguageDto::getLangCode, JpmLanguageDto::getLangId));
        else
            return new HashMap<>();
    }

}
