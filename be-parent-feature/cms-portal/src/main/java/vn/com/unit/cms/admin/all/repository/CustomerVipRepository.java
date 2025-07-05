package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageResultDto;
import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageSearchDto;
import vn.com.unit.cms.admin.all.entity.CustomerVip;
import vn.com.unit.db.repository.DbRepository;

public interface CustomerVipRepository extends DbRepository<CustomerVip, Long> {

	int countByCustomerSearchDto(@Param("searchDto") CustomerVipLanguageSearchDto searchDto);

	List<CustomerVipLanguageResultDto> findByCustomerSearchDto(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("searchDto") CustomerVipLanguageSearchDto searchDto);

	List<CustomerVipLanguageResultDto> exportExcelWithCondition(
			@Param("searchDto") CustomerVipLanguageSearchDto searchDto);

	Long getMaxSort();

	@Modifying
	public void deleteData(@Param("id") Long idData, @Param("username") String username);

}
