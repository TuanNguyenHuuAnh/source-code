/*******************************************************************************
 * Class        ：MasterCommonServiceImpl
 * Created date ：2021/01/26
 * Lasted date  ：2021/01/26
 * Author       ：taitt
 * Change log   ：2021/01/26：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.service.JcaAccountOrgService;
import vn.com.unit.dts.utils.DtsCollectionUtil;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.MasterCommonService;

/**
 * MasterCommonServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MasterCommonServiceImpl implements MasterCommonService{

    @Autowired
    private JcaAccountOrgService jcaAccountOrgService;
    
    @Override
    public Map<String,List<Long>> getMapOrgAndPosByAccountId(Long accountId){
        Map<String,List<Long>> mapResult = new HashMap<>();
        List<JcaAccountOrgDto> jcaAccountOrgs = jcaAccountOrgService.getListJcaAccountOrgDtoByAccountId(accountId);
        List<Long> posIds = DtsCollectionUtil.isNotEmpty(jcaAccountOrgs) ? jcaAccountOrgs.stream().map(JcaAccountOrgDto::getPositionId).collect(Collectors.toList()) : new ArrayList<>();
        List<Long> orgIds = DtsCollectionUtil.isNotEmpty(jcaAccountOrgs) ? jcaAccountOrgs.stream().map(JcaAccountOrgDto::getOrgId).collect(Collectors.toList()) : new ArrayList<>();
        
        mapResult.put(AppCoreConstant.PARAMETTER_POS_IDS, posIds);
        mapResult.put(AppCoreConstant.PARAMETTER_ORG_IDS, orgIds);
        
        return mapResult;
    }
    
    @Override
    public <T extends Enum<T>> List<EnumsParamSearchRes> getEnumsParamSearchResForEnumClass(T[] enumData) {
        List<EnumsParamSearchRes> results = new ArrayList<>();
        Stream.of(enumData).forEach(item -> {
                EnumsParamSearchRes result = new EnumsParamSearchRes();
                result.setLabel(item.name());
                result.setValue(item.toString());
                results.add(result);
        });

        return results;                
    }
}
