/*******************************************************************************
 * Class        ：EfoOzDocMainFileVersionServiceImpl
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：tantm
 * Change log   ：2020/12/03：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.enumdef.DocumentActionFlag;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileVersionDto;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocMainFile;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocMainFileVersion;
import vn.com.unit.ep2p.core.efo.repository.EfoOzDocMainFileVersionRepository;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocMainFileVersionService;

/**
 * EfoOzDocMainFileVersionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoOzDocMainFileVersionServiceImpl implements EfoOzDocMainFileVersionService {

    @Autowired
    private EfoOzDocMainFileVersionRepository efoOzDocMainFileVersionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JCommonService commonService;

    @Override
    public void saveFromEfoOzDocMainFile(EfoOzDocMainFile efoOzDocMainFile, DocumentActionFlag actionFlag) {
        EfoOzDocMainFileVersion efoOzDocMainFileVersion = objectMapper.convertValue(efoOzDocMainFile, EfoOzDocMainFileVersion.class);

        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();

//        efoOzDocMainFileVersion.setMainFileId(efoOzDocMainFile.getId());
        efoOzDocMainFileVersion.setId(null);
        efoOzDocMainFileVersion.setCreatedId(userId);
        efoOzDocMainFileVersion.setCreatedDate(sysDate);
        efoOzDocMainFileVersion.setUpdatedId(userId);
        efoOzDocMainFileVersion.setUpdatedDate(sysDate);
//        efoOzDocMainFileVersion.setAction(actionFlag.toString());

        efoOzDocMainFileVersionRepository.create(efoOzDocMainFileVersion);
    }

    @Override
    public List<EfoOzDocMainFileVersionDto> getListEfoOzDocMainFileVersionDtoByOzDocId(Long docId) {
        return efoOzDocMainFileVersionRepository.getListEfoOzDocMainFileVersionDtoByOzDocId(docId);
    }

}
