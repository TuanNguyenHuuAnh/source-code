SELECT
    *    
FROM
    m_banner
WHERE
	delete_date IS NULL
	/*IF bannerType != null && bannerType != ''*/
	AND
	banner_type = /*bannerType*/
	/*END*/
	/*IF isMobile != null*/
	AND
	is_mobile = /*isMobile*/
	/*END*/
ORDER BY create_date DESC
	