SELECT distinct mb.id,
	mbl.title,
	mb.create_date
FROM
    m_banner mb    
join m_banner_language mbl on mbl.BANNER_ID = mb.id
WHERE
	mb.delete_date IS NULL
	/*IF bannerType != null && bannerType != ''*/
	AND
	mb.banner_type = /*bannerType*/
	/*END*/
	/*IF isMobile != null*/
	AND
	mb.is_mobile = /*isMobile*/
	/*END*/
ORDER BY create_date DESC
	