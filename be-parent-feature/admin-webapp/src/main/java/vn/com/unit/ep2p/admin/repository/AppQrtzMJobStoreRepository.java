package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;

@Repository
public interface AppQrtzMJobStoreRepository extends DbRepository<QrtzMJobStore, Long> {
	public QrtzMJobStore getByGroupCode(@Param("groupCode") String groupCode);

	public List<String> getGroupIds();
}
