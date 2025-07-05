package vn.com.unit.ep2p.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.admin.repository.AppQrtzMJobLogRepository;
import vn.com.unit.ep2p.admin.service.QrtzMJobLogWebappService;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;

@Service
@Transactional
public class QrtzMJobLogWebappServiceImpl implements QrtzMJobLogWebappService {

	@Autowired
	AppQrtzMJobLogRepository qMJobLogRepository;

	@Override
	public QrtzMJobLog save(QrtzMJobLog job) {
		return qMJobLogRepository.save(job);
	}

	@Override
	public QrtzMJobLog getByJobNameRefAndStartTime(Long id) {
		QrtzMJobLog qMJobLog = qMJobLogRepository.getByJobNameRefAndStartTime(id);
		return qMJobLog == null ? new QrtzMJobLog() : qMJobLog;
	}

    @Override
    public QrtzMJobLog getByJobNameRef(String jobNameRef) {
        QrtzMJobLog qMJobLog = qMJobLogRepository.getByJobNameRef(jobNameRef);
        return qMJobLog == null ? new QrtzMJobLog() : qMJobLog;
    }
}
