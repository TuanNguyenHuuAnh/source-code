package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ServiceLanguageSearchDto;
import vn.com.unit.cms.admin.all.entity.ServiceLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * ServiceLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns
 */
public interface ServiceLanguageRepository extends DbRepository<ServiceLanguage, Long> {

	List<ServiceLanguageSearchDto> findAllActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("serviceSearchDto") ServiceLanguageSearchDto serviceSearchDto);

	/**
	 * @param id
	 * @param langCode
	 * @return ServiceLanguageSearchDto
	 */
	ServiceLanguageSearchDto findServiceLanguageDto(@Param("id") Long id, @Param("lang") String langCode);

	/**
	 * @param id
	 * @return ServiceLanguage
	 */
	List<ServiceLanguage> findLanguageByID(@Param("id") long id);

	@Modifying
	void deleteLanguageByID(@Param("id") Long id, @Param("user") String user, @Param("deleteDate") Date date);

}
