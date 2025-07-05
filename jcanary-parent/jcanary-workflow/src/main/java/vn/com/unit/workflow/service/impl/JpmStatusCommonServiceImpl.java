/*******************************************************************************
 * Class        ：JpmStatusCommonServiceImpl
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：KhuongTH
 * Change log   ：2021/03/04：01-00 KhuongTH create a new
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

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.entity.JpmStatusCommon;
import vn.com.unit.workflow.repository.JpmStatusCommonRepository;
import vn.com.unit.workflow.service.JpmStatusCommonService;

/**
 * <p>JpmStatusCommonServiceImpl</p>.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStatusCommonServiceImpl implements JpmStatusCommonService {

    @Autowired
    private JpmStatusCommonRepository jpmStatusCommonRepository;
    
    @Override
    public DbRepository<JpmStatusCommon, Long> initRepo() {
        return jpmStatusCommonRepository;
    }

    @Override
    public List<Select2Dto> getStatusCommonSelect2Dtos(String lang) {
        List<Select2Dto> select2Dtos = new ArrayList<>();

        List<JpmStatusCommonDto> statusList =  jpmStatusCommonRepository.getStatusCommonDtoByLang(lang);
        if (CommonCollectionUtil.isNotEmpty(statusList)) {
            for (JpmStatusCommonDto status : statusList) {
                String id = status.getStatusCode();
                String name = status.getStatusName();
                String text = status.getStatusName();
                Select2Dto item = new Select2Dto(id, name, text);
                select2Dtos.add(item);
            }
        }
        return select2Dtos;
    }

    @Override
    public Map<String, Long> getStatusCommonIdConverter() {
        Iterable<JpmStatusCommon> iterable = jpmStatusCommonRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false).filter(i -> 0L == i.getDeletedId())
                .collect(Collectors.toMap(JpmStatusCommon::getStatusCode, JpmStatusCommon::getId));
    }

    @Override
    public JpmStatusCommonDto getStatusCommon(String statusCode, String lang) {
        return jpmStatusCommonRepository.findStatusCommon(statusCode, lang);
    }
}
