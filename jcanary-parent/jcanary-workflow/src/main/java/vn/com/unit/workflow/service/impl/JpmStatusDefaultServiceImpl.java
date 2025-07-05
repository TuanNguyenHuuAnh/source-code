/*******************************************************************************
* Class        JpmStatusDefaultServiceImpl
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonIterableUtil;
import vn.com.unit.workflow.dto.JpmStatusDto;
import vn.com.unit.workflow.dto.JpmStatusLangDto;
import vn.com.unit.workflow.entity.JpmStatusDefault;
import vn.com.unit.workflow.repository.JpmStatusDefaultLangRepository;
import vn.com.unit.workflow.repository.JpmStatusDefaultRepository;
import vn.com.unit.workflow.service.JpmStatusDefaultService;

/**
 * JpmStatusDefaultServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStatusDefaultServiceImpl implements JpmStatusDefaultService {

    @Autowired
    private JpmStatusDefaultRepository jpmStatusDefaultRepository;

    @Autowired
    private JpmStatusDefaultLangRepository jpmStatusDefaultLangRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<JpmStatusDto> getListStatusDefault() {
        List<JpmStatusDto> statusDtos = new ArrayList<>();
        Iterable<JpmStatusDefault> statusDefaults = jpmStatusDefaultRepository.findAll();
        if (CommonIterableUtil.isNotEmpty(statusDefaults)) {
            List<JpmStatusLangDto> statusDefaultLangs = jpmStatusDefaultLangRepository.getListStatusLangDefault();
            Map<Long, List<JpmStatusLangDto>> langMap = statusDefaultLangs.stream()
                    .collect(Collectors.groupingBy(JpmStatusLangDto::getStatusId));

            for (JpmStatusDefault statusDefault : statusDefaults) {
                Long statusId = statusDefault.getId();
                JpmStatusDto statusDto = objectMapper.convertValue(statusDefault, JpmStatusDto.class);
                List<JpmStatusLangDto> statusLangs = langMap.get(statusId);
                statusLangs.forEach(b -> b.setStatusId(null));
                statusDto.setStatusLangs(statusLangs);
                statusDtos.add(statusDto);
            }
        }
        return statusDtos;
    }

}