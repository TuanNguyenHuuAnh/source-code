SELECT DISTINCT 
	homepage.id 					AS id,
	homepage.m_banner_top_id 		AS banner_top_id,
	homepage.m_banner_top_mobile_id 	AS banner_top_mobile_id
FROM M_HOMEPAGE_SETTING homepage
ORDER BY homepage.id DESC