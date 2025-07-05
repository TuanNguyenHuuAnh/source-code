package vn.com.unit.cms.core.module.income.repository;

import vn.com.unit.db.repository.DbRepository;

import vn.com.unit.cms.core.module.income.entity.TaxRegistration;

import org.springframework.data.repository.query.Param;

public interface CommitmentRepository extends DbRepository<TaxRegistration, Long> {
	public String getDocumentStatus(@Param("agentCode") String agentCode, @Param("currYear") String year);
}