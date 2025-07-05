package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.quartz.job.entity.QrtzMJobType;

public interface AppQrtzMJobTypeRepository extends DbRepository<QrtzMJobType, Long> {
	
	public List<Select2Dto> findListForCombobox();

}
