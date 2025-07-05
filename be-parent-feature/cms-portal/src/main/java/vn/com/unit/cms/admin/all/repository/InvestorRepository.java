package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

//import jp.xet.springframework.data.mirage.repository.MirageRepository;
import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ExportInvestorReportDto;
import vn.com.unit.cms.admin.all.dto.InvestorEditDto;
import vn.com.unit.cms.admin.all.dto.InvestorLanguageDto;
import vn.com.unit.cms.admin.all.dto.InvestorLanguageEditDto;
import vn.com.unit.cms.admin.all.dto.InvestorSearchDto;
import vn.com.unit.cms.admin.all.entity.Investor;
import vn.com.unit.db.repository.DbRepository;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface InvestorRepository extends DbRepository<Investor, Long> {

	int countBySearchDto(@Param("searchDto") InvestorSearchDto searchDto);

	List<InvestorLanguageDto> findBySearchDto(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("searchDto") InvestorSearchDto searchDto);

	Investor findInvestorByCode(@Param("code") String code);

	List<InvestorEditDto> findAllInvestorForSort(@Param("searchDto") InvestorSearchDto searchDto,
			@Param("language") String language);

	List<ExportInvestorReportDto> exportExcelWithCondition(@Param("searchDto") InvestorSearchDto searchDto);

	@Modifying
	public void updateSortAll(@Param("cond") SortOrderDto sortItem);

	InvestorLanguageEditDto findByAliasAndCategoryId(@Param("categoryId") Long categoryId,
			@Param("linkAlias") String linkAlias, @Param("language") String language);

}
