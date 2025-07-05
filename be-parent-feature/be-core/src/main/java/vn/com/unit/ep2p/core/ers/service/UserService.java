package vn.com.unit.ep2p.core.ers.service;

import vn.com.unit.ep2p.core.req.dto.UserReq;

public interface UserService {
	
	public UserReq getUserByUsername(String username) throws Exception;

	UserReq testUpdateUserByUsername(String username);
}
