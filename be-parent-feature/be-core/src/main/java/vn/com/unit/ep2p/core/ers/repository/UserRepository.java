package vn.com.unit.ep2p.core.ers.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.req.dto.UserReq;

@Transactional
public interface UserRepository extends DbRepository<UserReq, Long> {

	UserReq findUserByUsername(@Param("username") String username);
	
	@Modifying
	int updateUserByUsername(@Param("username") String username);
}