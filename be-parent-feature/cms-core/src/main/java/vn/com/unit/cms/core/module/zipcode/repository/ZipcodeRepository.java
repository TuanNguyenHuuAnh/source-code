package vn.com.unit.cms.core.module.zipcode.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;

public interface ZipcodeRepository extends DbRepository<Notify, Long> {
	List<Select2Dto> findAllProvince();
	List<Select2Dto> findAllDistrictByCondition(@Param("province") String province);
	List<Select2Dto> findAllWardByCondition(@Param("province") String province, @Param("district") String district);
}
