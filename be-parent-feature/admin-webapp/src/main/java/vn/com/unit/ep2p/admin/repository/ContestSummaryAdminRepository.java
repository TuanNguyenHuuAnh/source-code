package vn.com.unit.ep2p.admin.repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.entity.ContestSummaryAdmin;
import vn.com.unit.ep2p.admin.entity.NotifyAdmin;
import vn.com.unit.ep2p.dto.NotifyAdminEditDto;

import java.util.Date;
import java.util.List;

public interface ContestSummaryAdminRepository extends DbRepository<ContestSummaryAdmin, Long> {


    List<ContestSummaryAdmin> findContestSaveDetail(Date dateNow);

    @Modifying
	void updateCheckSave(Long id);
}
