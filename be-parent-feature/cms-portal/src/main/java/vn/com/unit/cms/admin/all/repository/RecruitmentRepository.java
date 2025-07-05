/*******************************************************************************
 * Class        ：RecruitmentRepository
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.RecruimentSearchDto;
import vn.com.unit.cms.admin.all.dto.RecruitmentDto;
import vn.com.unit.cms.admin.all.entity.Recruitment;
import vn.com.unit.db.repository.DbRepository;

/**
 * RecruitmentRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface RecruitmentRepository extends DbRepository<Recruitment, Long> {

	/**
	 * findAllActive
	 *
	 * @param offset
	 * @param sizeOfPage
	 * @param recDto
	 * @return List<RecruitmentDto>
	 * @author phunghn
	 */
	List<RecruitmentDto> findAllActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("recDto") RecruimentSearchDto recDto);

	/**
	 * countRecruitmentDto
	 *
	 * @param recDto
	 * @return int
	 * @author phunghn
	 */
	int countRecruitmentDto(@Param("recDto") RecruimentSearchDto recDto);

	/**
	 * getRecruitmentEditdto
	 *
	 * @param id
	 * @return Recruitment
	 * @author phunghn
	 */
	Recruitment getRecruitmentEditdto(@Param("id") Long id);

}
