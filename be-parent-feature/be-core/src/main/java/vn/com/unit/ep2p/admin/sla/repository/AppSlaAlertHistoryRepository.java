package vn.com.unit.ep2p.admin.sla.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.sla.repository.SlaNotiAlertHistoryRepository;

public interface AppSlaAlertHistoryRepository extends SlaNotiAlertHistoryRepository {
	
	@Modifying
    Object CopyAlertToAlertHistoryByIdList(@Param("slaAlerts") List<Long> slaAlerts);

}
