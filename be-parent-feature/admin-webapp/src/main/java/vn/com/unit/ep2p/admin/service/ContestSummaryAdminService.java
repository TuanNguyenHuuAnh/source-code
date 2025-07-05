package vn.com.unit.ep2p.admin.service;

import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;
import vn.com.unit.ep2p.admin.entity.ContestApplicableDetailAdmin;
import vn.com.unit.ep2p.admin.entity.ContestSummaryAdmin;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ContestSummaryAdminService {


    List<ContestSummaryAdmin> getContestSaveDetail(Date dateNow);

    ContestApplicableDetailAdmin getdata(Long id);

	void saveDetail(List<Db2SummaryDto> lstData, Long id) throws SQLException;

	void updateCheckSave(Long id);
}
