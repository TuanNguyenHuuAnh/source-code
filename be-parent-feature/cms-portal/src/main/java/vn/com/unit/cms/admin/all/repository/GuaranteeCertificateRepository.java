/*******************************************************************************
 * Class        ：GuaranteeCertificateRepository
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：hoangnp
 * Change log   ：2017/08/24：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.GuaranteeCertificateSearchDto;
import vn.com.unit.cms.admin.all.entity.GuaranteeCertificate;
import vn.com.unit.db.repository.DbRepository;

/**
 * GuaranteeCertificateRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */

public interface GuaranteeCertificateRepository extends DbRepository<GuaranteeCertificate, Long> {

	/**
	 * findGuaranteeCertificateNumber
	 *
	 * @param certificateNumber
	 * @return
	 * @author hoangnp
	 */
	GuaranteeCertificate findGuaranteeCertificateNumber(@Param("certificateNumber") String certificateNumber);

	/**
	 * countByGuaranteeCertificateSearchDto
	 *
	 * @param searchDto
	 * @return
	 * @author toannt
	 */
	int countByGuaranteeCertificateSearchDto(@Param("searchCond") GuaranteeCertificateSearchDto searchDto);

	/**
	 * findByGuaranteeCertificateSearchDto
	 *
	 * @param offsetSQL
	 * @param sizeOfPage
	 * @param searchDto
	 * @return
	 * @author toannt
	 */
	List<GuaranteeCertificateSearchDto> findByGuaranteeCertificateSearchDto(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("searchCond") GuaranteeCertificateSearchDto searchDto);

}
