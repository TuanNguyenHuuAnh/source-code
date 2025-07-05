/*******************************************************************************
 * Class        RepositoryRepository
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/01 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.core.entity.JcaSystemConfig;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.dto.ResetPasswordDto;

public interface ResetPasswordService {

	public Db2AgentDto checkAgent(String agent);
	
	public ResetPasswordDto resetPassword(ResetPasswordDto editDto);

	public ResetPasswordDto activeSend(ResetPasswordDto editDto);

	public String setPasswordReset(ResetPasswordDto editDto,Locale locale) throws Exception;

	public String checkGad(String agent);

	Db2AgentDto checkAgentExit(String agent);

    boolean checkAgentIsGad(String agent);
}
