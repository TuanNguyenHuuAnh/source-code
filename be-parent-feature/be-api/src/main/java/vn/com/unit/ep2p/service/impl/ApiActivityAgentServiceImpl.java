package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.agent.dto.ActivityAgentDto;
import vn.com.unit.cms.core.module.agent.dto.ActivityAgentParam;
import vn.com.unit.cms.core.module.agent.dto.ActivityGroupDto;
import vn.com.unit.cms.core.module.agent.dto.ActivityGroupParam;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.service.ApiActivityAgentService;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiActivityAgentServiceImpl implements ApiActivityAgentService{

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	private static final String DS_SP_GET_MONTHLY_LEADER = "RPT_ODS.DS_SP_GET_MONTHLY_MTD";
	private static final String DS_SP_GET_MONTHLY_SALES = "RPT_ODS.DS_SP_GET_MONTHLY_MTD";
	
	@Override
	public ActivityAgentDto getActivityAgent(String agentCode) {
		ActivityAgentParam param = new ActivityAgentParam();
		param.agentCode = agentCode;
		param.agentGroup = "FC";
		//DS_SP_GET_MONTHLY_MTD(100815,'FC'); 
		sqlManagerDb2Service.call(DS_SP_GET_MONTHLY_SALES, param);
		ActivityAgentDto entity = new ActivityAgentDto();
		if(CollectionUtils.isNotEmpty(param.dataDetail)) {
			entity = param.dataDetail.get(0);
		}
		return entity;
	}
	
	@Override
	public ActivityGroupDto getActivityGroup(String agentCode, String agentGroup) {
		ActivityGroupParam param = new ActivityGroupParam();
		param.agentCode = agentCode;
		param.agentGroup = agentGroup;
		sqlManagerDb2Service.call(DS_SP_GET_MONTHLY_LEADER, param);
		ActivityGroupDto entity = new ActivityGroupDto();
		if(CollectionUtils.isNotEmpty(param.dataDetail)) {
			entity = param.dataDetail.get(0);
		}
		if(StringUtils.isNotEmpty(entity.getTop3User())){
			List<String> list = new ArrayList<>();
            CollectionUtils.addAll(list, entity.getTop3User().split(";"));
            entity.setTop3UserList(list);
		}
		return entity;
	}
}
