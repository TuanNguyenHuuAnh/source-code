package vn.com.unit.ep2p.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.customer.repository.ClientNickNameRepository;
import vn.com.unit.ep2p.service.ClientNickNameService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ClientNickNameServiceImpl implements ClientNickNameService{
	
	@Autowired
	private ClientNickNameRepository clientNickNameRepository;

	@Override
	public String getUserName(String agentCode,String customerNo) {
		return clientNickNameRepository.findUsername(agentCode,customerNo);
	}

}
