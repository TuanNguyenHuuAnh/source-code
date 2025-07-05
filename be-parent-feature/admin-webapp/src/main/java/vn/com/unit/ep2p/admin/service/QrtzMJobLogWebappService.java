package vn.com.unit.ep2p.admin.service;

import vn.com.unit.quartz.job.entity.QrtzMJobLog;

public interface QrtzMJobLogWebappService {
	public QrtzMJobLog save(QrtzMJobLog job);
	
	public QrtzMJobLog getByJobNameRefAndStartTime(Long id);

    public QrtzMJobLog getByJobNameRef(String jobNameRef);
}
