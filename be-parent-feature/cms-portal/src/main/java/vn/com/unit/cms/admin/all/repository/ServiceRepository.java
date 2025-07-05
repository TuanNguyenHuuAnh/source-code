package vn.com.unit.cms.admin.all.repository;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.ServiceDto;
import vn.com.unit.cms.admin.all.dto.ServiceLanguageSearchDto;
import vn.com.unit.cms.admin.all.entity.Service;
import vn.com.unit.db.repository.DbRepository;

/**
 * ServiceRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns
 */
public interface ServiceRepository extends DbRepository<Service, Long> {

	int countAll(@Param("serviceSearchDto") ServiceLanguageSearchDto serviceSearchDto);

	int findMaxOrder();

	/**
	 * @param id
	 * @return
	 */
	ServiceDto findServiceDto(@Param("id") Long id);

	/**
	 * @param code
	 * @return
	 */
	ServiceDto findServiceDtoByCode(@Param("code") String code);

}
