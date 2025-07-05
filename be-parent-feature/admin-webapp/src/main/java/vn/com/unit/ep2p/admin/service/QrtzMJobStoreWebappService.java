package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.quartz.job.entity.QrtzMJobStore;

public interface QrtzMJobStoreWebappService {
	public QrtzMJobStore getByGroupCode(String groupCode);

	public QrtzMJobStore getById(Long id);

	public List<String> getGroupIds();

	boolean isGroupCodeExists(String groupCode);

	public boolean save(QrtzMJobStore jobStoreEntity);
}
