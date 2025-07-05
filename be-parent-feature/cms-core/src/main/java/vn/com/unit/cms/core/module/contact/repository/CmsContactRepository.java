package vn.com.unit.cms.core.module.contact.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.contact.dto.CmsContactDto;
import vn.com.unit.cms.core.module.contact.entity.CmsContact;
import vn.com.unit.db.repository.DbRepository;

public interface CmsContactRepository extends DbRepository<CmsContact, Long> {

	public int countByCondition(@Param("lang") String lang); // @Param("searchCondition") CmsContactSearchDto dto

	public List<CmsContactDto> searchByCondition(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage, @Param("lang") String lang);

}
