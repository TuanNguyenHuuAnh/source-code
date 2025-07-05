/**
 * @author TaiTM
 * @date 2021-03-16
 */
package vn.com.unit.ep2p.core.ers.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.core.ers.repository.Select2DataRepository;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;

/**
 * @author TaiTM
 * @date 2021-03-16
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class Select2DataServiceImpl implements Select2DataService {

    @Autowired
    private Select2DataRepository select2DataRepository;

    @Override
    public List<Select2Dto> getProvinceList() {
        return select2DataRepository.findProvinceList();
    }

    @Override
    public List<Select2Dto> getAllDistrictByProvince(String proivnce) {
        return select2DataRepository.findAllDistrictByCondition(proivnce);
    }

    @Override
    public List<Select2Dto> getAllWardByDistrict(String province, String district) {
        return select2DataRepository.findAllWardByCondition(province, district);
    }
    
    @Override
    public List<Select2Dto> getAllBankBranchByProvince(String proivnce, String lang) {
        return select2DataRepository.findAllBankBranchByCondition(proivnce, lang);
    }
    
    @Override
    public List<Select2Dto> getConstantData(String groupCode, String kind, String code, String lang) {
        return select2DataRepository.findConstantData(groupCode, kind, code, lang);
    }

    @Override
    public List<Select2Dto> getRegionBancasList() {
        return select2DataRepository.findRegionBancasList();
    }

	@Override
	public List<Select2Dto> getConstantData(String groupCode, String kind, String lang) {
		return getConstantData(groupCode, kind, null, lang);
	}

    @Override
    public List<Select2Dto> getManagerByRegionBancas(String regionManagerCode) {
        return select2DataRepository.findManagerByRegionBancas(regionManagerCode);
    }

    @Override
    public List<Select2Dto> getClassInfoNoStartBancas() {
        return select2DataRepository.findClassInfoNoStartBancas();
    }

    @Override
    public List<Select2Dto> getRMManagerBancas() {
        return select2DataRepository.findRMManagerBancas();
    }

    @Override
    public List<Select2Dto> getRMAMManagerBancas() {
        return select2DataRepository.findRMAMManagerBancas();
    }

    @Override
    public List<Select2Dto> getSecurityQuestion(String lang) {
        return select2DataRepository.findSecurityQuestion(lang);
    }

    @Override
    public List<Select2Dto> getListItem(Long companyId) {
        return select2DataRepository.findListItem(companyId);
    }

    @Override
    public List<Select2Dto> getListStatusForProcess(String businessCode, String languageCode, Long companyId) {
        return select2DataRepository.findListStatusForProcess(businessCode, languageCode, companyId);
    }

    @Override
    public List<Select2Dto> getListFaqsCategory(String languageCode) {
        return select2DataRepository.findListFaqsCategory(languageCode);
    }

    @Override
    public List<Select2Dto> getListDocumentCategory(String languageCode, String channel) {
        return select2DataRepository.findListDocumentCategory(languageCode, channel);
    }
    
	@Override
	public List<Select2Dto> getListBanner(String bannerDevice, String bannerType) {
		 List<Select2Dto> lstDto = null;
		try {
			lstDto = select2DataRepository.findListBanner( bannerDevice, bannerType);
		} catch(Exception e) {
			System.out.print(e);
		}
		return lstDto;
	}

	@Override
	public List<Select2Dto> getListConstantByKindOrderBy(String groupCode, String kind, String lang) {
		return select2DataRepository.getListConstantByKindOrderBy(groupCode, kind, lang);
	}
}