package vn.com.unit.ep2p.admin.service.impl;

import jp.sf.amateras.mirage.provider.ConnectionProvider;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;
import vn.com.unit.ep2p.admin.entity.ContestApplicableDetailAdmin;
import vn.com.unit.ep2p.admin.entity.ContestSummaryAdmin;
import vn.com.unit.ep2p.admin.repository.ContestApplicableDetailAdminRepository;
import vn.com.unit.ep2p.admin.repository.ContestSummaryAdminRepository;
import vn.com.unit.ep2p.admin.service.ContestSummaryAdminService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ContestSummaryAdminServiceImpl implements ContestSummaryAdminService {

	@Autowired
	ContestSummaryAdminRepository contestSummaryAdminRepository;

	@Autowired
	ContestApplicableDetailAdminRepository contestApplicableDetailAdminRepository;

	@Autowired
	private ConnectionProvider connectionProvider;

	private static final Logger logger = LoggerFactory.getLogger(ContestSummaryAdminServiceImpl.class);

	private final int BATCHSIZE = 10000;

	@Override
	public List<ContestSummaryAdmin> getContestSaveDetail(Date dateNow) {
		return contestSummaryAdminRepository.findContestSaveDetail(dateNow);
	}

	@Override
	public ContestApplicableDetailAdmin getdata(Long id) {
		return contestApplicableDetailAdminRepository.findAll(id);
	}

	@Override
	public void saveDetail(List<Db2SummaryDto> lstData, Long contestId) throws SQLException {
		// CHANGE OJECT DELETE DATA CŨ
		Connection connection = connectionProvider.getConnection();

		String exit = contestApplicableDetailAdminRepository.checkDataOld(contestId);
		if(!StringUtils.endsWithIgnoreCase(exit, "0")) {
			logger.error("Begin delete ");
			connection.setAutoCommit(false);
			String deleteOject = " DELETE FROM M_CONTEST_APPLICABLE_DETAIL WHERE CONTEST_ID = ?";

			PreparedStatement psttt = connection.prepareStatement(deleteOject);
			int startRowDeleteOject = 0;
			for (Db2SummaryDto data : lstData) {
				psttt.setLong(1, contestId);
				psttt.addBatch();
				startRowDeleteOject++;
				if (startRowDeleteOject % BATCHSIZE == 0) {
					psttt.executeBatch();
					psttt.clearBatch();
					startRowDeleteOject = 0;
				}
				psttt.executeBatch();
				psttt.clearBatch();
				connection.commit();
			}
		}
			//END



		//INSERT
		String query = "INSERT INTO M_CONTEST_APPLICABLE_DETAIL (CONTEST_ID, TERRITORRY, AREA, REGION, OFFICE, POSITION, AGENT_NAME, AGENT_CODE, REPORTINGTO_CODE, REPORTINGTO_NAME, CREATE_BY, CREATE_DATE) " +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement pst = connection.prepareStatement(query);
		int startRow = 0;
		for (Db2SummaryDto data : lstData) {

			pst.setLong(1, contestId);
			pst.setString(2, data.getNdCode());
			pst.setString(3, data.getNCode());
			pst.setString(4, data.getRCode());
			pst.setString(5, data.getOCode());
			pst.setString(6, data.getAgentType());
			pst.setString(7, data.getAgentName());
			pst.setString(8, data.getAgentCode());
			pst.setString(9, data.getReportingToCode());
			pst.setString(10, data.getReportingToName());
			pst.setString(11, "System");
			Date curDate = new Date();
			pst.setDate(12, new java.sql.Date(curDate.getTime()));
			pst.addBatch();
			startRow++;
			if (startRow % BATCHSIZE == 0) {
				pst.executeBatch();
				logger.error("PST = "+startRow);

				pst.clearBatch();
				startRow = 0;
			}

		}
		pst.executeBatch();
		pst.clearBatch();
		connection.commit();
		//END
	}

	@Override
	public void updateCheckSave(Long id) {
		contestSummaryAdminRepository.updateCheckSave(id);
	}
}
