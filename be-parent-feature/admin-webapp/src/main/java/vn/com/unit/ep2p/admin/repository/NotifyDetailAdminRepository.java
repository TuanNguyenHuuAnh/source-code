package vn.com.unit.ep2p.admin.repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.entity.NotifyAdmin;
import vn.com.unit.ep2p.dto.NotifyAdminEditDto;
import vn.com.unit.ep2p.dto.NotifyDetailAdminEditDto;

import java.util.List;

public interface NotifyDetailAdminRepository extends DbRepository<NotifyDetailAdminEditDto, Long> {


}
