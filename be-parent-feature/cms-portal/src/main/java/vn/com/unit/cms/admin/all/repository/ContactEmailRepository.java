package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.ContactEmailDto;
//import vn.com.unit.cms.admin.all.dto.ContactEmailSearchDto;
import vn.com.unit.cms.admin.all.entity.ContactEmail;
import vn.com.unit.cms.core.module.contact.dto.ContactEmailSearchDto;
import vn.com.unit.db.repository.DbRepository;

public interface ContactEmailRepository extends DbRepository<ContactEmail, Long> {
	public List<ContactEmail> findAllEmailRegistration();

	public int countBySearchCondition(@Param("searchCondition") ContactEmailSearchDto searchCondition);

	public List<ContactEmailDto> findAllEmailByCondition(@Param("offsetSQL") int offsetSQL,
			@Param("sizeOfPage") int sizeOfPage, @Param("searchCondition") ContactEmailSearchDto searchCondition);

	public List<ContactEmailDto> findAllEmailByConditionNoPaging(
			@Param("searchCondition") ContactEmailSearchDto searchCondition);

}
