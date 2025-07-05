package vn.com.unit.ep2p.admin.sla.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.sla.repository.SlaNotiAlertRepository;

public interface AppSlaAlertRepository extends SlaNotiAlertRepository{

//	List<SlaAlert> findListScheduledAlert( @Param("companyId") Long companyId, @Param("startDate") Date startDate,@Param("endDate") Date endDate, @Param("count") int count);
	
	@Modifying
    void deleteByIdList(@Param("slaAlerts") List<Long> slaAlerts);
	
	@Modifying
	void deleteByTaskId(@Param("id") Long id);
	
	@Modifying
	void updateStatusAndCountOfErrorByIds(@Param("status") Integer status, @Param("count") Integer count, @Param("ids") List<Long> ids);
	
	List<Long> getListTransfer(@Param("docId")Long docId, @Param("taskId")Long taskId);
}
