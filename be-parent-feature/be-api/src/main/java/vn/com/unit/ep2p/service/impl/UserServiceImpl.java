package vn.com.unit.ep2p.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import vn.com.unit.dts.exception.DetailException;
//import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.ers.repository.UserRepository;
import vn.com.unit.ep2p.core.ers.service.UserService;
import vn.com.unit.ep2p.core.req.dto.UserReq;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired	
	private UserRepository userRepository;
	

	@Override
	public UserReq getUserByUsername(String username) throws Exception {
		UserReq user = userRepository.findUserByUsername("ngahtt");
		return user;
	}
	
	@Override
	@Transactional
	public UserReq testUpdateUserByUsername(String username) {
//		System.out.println(TransactionSynchronizationManager.isCurrentTransactionReadOnly());

//		User user = userRepository.findUserByUsername("test");
//		user.setNotes("test");
		userRepository.updateUserByUsername("test");
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            logger.error("Exception ", e);
        }
		return null;
	}
}
