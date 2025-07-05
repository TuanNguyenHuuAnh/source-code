package vn.com.unit.ep2p.rest.cms;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.com.unit.cms.core.constant.CmsApiConstant;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_ATM_BRANCH)
@Api(tags = { CmsApiConstant.API_CMS_ATM_BRANCH_DESCR })
public class AtmBranchRest {

}
