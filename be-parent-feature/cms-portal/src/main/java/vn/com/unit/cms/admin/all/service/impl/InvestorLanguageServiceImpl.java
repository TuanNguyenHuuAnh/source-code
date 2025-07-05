package vn.com.unit.cms.admin.all.service.impl;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.service.InvestorLanguageService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class InvestorLanguageServiceImpl implements InvestorLanguageService {

//	@Autowired
//	private InvestorLanguageService investorLanguageService;

}
