package vn.com.unit.ep2p.admin.repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.entity.ContestApplicableDetailAdmin;
import vn.com.unit.ep2p.admin.entity.ContestSummaryAdmin;

import java.util.List;

public interface ContestApplicableDetailAdminRepository extends DbRepository<ContestApplicableDetailAdmin, Long> {

    ContestApplicableDetailAdmin findAll(Long id);

	String checkDataOld(Long contestId);
}
