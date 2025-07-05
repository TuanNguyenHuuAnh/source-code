package vn.com.unit.cms.admin.all.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import vn.com.unit.cms.admin.all.entity.Information;
import vn.com.unit.cms.admin.all.repository.InformationRepository;
import vn.com.unit.cms.admin.all.service.InformationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InformationServiceImpl implements InformationService {
	@Autowired
	InformationRepository infoRep;
	
	public String findTextInfo(long infoId) {
		//Information info = infoRep.findOne(infoId);
		Information info = infoRep.findInfo(infoId);
		
		//info.setInfoText("new test");
		//infoRep.save(info);
		
		//int a = 199/0;
		
		if (info != null) {
			return info.getInfoText();
		} else {
			return "";
		}
	}
}
