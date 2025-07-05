package vn.com.unit.ep2p.admin.repository;

import org.apache.ibatis.annotations.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.entity.ConfirmDecree;

public interface ConfirmDecreeRepository extends DbRepository<ConfirmDecree, Long> {
	public ConfirmDecree findByUserName(@Param("userName") String userName);
	
	public int checkConfirmSopByUserName(@Param("userName") String userName);

	@Modifying
    public void updateConfirmDecreeByUserName(@Param("userName") String userName);
}
