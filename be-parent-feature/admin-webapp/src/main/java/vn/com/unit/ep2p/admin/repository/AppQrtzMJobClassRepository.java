package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.quartz.job.entity.QrtzMJobClass;

public interface AppQrtzMJobClassRepository extends DbRepository<QrtzMJobClass, Long> {
	
	List<Select2Dto> findListForCombobox(@Param("term") String term, @Param(value = "id") Long id);
	
	String findClassNameByPath(@Param("path") String path) throws Exception;

}
