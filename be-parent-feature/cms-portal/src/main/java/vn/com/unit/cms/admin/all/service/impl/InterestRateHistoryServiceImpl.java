package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.InterestRateHistoryDto;
import vn.com.unit.cms.admin.all.dto.InterestRateListDto;
import vn.com.unit.cms.admin.all.entity.InterestRateHistory;
import vn.com.unit.cms.admin.all.repository.InterestRateHistoryRepository;
import vn.com.unit.cms.admin.all.service.InterestRateHistoryService;
import vn.com.unit.cms.admin.all.service.InterestRateService;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.jcanary.entity.CityLanguage;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.utils.Utility;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class InterestRateHistoryServiceImpl implements InterestRateHistoryService{
	
	@Autowired
	InterestRateHistoryRepository interestRateHistoryRepository;
	
	@Autowired
	InterestRateService interestRateService;
	@Autowired
	private SystemConfig systemConfig;
	
	@Override
	public void insert(InterestRateListDto interestRateListDto) {
		InterestRateHistory interestRateHistory = new InterestRateHistory();
		interestRateHistory.setInterestId(interestRateListDto.getInterestId());
		interestRateHistory.setCityId(interestRateListDto.getCityId());
		interestRateHistoryRepository.insert(interestRateHistory);
	}
	
	@Override
	public PageWrapper<InterestRateHistoryDto> list(Long cityId, int page, Locale locale) {
		int pageSize = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<InterestRateHistoryDto> pageWrapper = new PageWrapper<InterestRateHistoryDto>(page, pageSize);
		
		int count = interestRateHistoryRepository.countByCity(cityId);
		
		List<InterestRateHistoryDto> historyList = new ArrayList<>();
		//if(count > 0){
			int offsetSQL = Utility.calculateOffsetSQL(page, pageSize);
			historyList = interestRateHistoryRepository.findAllByCity(offsetSQL, pageSize, cityId);
		//}
		List<CityLanguage> cityList = interestRateService.getCityList(locale.toString());
		Map<Long, String> cityMap = createCityMap(cityList);
		fillCityName(historyList, cityMap);
		
		pageWrapper.setDataAndCount(historyList, count);
		return pageWrapper;
	}

	private void fillCityName(List<InterestRateHistoryDto> historyList, Map<Long, String> cityMap) {
		for(InterestRateHistoryDto item : historyList){
			item.setCityName(cityMap.get(item.getCityId()));
		}
	}

	private Map<Long, String> createCityMap(List<CityLanguage> cityList) {
		Map<Long, String> cityMap = new HashMap<>();
		for(CityLanguage item : cityList){
			cityMap.put(item.getmCityId(), item.getName());
		}
		return cityMap;
	}
	
	
}
