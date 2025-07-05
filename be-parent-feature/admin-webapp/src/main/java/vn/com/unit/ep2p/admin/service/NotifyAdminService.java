package vn.com.unit.ep2p.admin.service;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.dto.NotifyAdminEditDto;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface NotifyAdminService {
	
	List<String> insertToDatabaseWithBatch(List<Db2AgentDto> entity,Long notifyId,String contest, int batchSize) throws SQLException;

    List<Db2AgentDto> getLsNotifyDetail(Long id);

	public List<NotifyAdminEditDto> getSendDate(String sendDate);

    void updateIsSend(Long id);

    NotifyAdminEditDto findNotifyId (String notifyCode);

    void updateSendDate(Long id,String code);

    List<Db2AgentDto> getNotifySaveDetail(Date date);

    void insertDetailToDatabaseWithBatch(List<Db2AgentDto> dataInsert, Long id, Date sendDate) throws SQLException;

    void updateCheckSave(Long id);

    void pushNotificationToFirebaseCloud(List<Db2AgentDto> dataInsert, Db2AgentDto data) throws Exception;
}
