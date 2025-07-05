/*******************************************************************************
 * Class        ：AuthenLoginRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：TaiTM
 * Change log   ：2020/11/30：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateDetailDto;

/**
 * AuthenLoginRes
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
@NoArgsConstructor
public class AuthenLoginInfoRes {
	private String role;
	private String username;
	private String fullname;
	private String email;
	private Date birthday;
	private String phone;
	private String avatar;
	private String gender;
	private String accountType;

	private String urlFacebook;
	private String urlZalo;
	private Integer googleFlag;
	private Integer facebookFlag;
	private Integer appleFlag;

	private List<String> authorities;
	private CmsAgentDetail agentInfomation;
	private String faceMask;
	private ProfileCandidateDetailDto canidateInfomation;
	private String channel;
	private String menuInfo;
	private String adpApiToken;
}
