/**
 * 
 */
package vn.com.unit.cms.admin.all.service;

import java.util.Locale;

import vn.com.unit.cms.admin.all.dto.InterestRateHistoryDto;
import vn.com.unit.cms.admin.all.dto.InterestRateListDto;
import vn.com.unit.common.dto.PageWrapper;

/**
 * @author sonnt
 *
 */
public interface InterestRateHistoryService {
	
	public void insert(InterestRateListDto interestRateListDto);
	
	public PageWrapper<InterestRateHistoryDto> list(Long cityId, int page, Locale locale);
}
