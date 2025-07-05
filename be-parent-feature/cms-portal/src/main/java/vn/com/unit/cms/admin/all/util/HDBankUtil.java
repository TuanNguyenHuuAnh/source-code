/*******************************************************************************
 * Class        ：HDBankUtil
 * Created date ：2018/12/05
 * Lasted date  ：2018/12/05
 * Author       ：taitm
 * Change log   ：2018/12/05：01-00 taitm create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.util;

import java.util.ArrayList;
import java.util.List;

import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * HDBankUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author taitm
 */
public class HDBankUtil {
	public final static Long PERSONAL_TYPE_ID = 9L;
	public final static Long CORPORATE_TYPE_ID = 10L;
	public final static Long ABOUT_HDBANK_TYPE_ID = 11L;
	public final static Long INVESTOR_TYPE_ID = 12L;
	public final static Long INTRODUCE_INTERNET_BANKING_ID = 13L;

	public static Long getCustomerType(String customerAlias) {
		Long customerId = -1L;
		switch (customerAlias) {
		case UrlConst.CORPORATE:
			customerId = CORPORATE_TYPE_ID;
			break;
		case UrlConst.PERSONAL:
			customerId = PERSONAL_TYPE_ID;
			break;
		case UrlConst.INVESTOR:
			customerId = INVESTOR_TYPE_ID;
			break;
		case UrlConst.ABOUT_HDBANK:
			customerId = ABOUT_HDBANK_TYPE_ID;
			break;
		case UrlConst.INTRODUCE_INTERNET_BANKING:
			customerId = INTRODUCE_INTERNET_BANKING_ID;
			break;
		default:
			customerId = -1L;
			break;
		}
		return customerId;
	}

	public static List<Long> getListStatusForDependency() {
		List<Long> lstStatus = new ArrayList<>();
		lstStatus.add(Long.valueOf(StepStatusEnum.DRAFT.getStepNo()));
		lstStatus.add(Long.valueOf(StepStatusEnum.REJECTED.getStepNo()));
		lstStatus.add(Long.valueOf(StepStatusEnum.CANCELED.getStepNo()));
		return lstStatus;
	}
}
