/*******************************************************************************
* Class        JpmButtonDefaultServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonIterableUtil;
import vn.com.unit.workflow.dto.JpmButtonDto;
import vn.com.unit.workflow.dto.JpmButtonLangDto;
import vn.com.unit.workflow.entity.JpmButtonDefault;
import vn.com.unit.workflow.repository.JpmButtonDefaultLangRepository;
import vn.com.unit.workflow.repository.JpmButtonDefaultRepository;
import vn.com.unit.workflow.service.JpmButtonDefaultService;

/**
 * JpmButtonDefaultServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmButtonDefaultServiceImpl implements JpmButtonDefaultService {

    @Autowired
    private JpmButtonDefaultRepository jpmButtonDefaultRepository;

    @Autowired
    private JpmButtonDefaultLangRepository jpmButtonDefaultLangRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<JpmButtonDto> getListButtonDefault() {
        List<JpmButtonDto> buttonDtos = new ArrayList<>();
        Iterable<JpmButtonDefault> buttonDefaults = jpmButtonDefaultRepository.findAll();
        if (CommonIterableUtil.isNotEmpty(buttonDefaults)) {
            List<JpmButtonLangDto> buttonDefaultLangs = jpmButtonDefaultLangRepository.getListButtonLangDefault();
            Map<Long, List<JpmButtonLangDto>> langMap = StreamSupport.stream(buttonDefaultLangs.spliterator(), false)
                    .collect(Collectors.groupingBy(JpmButtonLangDto::getButtonId));

            for (JpmButtonDefault buttonDefault : buttonDefaults) {
                Long buttonId = buttonDefault.getId();
                JpmButtonDto buttonDto = objectMapper.convertValue(buttonDefault, JpmButtonDto.class);
                List<JpmButtonLangDto> buttonLangs = langMap.get(buttonId);
                buttonLangs.forEach(b -> {
                    b.setButtonId(null);
                });
                buttonDto.setButtonLangs(buttonLangs);
                buttonDtos.add(buttonDto);
            }
        }
        return buttonDtos;
    }

}