package vn.com.unit.ep2p.rest.cms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.common.dto.EmailResultDto;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.CmsEmailService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_EMAIL)
@Api(tags = { CmsApiConstant.API_CMS_EMAIL_DESCR })
public class EmailRest extends AbstractRest {
	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;

	@Autowired
	private CmsEmailService cmsEmailService;
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
	public DtsApiResponse create(String agentCode, String email, HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			EmailResultDto resObj = cmsEmailService.sendMailOtp(agentCode, email);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (
		Exception ex) {
			System.out.print(ex);
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

}
