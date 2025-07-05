package vn.com.unit.cms.core.module.sam.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.sam.entity.SamAttachment;
import vn.com.unit.db.repository.DbRepository;

public interface SamAttachmentRepository extends DbRepository<SamAttachment, Long> {
	@Modifying
	int updateWfCreated(@Param("actId") Long actId, @Param("type") String type);
}