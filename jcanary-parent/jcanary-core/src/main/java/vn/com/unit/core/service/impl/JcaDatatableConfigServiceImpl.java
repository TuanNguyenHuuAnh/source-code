/*******************************************************************************
 * Class        ：JcaDatatableConfigServiceImpl
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：vinhlt
 * Change log   ：2021/01/27：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.JcaDatatableDefaultConfigDto;
import vn.com.unit.core.entity.JcaDatatableConfig;
import vn.com.unit.core.repository.JcaDatatableConfigRepository;
import vn.com.unit.core.req.DatatableHeaderReq;
import vn.com.unit.core.service.JcaDatatableConfigService;

/**
 * JcaDatatableConfigServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
@Service
@Transactional(readOnly = true)
public class JcaDatatableConfigServiceImpl implements JcaDatatableConfigService {

    @Autowired
    private JcaDatatableConfigRepository datatableConfigRepository;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaDatatableConfigService#findConfigByRequest(vn.com.unit.core.req.DatatableHeaderReq)
     */
    @Override
    public JcaDatatableConfig findConfigByRequest(DatatableHeaderReq requestConfig) {
        return datatableConfigRepository.findConfigByRequest(requestConfig);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaDatatableConfigService#createOrUpdate(vn.com.unit.core.req.DatatableHeaderReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createOrUpdate(DatatableHeaderReq datatableHeaderReq) {
        JcaDatatableConfig jcaDatatableConfig = datatableConfigRepository.findConfigByRequest(datatableHeaderReq);
        int count = 0;
        if (jcaDatatableConfig.getId() != null) {
            count = datatableConfigRepository.updateConfigByRequest(datatableHeaderReq);
        } else {
            JcaDatatableConfig jcaDatatableConfigNew = new JcaDatatableConfig();
            jcaDatatableConfigNew.setFunctionCode(datatableHeaderReq.getFunctionCode());
            jcaDatatableConfigNew.setUserId(datatableHeaderReq.getUserId());
            jcaDatatableConfigNew.setJsonConfig(datatableHeaderReq.getJsonConfig());
            Date currentDate = new Date();
            jcaDatatableConfigNew.setCreatedDate(currentDate);
            jcaDatatableConfigNew.setCreatedId(datatableHeaderReq.getUserId());
            jcaDatatableConfigNew.setUpdatedDate(currentDate);
            jcaDatatableConfigNew.setUpdatedId(datatableHeaderReq.getUserId());
            datatableConfigRepository.create(jcaDatatableConfigNew);
            count = 1;
        }
        return count;
    }

    @Override
    public JcaDatatableConfig findConfigDefaultByRequest(DatatableHeaderReq datatableHeaderReq) {
        return datatableConfigRepository.findConfigDefaultByRequest(datatableHeaderReq);
    }

    @Override
    public List<JcaDatatableDefaultConfigDto> getListJcaDatatableDefaultConfigDto(String functionCode,
            String langCode) {
        return datatableConfigRepository.findListJcaDatatableDefaultConfigDto(functionCode, langCode);
    }

    @Override
    public List<String> getListCoLumn(String functionCode, String langCode) {
        return datatableConfigRepository.findListColumn(functionCode, langCode);
    }
}
