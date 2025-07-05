SELECT count(*)
FROM m_homepage_setting homepage
LEFT JOIN jca_constant_display acc_code ON acc_code.type = 'B01' AND acc_code.cat = homepage.banner_page
WHERE
	homepage.delete_date is NULL
	
	/*IF homepageSearchDto.status != null && homepageSearchDto.status != ''*/
	AND homepage.status = /*homepageSearchDto.status*/
	/*END*/
	
	/*IF homepageSearchDto.startDate != null*/
	AND CAST(homepage.effective_date_from as DATE) = CAST(/*homepageSearchDto.startDate*/'' as DATE)
	/*END*/
	
	/*IF homepageSearchDto.endDate != null*/
	AND CAST(homepage.effective_date_to as DATE) = CAST(/*homepageSearchDto.endDate*/'' as DATE)
	/*END*/
	/*IF homepageSearchDto.bannerPage != null && homepageSearchDto.bannerPage != ''*/
	AND acc_code.cat = /*homepageSearchDto.bannerPage*/
	/*END*/